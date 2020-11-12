const { post } = require('../../../utils/api')
const { debug } = require('../../../utils/debug')
import Toast from "../../../vant/toast/toast"

Page({

  data: {
    types: []
  },

  onLoad: function (options) {
    this.reloadData()
  },

  reloadData() {
    const _this = this
    post('/api/wx/resource-type/list').then(res => {
      debug(res)
      _this.setData({ types: res.response })
      wx.stopPullDownRefresh();
    }).catch(err => {
      debug(err)
    })
  },

  onItemClick(e) {
    const index = e.currentTarget.dataset.index
    const type_ = this.data.types[index]
    const needVip = type_.needVip
    const role = wx.getStorageSync("role")
    if (needVip && role != 'vip') {
      Toast.fail("该内容只有会员才能享用")
      return
    }
    const typeId = this.data.types[index].id
    wx.navigateTo({
      url: '/pages/resource/list/index?type_id=' + typeId,
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
    this.reloadData()
  },

  onReachBottom: function () {

  },

  onShareAppMessage: function () {

  }
})