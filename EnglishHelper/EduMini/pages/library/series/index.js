const app = getApp();
const { post } = require('../../../utils/api')
const { debug } = require('../../../utils/debug')
import Toast from "../../../vant/toast/toast"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    serieses: []
  },

  onItemClick(e) {
    const index = e.currentTarget.dataset.index
    const series = this.data.serieses[index]
    const id = series.id
    const needVip = series.needVip
    const role = wx.getStorageSync("role")
    if(needVip && role != 'vip') {
      Toast.fail("该内容只有会员才能享用")
      return
    }
    wx.navigateTo({
      url: '/pages/library/paper/index?series_id=' + id,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.reloadData()
  },

  reloadData: function() {
    const _this = this
    post('/api/wx/series/list').then(res => {
      wx.stopPullDownRefresh()
      _this.setData({serieses: res.response})
    }).catch(err => {
      debug(err)
      wx.stopPullDownRefresh()
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