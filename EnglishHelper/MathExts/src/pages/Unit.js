import { withIonLifeCycle, IonContent, IonPage, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonList, IonItem } from '@ionic/react';
import React, { Component } from 'react';
import { REQUEST_UNITS } from '../urls.js'
import axios from "axios";

class Unit extends Component {

    constructor(props) {
        super(props)
        this.state = {
            units: []
        }
    }

    ionViewWillEnter() {
        const that = this
        axios.get(REQUEST_UNITS + "/" + this.props.match.params.book_id).then(res => {
            if (res.status === 200) {
                that.setState({ units: res.data.response })
            } else {
                console.log(res)
            }
        }).catch(err => {
            console.log(err)
        })
    }

    render() {
        return (
            <IonPage>
                <IonHeader>
                    <IonToolbar>
                        <IonButtons slot="start">
                            <IonBackButton defaultHref="/home" />
                        </IonButtons>
                        <IonTitle>章节内容</IonTitle>
                    </IonToolbar>
                </IonHeader>
                <IonContent>
                    <IonList>
                    {this.state.units.map((unit, index) =>
                        (<IonItem key={index} routerLink={ "/book/" + this.props.match.params.book_id  + "/unit/" + unit.id  + "/concepts" }>{unit.name}</IonItem>)
                    )}
                    </IonList>
                </IonContent>
            </IonPage>
        );
    }

}

export default withIonLifeCycle(Unit);
