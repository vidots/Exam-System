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
    subject: '',
    dynamic: {},
    content: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const subject = options.subject
    this.setData({
      subject: subject
    })
    const _this = this
    post('/api/wx/dycontent/subject?subject=' + subject ).then(data => {
      const res = data.response
      let result = app.towxml(res.content, 'html')
      _this.setData({ dynamic: res, content: result })

    }).catch(err => {
      debug(err)
    })
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