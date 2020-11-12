const { info_of_words } = require('../../../utils/consts.js')
import Notify from '../../../vant/notify/notify'
const {post} = require('../../../utils/api.js')
const { debug } = require('../../../utils/debug')

Page({

  data: {
    currentIndex: 0,
    activeIndex: 1,
    listIndex: 0,
    words: [],
    nums: [],
    currentWord: '',
    currentDetail: null,
    engAnalysis: false,
    allAnalysis: false,
    scrollviewHeight: 0,
    listSize: 50
  },

  onLoad: function (options) {
    let listSize = wx.getStorageSync('listSize')
    if(listSize == null || listSize == '') {
      listSize = 50
    }
    const activeIndex = options.activeIndex
    const currentIndex = parseInt(options.currentIndex)
    const listIndex = options.listIndex
    const words_data = require("../../../data/word/" + info_of_words[listIndex].fs)
    const keys = Object.keys(words_data)
    const values = Object.values(words_data)
    const words = keys.slice((activeIndex - 1) * listSize, activeIndex * listSize)
    const nums = values.slice((activeIndex - 1) * listSize, activeIndex * listSize)
    this.setData({
      currentIndex: currentIndex,
      activeIndex: activeIndex,
      listIndex: listIndex,
      words: words,
      nums: nums,
      currentWord: words[currentIndex]
    })
  },

  preWord(e) {
    const current = this.data.currentIndex
      if(current == 0) {
        Notify("已经是第一个单词啦")
        return
      }
    this.setData({ currentIndex: current - 1, currentDetail: null, currentWord: this.data.words[current - 1], allAnalysis: false, engAnalysis: false })
  },
  nextWord(e) {
    const current = this.data.currentIndex
    if (current == this.data.words.length - 1) {
      Notify("已经是最后一个单词啦")
      return
    }
    this.setData({ currentIndex: current + 1, currentDetail: null, currentWord: this.data.words[current + 1] , allAnalysis: false, engAnalysis: false})
  },

  engAnalyze(e) {
    if(this.data.currentDetail == null) {
      this.fetchWordDetail(false)
    } else {
      this.setData({engAnalysis: true})
    }
  },

  allAnalyze(e) {
    if (this.data.currentDetail == null) {
      this.fetchWordDetail(true)
    } else {
      this.setData({engAnalysis: true, allAnalysis: true})
    }
  },

  clickUS(e) {
    const word = this.data.currentWord
    this.audioCtx.setSrc('https://dict.youdao.com/dictvoice?audio=' + word + '&type=2')
    this.audioCtx.play()
  },

  clickUK(e) {
    const word = this.data.currentWord
    this.audioCtx.setSrc('https://dict.youdao.com/dictvoice?audio=' + word + '&type=1')
    this.audioCtx.play()
  },

fetchWordDetail(all) {
  let that = this
  post('/api/wx/word?word=' + this.data.currentWord).then(res => {
    const detail = JSON.parse(res.response)
    if(all) {
      that.setData({ currentDetail: detail, allAnalysis: true, engAnalysis: true })
    } else {
      that.setData({ currentDetail: detail, engAnalysis: true })
    }
   
  }).catch(err => {
    debug("发生错误",err)
  })
},

  // 计算Scrollview的高度
  computeScrollViewHeight() {
    let that = this
    let query = wx.createSelectorQuery().in(this)
    query.select('.footer-group').boundingClientRect(function (res) {
      let footerHeight = res.height
      //获取屏幕可用高度
      let screenHeight = wx.getSystemInfoSync().windowHeight
      let scrollHeight = screenHeight - footerHeight - 56
      that.setData({
        scrollviewHeight: scrollHeight
      })
    }).exec()
  },

  onReady: function () {
    this.computeScrollViewHeight();
    this.audioCtx = wx.createAudioContext('myAudio')
  },

  onShow: function () {

  },

  onHide: function () {

  },

  onUnload: function () {

  },

  onPullDownRefresh: function () {

  },

  onReachBottom: function () {

  },

  onShareAppMessage: function () {

  }
})