const {post} = require('../../../utils/api');
const {debug} = require('../../../utils/debug');
Page({

  data: {
    typeId: -1,
    articles: [],

  },

  onLoad: function(options) {
    const typeId = options.type_id
    const _this = this
    post('/api/wx/article/list?articleType=' + typeId).then(res => {
      debug(res)
      _this.setData({
        articles: res.response
      })
    }).catch(err => {
      debug(err)
    })
  },

  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

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