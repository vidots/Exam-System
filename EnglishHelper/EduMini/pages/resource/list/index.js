const { post } = require('../../../utils/api')
const { debug } = require('../../../utils/debug')
import Toast from "../../../vant/toast/toast"

Page({

  data: {
    typeId: 0,
    resources: []
  },

  onLoad: function (options) {
    const typeId = options.type_id
    this.setData({ typeId: typeId})
    this.reloadData()
  },

  reloadData() {
    const that = this
    post("/api/wx/resource/list/" + this.data.typeId).then(res => {
      that.setData({  resources: res.response })
      wx.stopPullDownRefresh();
    }).catch(err => {
      debug(err)
    })
  },

  copyResource(e) {
    const role = wx.getStorageSync("role")
    if (role != 'vip') {
      Toast.fail("该内容只有会员才能享用")
      return
    }
    const index = e.currentTarget.dataset.index
    const resource = this.data.resources[index]
    const data = "资源：" + resource.title + "，" + "链接：" + resource.link + "，" + "密码：" + resource.passw
    wx.setClipboardData({
      data: data,
      success: function (res) {
        wx.getClipboardData({
          success: function (res) {
            wx.showToast({
              title: '复制成功'
            })
          }
        })
      }
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
    this.reloadData()
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