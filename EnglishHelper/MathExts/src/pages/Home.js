import { withIonLifeCycle, IonContent, IonPage, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonItem, IonFab, IonFabButton, IonIcon, IonFabList, IonModal, IonButton, IonHeader, IonToolbar, IonTitle, IonButtons, IonList, IonItemDivider, IonLabel, IonRange, IonSearchbar } from '@ionic/react';
import React, { Component } from 'react';
import { REQUEST_BOOKS } from '../urls.js'
import { Plugins } from "@capacitor/core";
import { settings, add, gridOutline, key } from 'ionicons/icons';
import axios from "axios";

const {Storage} = Plugins

class Home extends Component {

  constructor(props) {
    super(props)
    this.state = {
      books: [],
      showModal: false,
      fontSize: 0
    }
  }

  ionViewWillEnter() {

    const that = this
    axios.get(REQUEST_BOOKS).then(res => {
      if (res.status === 200) {
        that.setState({ books: res.data.response })
      } else {
        console.log(res)
      }
    }).catch(err => {
      console.log(err)
    })
  }

  clickSetting() {
    const that = this
    Storage.get({key: 'fontSize'}).then(res => {
      const fontSize = res.value
      if(fontSize == null) {
        that.setState({fontSize: 12})
      } else {
        that.setState({fontSize: parseInt(fontSize)})
      }
    }).catch(err => {
      console.log(err)
    })
    this.setState({ showModal: true })
  }

  setFontSize(size) {
    this.setState({fontSize: size})
    Storage.set({key: 'fontSize', value: size + ''}).then(res => {
      console.log(res)
    }).catch(err => {
      console.log(err)
    })
  }


  render() {
    return (
      <IonPage>
        <IonContent>
          <IonModal isOpen={this.state.showModal} onDidDismiss={() => this.setState({showModal: false})}>
            <IonHeader>
              <IonToolbar>
                <IonTitle>设置</IonTitle>
                <IonButtons slot="end">
                  <IonButton onClick={() => this.setState({ showModal: false })}>关闭</IonButton>
                </IonButtons>
              </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
              <IonList lines="full" class="ion-no-margin ion-no-padding">
                <IonItemDivider>
                  <IonLabel>数学公式字体大小</IonLabel>
                </IonItemDivider>
                <IonItem>
                  <IonLabel >{this.state.fontSize}</IonLabel>
                  <IonRange min={6} max={24} value={this.state.fontSize} onIonChange={e => this.setFontSize(parseInt(e.detail.value)) }></IonRange>
                </IonItem>
              </IonList>
            </IonContent>
          </IonModal>
          <IonSearchbar onFocus={e => this.props.history.push("/search")}></IonSearchbar>
          {this.state.books.map((book, index) =>
            (<IonCard key={index} routerLink={"/book/" + book.id + "/units"} routerDirection='forward' >
              <IonCardHeader>
                <IonCardTitle>{book.name}</IonCardTitle>
              </IonCardHeader>
              <IonCardContent>{book.info}</IonCardContent>
            </IonCard>)
          )}
          <IonFab vertical="bottom" horizontal="end" slot="fixed">
            <IonFabButton>
              <IonIcon icon={gridOutline} />
            </IonFabButton>
            <IonFabList side="top">
              <IonFabButton onClick={() => this.clickSetting()}><IonIcon icon={settings}></IonIcon></IonFabButton>
            </IonFabList>
          </IonFab>
        </IonContent>
      </IonPage>
    );
  }

}

export default withIonLifeCycle(Home);
