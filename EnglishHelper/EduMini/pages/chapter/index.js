const {
  post
} = require('../../utils/api')
const { debug } = require('../../utils/debug')
Page({
  data: {
    chapterId: -1,
    chapterName: '',
    paragraphs: [],
    visibles: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const chapterId = options.chapter_id
    const chapterName = options.chapter_name
    this.setData({chapterName: chapterName})
    const _this = this
    post('/api/wx/paragraph/list?chapterId=' + chapterId).then(data => {
      debug(data)
      _this.setData({ chapterId: chapterId, paragraphs: data.response })

    }).catch(err => {
      debug(err)
    })
  },

  onParagraphClick(e) {
    const index = e.currentTarget.dataset.index
    const isVisible = this.data.visibles[index]
    let key = `visibles.${index}`
    if(isVisible) {
      this.setData({
        [key]: !isVisible
      })
    } else {
      this.setData({[key]: true})
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})