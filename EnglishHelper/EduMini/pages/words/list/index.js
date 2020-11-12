const { info_of_words } = require('../../../utils/consts.js')

Page({

  data: {
    wordIndex: 0,
    wordsInfo: {},
    scrollviewHeight: 0,
    activeIndex: 0,
    words: [],
    nums: [],
    showNum: false,
    showTitle: '显示频次',
    listSize: 50
  },

  onLoad: function (options) {
    const index = options.index
    const words_data = require("../../../data/word/" + info_of_words[index].fs)
    let listSize = wx.getStorageSync('listSize')
    if(listSize == null || listSize == '') {
      listSize = 50
    }
    this.setData({ wordIndex: index, wordsInfo: info_of_words[index], scrollviewHeight: wx.getSystemInfoSync().windowHeight, words: Object.keys(words_data), nums: Object.values(words_data), listSize: listSize})
  },

  clickOverview(e) {
      this.setData({activeIndex: 0})
  },

  onChange(e) {
      const targetIndex = e.currentTarget.dataset.index
      this.setData({activeIndex: targetIndex + 1})
  },

  toggleShowNum(e) {
    this.setData({showNum: !this.data.showNum, showTitle: this.data.showNum ? '显示频次' : '隐藏频次'})
  },

  startRemember(e) {
    wx.navigateTo({
      url: '/pages/words/word/index?activeIndex=' + this.data.activeIndex + '&currentIndex=0&listIndex=' + this.data.wordIndex,
    })
  },

clickWord(e) {
  const targetIndex = e.currentTarget.dataset.index
  wx.navigateTo({
    url: '/pages/words/word/index?activeIndex=' + this.data.activeIndex + '&currentIndex=' + targetIndex + '&listIndex=' + this.data.wordIndex,
  })
},

  onReady: function () {

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