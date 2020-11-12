import { withIonLifeCycle, IonContent, IonPage, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonList, IonItem, IonLabel, IonIcon, IonButton, IonPopover, IonModal, IonItemDivider, IonRange } from '@ionic/react';
import React, { Component } from 'react';
import { REQUEST_CONCEPTS } from '../urls.js'
import axios from "axios";
import renderMathInElement from 'katex/contrib/auto-render/auto-render'
import 'katex/dist/katex.css';
import { Plugins } from "@capacitor/core";
import { pin, imageOutline, lockClosed, lockClosedOutline, lockOpenOutline, settings } from 'ionicons/icons';

const { Storage } = Plugins

class Concept extends Component {

    constructor(props) {
        super(props)
        this.state = {
            concepts: [],
            fontSize: 12,
            showPopover: false,
            showModal: false,
            imgSrc: '',
            showMath: false
        }
    }

    ionViewWillEnter() {
        const that = this
        Storage.get({ key: 'fontSize' }).then(res => {
            const fontSize = res.value
            if (fontSize == null) {
                that.setState({ fontSize: 12 })
            } else {
                that.setState({ fontSize: parseInt(fontSize) })
            }
        }).catch(err => {
            console.log(err)
        })
        axios.get(REQUEST_CONCEPTS + "/" + this.props.match.params.unit_id).then(res => {
            if (res.status === 200) {
                const result = res.data.response.map(concept => {
                    concept.show = false
                    return concept
                })
                that.setState({ concepts: result })
            } else {
                console.log(res)
            }
        }).catch(err => {
            console.log(err)
        })
    }

    clickSetting() {
        const that = this
        Storage.get({ key: 'fontSize' }).then(res => {
            const fontSize = res.value
            if (fontSize == null) {
                that.setState({ fontSize: 12 })
            } else {
                that.setState({ fontSize: parseInt(fontSize) })
            }
        }).catch(err => {
            console.log(err)
        })
        this.setState({ showModal: true })
    }

    setFontSize(size) {
        this.setState({ fontSize: size })
        Storage.set({ key: 'fontSize', value: size + '' }).then(res => {
            console.log(res)
        }).catch(err => {
            console.log(err)
        })
    }

    convertMathExts() {
        renderMathInElement(document.getElementById('math-container'), {
            delimiters: [
                { left: '$$', right: '$$', display: false },
                { left: '$', right: '$', display: false }
            ],
            leqno: true
        })
    }

    showImage(src) {
        this.setState({
            showPopover: true,
            imgSrc: src
        })
    }

    toggleShowMath(item) {
        let concepts = this.state.concepts.map(concept => {
            if (item.id === concept.id) {
                concept.show = !item.show
            }
            return concept
        })
        this.setState({ concepts: concepts })
    }

    changeShowMath() {
        let concepts = this.state.concepts
        if (this.state.showMath) {
            concepts = concepts.map(concept => {
                concept.show = false
                return concept
            })
        } else {
            concepts = concepts.map(concept => {
                concept.show = true
                return concept
            })
        }
        this.setState({ showMath: !this.state.showMath, concepts: concepts })
    }

    componentDidUpdate() {
        this.convertMathExts()
    }

    render() {
        console.log("字体大小：", this.state.fontSize)
        const style = {
            fontSize: `${this.state.fontSize}px`
        }
        return (
            <IonPage>
                <IonHeader>
                    <IonToolbar>
                        <IonButtons slot="start">
                            <IonBackButton defaultHref={"/book/" + this.props.match.params.book_id + "/units"} />
                        </IonButtons>
                        <IonTitle>数学概念</IonTitle>
                        <IonButtons slot="end">
                            <IonButton onClick={e => this.clickSetting()}><IonIcon slot="icon-only" icon={settings} /></IonButton>
                            <IonButton onClick={e => this.changeShowMath()}><IonIcon slot="icon-only" icon={!this.state.showMath ? lockClosedOutline : lockOpenOutline} /></IonButton>
                        </IonButtons>
                    </IonToolbar>
                </IonHeader>
                <IonContent id="math-container">
                    <IonModal isOpen={this.state.showModal} onDidDismiss={() => this.setState({ showModal: false })}>
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
                                    <IonRange min={6} max={24} value={this.state.fontSize} onIonChange={e => this.setFontSize(parseInt(e.detail.value))}></IonRange>
                                </IonItem>
                            </IonList>
                        </IonContent>
                    </IonModal>
                    <IonPopover isOpen={this.state.showPopover} onDidDismiss={e => this.setState({ showPopover: false })}>
                        <div style={{ margin: '2px' }}>
                            <img src={this.state.imgSrc} />
                        </div>
                    </IonPopover>
                    <IonList>
                        {this.state.concepts.map((concept, index) =>
                            (<IonCard key={index}>
                                <IonItem>
                                    <IonLabel>{concept.name}</IonLabel>
                                    <IonButtons slot="end">
                                        {
                                            concept.img !== "" &&
                                            <IonButton onClick={e => this.showImage(concept.img)}><IonIcon icon={imageOutline} /></IonButton>
                                        }
                                        <IonButton onClick={e => this.toggleShowMath(concept)} size="small"><IonIcon slot="icon-only" icon={!concept.show ? lockClosedOutline : lockOpenOutline} /></IonButton>
                                    </IonButtons>
                                </IonItem>
                                {
                                    concept.show &&
                                    <IonCardContent>
                                        <div style={style} dangerouslySetInnerHTML={{ __html: concept.detail.split('\n').join('<br/><br/>') }}></div>
                                    </IonCardContent>
                                }
                            </IonCard>)
                        )}
                    </IonList>
                </IonContent>
            </IonPage>
        );
    }

}

export default withIonLifeCycle(Concept);
