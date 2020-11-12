const app = getApp()
const {
  post
} = require('../../utils/api')
const { debug } = require('../../utils/debug')


Page({

  /**
   * 页面的初始数据
   */
  data: {
    articleId: -1,
    article: {},
    content: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const articleId = options.article_id
    this.setData({
      articleId: options.article_id
    })
    const _this = this
    post('/api/wx/article/' + articleId).then(data => {
      const res = data.response
      let result = app.towxml(res.content, res.format, {
        events: {
          tap: (e) => {
            let tag = e.currentTarget.dataset.data.tag
            if(tag == "img") {
              let url = e.currentTarget.dataset.data.attr.src
              wx.previewImage({
                urls: [url],
              })
            }
          }
        }
      })
      _this.setData({article: res, content: result})
      
    }).catch(err => {
      debug(err)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
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