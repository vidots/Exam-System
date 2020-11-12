const {
  jsonPost,
  post
} = require("../../utils/api.js")
const { debug } = require('../../utils/debug')
import Notify from '../../vant/notify/notify'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    questions: [],
    current: 0,
    questionCount: 0,
    currentQuestion: {},
    chooseOne: false,
    chooseTwo: false,
    chooseThree: false,
    chooseFour: false,
    showAnalysis: false,
    rightAnswer: '',
    scrollviewHeight: 0,
    isShowPopup: false,
    myAnswer: '',
    showSheet: false,
    actions: [
      {loading: true, name: '' },
      {name: '导航'}
    ]
  },

  onClose() {
    this.setData({
      isShowPopup: false
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const _this = this;
    post("/api/wx/collect/list")
      .then(res => {
        debug(res)
        const questions = res.response
        const questionCount = questions.length
        if(questionCount == 0) return
        const currentQuestion = questions[0]
        const rightAnswer = currentQuestion.answer.toUpperCase().split("").join("、")
        _this.setData({
          questions: questions,
          questionCount: questionCount,
          currentQuestion: currentQuestion,
          rightAnswer: rightAnswer
        })
      })
  },
  clickOne(e) {
    const selected = !this.data.chooseOne
    this.setData({
      chooseOne: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseTwo: false,
        chooseThree: false,
        chooseFour: false
      })
    }
  },
  clickTwo(e) {
    const selected = !this.data.chooseTwo
    this.setData({
      chooseTwo: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseThree: false,
        chooseFour: false
      })
    }
  },
  clickThree(e) {
    const selected = !this.data.chooseThree
    this.setData({
      chooseThree: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseTwo: false,
        chooseFour: false
      })
    }
  },
  clickFour(e) {
    const selected = !this.data.chooseFour
    this.setData({
      chooseFour: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseTwo: false,
        chooseThree: false
      })
    }
  },
  jumpToIndex(e) {
    const targetIndex = e.currentTarget.dataset.index
    const targetQuestion = this.data.questions[targetIndex]
    const rightAnswer = targetQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: targetIndex,
      rightAnswer: rightAnswer,
      currentQuestion: targetQuestion,
      isShowPopup: false
    })
  },
  // 计算Scrollview的高度
  computeScrollViewHeight() {
    let that = this
    let query = wx.createSelectorQuery().in(this)
    query.select('.footer-group').boundingClientRect(function(res) {
      let footerHeight = res.height
      //获取屏幕可用高度
      let screenHeight = wx.getSystemInfoSync().windowHeight
      let scrollHeight = screenHeight - footerHeight - 24
      that.setData({
        scrollviewHeight: scrollHeight
      })
    }).exec()
  },

  // 是否显示答案
  startAnalyze(e) {
    this.setData({
      showAnalysis: !this.data.showAnalysis
    })
  },


  //点击上一题的操作
  preQuestion(e) {
    if (this.data.current == 0) {
      Notify("已经是第一题了")
      return
    }

    const preIndex = this.data.current - 1
    const preQuestion = this.data.questions[preIndex]
    const rightAnswer = preQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: preIndex,
      rightAnswer: rightAnswer,
      currentQuestion: preQuestion,
      showAnalysis: false
    })
  },
  // 点击下一题的操作
  nextQuestion(e) {
    if (this.data.current == this.data.questions.length - 1) {
      this.setData({
        isShowPopup: true
      })
      return
    }
    const nextIndex = this.data.current + 1
    const nextQuestion = this.data.questions[nextIndex]
    const rightAnswer = nextQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: nextIndex,
      rightAnswer: rightAnswer,
      currentQuestion: nextQuestion,
      showAnalysis: false
    })
  },

  showMore: function(e) {
      this.setData({showSheet: true, actions: [
        { loading: true, name: '' }
      ]})
      const _this = this
    post("/api/wx/collect/is", {questionId: this.data.currentQuestion.id})
    .then(res => {
      debug(res)
      if(res.code == 200) {
        _this.setData({actions: [
          { loading: false, name: res.response == "true" ? "取消收藏" : "收藏" },
          {name: '导航'}
        ]})
      }
    }).catch(err => {
      debug(err)
    })
  },
  onClose: function() {
      this.setData({showSheet: false})
  },
  onSelect: function(e) {
    if(e.detail.name == "收藏") {
      post("/api/wx/collect/add", {
        questionId: this.data.currentQuestion.id
      }).then(res => {
        if(res.code == 200) {

        } else {
          debug(res.response)
        }
      }).catch(err => {
        debug(err)
      })
    } else if(e.detail.name == '取消收藏'){
      post("/api/wx/collect/cancel", {
        questionId: this.data.currentQuestion.id
      }).then(res => {
        if (res.code == 200) {

        } else {
          debug(res.response)
        }
      }).catch(err => {
        debug(err)
      })
    } else if(e.detail.name == '导航') {
      this.showCard()
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    this.computeScrollViewHeight();
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  showCard() {
    this.setData({
      isShowPopup: true
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})