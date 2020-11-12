const app = getApp();
const { post } = require('../../../utils/api')
const { debug } = require('../../../utils/debug')
import Toast from "../../../vant/toast/toast"
Page({

  /**
   * 页面的初始数据
   */
  data: {
    types: [],
    books: [],
    active: 0
  },

  onTypeClick(e) {
    const index = e.currentTarget.dataset.index
    const type_ = this.data.types[index]
    const id = type_.id
    const needVip = type_.needVip
    const role = wx.getStorageSync("role")
    if (needVip && role != 'vip') {
      Toast.fail("该内容只有会员才能享用")
      return
    }
    wx.navigateTo({
      url: '/pages/camp/articleList/index?type_id=' + id,
    })
  },
  onBookClick(e) {
    const index = e.currentTarget.dataset.index
    const book = this.data.books[index]
    const id = book.id
    const needVip = book.needVip
    const role = wx.getStorageSync("role")
    if (needVip && role != 'vip') {
      Toast.fail("该内容只有会员才能享用")
      return
    }
    wx.navigateTo({
      url: '/pages/camp/chapterList/index?book_id=' + id,
    })
  },
  onChange(event) {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.reloadData(options)
  },

reloadData(options) {
  const _this = this
  post('/api/wx/article-type/list').then(res => {
    debug(res)
    _this.setData({ types: res.response })
    wx.stopPullDownRefresh();
  }).catch(err => {
    debug(err)
  })
  post('/api/wx/book/list').then(res => {
    debug(res)
    _this.setData({ books: res.response })
    wx.stopPullDownRefresh();
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