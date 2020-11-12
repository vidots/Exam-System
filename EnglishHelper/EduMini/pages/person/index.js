const { post } = require('../../utils/api');
const { debug } = require('../../utils/debug')
import Toast from "../../vant/toast/toast"


Page({

  data: {
      name: '',
      imageUrl: '',
      vip: false,
      showDialog: false,
      showListNum: false,
      vipCode: '',
      listNum: 50,
  },

  clickVIP: function(e) {
    if(this.data.vip) return
    this.setData({showDialog: true})
  },

  clickAbout: function(e) {
    wx.navigateTo({
      url: '/pages/dynamic/index?subject=about',
    })
  },
  clickUpate: function(e) {
    if(wx.canIUse('getUpdateManager')) {
      const updateManager = wx.getUpdateManager()
      updateManager.onCheckForUpdate(function(res) {
        if(res.hasUpdate) {
          updateManager.onUpdateReady(function() {
            wx.showModal({
              title: '更新提示',
              content: '新版本已经准备好，是否重启应用',
              success: function(res) {
                if(res.confirm) {
                  updateManager.applyUpdate()
                }
              }
            })
          })
        } else {
          Toast.success("当前是最新版本")
        }
      })
      updateManager.onUpdateFailed(function() {
        wx.showModal({
          title: '已经有新版本了哦',
          content: '新版本已经上线啦，请你删除当前小程序，重新搜索打开哦'
        })
      })
    }
  },
  setWordNum: function(e) {
    this.setData({showListNum: true})
  },
  enterLibrary: function(e) {
    wx.navigateTo({
      url: '/pages/library/series/index',
    })
  },
  clickObligation: function(e) {
    wx.navigateTo({
      url: '/pages/dynamic/index?subject=duty',
    })
  },
  enterCollect: function(e) {
    wx.navigateTo({
      url: '/pages/collect/index',
    })
  },
  enterResource: function(e) {
    wx.navigateTo({
      url: '/pages/resource/type/index',
    })
  },

  onChange: function(e) {
    this.setData({vipCode: e.detail})
  },
  setListNum: function(e) {
   this.setData({listNum: e.detail})
  },
  onConfirmListNum: function(e) {
    if(!/^\d+$/.test(this.data.listNum)) {
      Toast.fail("请填写正确的字体")
      return
    }
    const size = parseInt(this.data.listNum)
    if(size < 10 || size > 100) {
      Toast.fail("单词数量应在10-100之间")
      return
    }
    wx.setStorageSync('listSize', size)
  },
  closeDialog: function(e) {
    this.setData({showModal: false})
  },
  closeListNum: function(e) {
    this.setData({showListNum: false})
  },
  onConfirm: function(e) {
    const _this = this
    const code = this.data.vipCode
    if(code == '') {
      Toast.fail("激活码不能为空")
      return
    }
    post("/api/wx/become-vip", { vipCode: code})
          .then(res => {
              wx.setStorageSync("role", "vip")
              _this.setData({vip: true, vipCode: ''})
              Toast.success("您已经成为会员")
          })
          .catch(err => {
            debug("发生错误",err)
            _this.setData({ vipCode: '' })
            Toast.fail(err.message)
          })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    let role = wx.getStorageSync("role")
    let listNum = wx.getStorageSync('listSize')
    if(role != "vip") {
      this.verifyVIP()
    } else {
      this.setData({ vip: role == "vip" })
    }
    if(listNum == null || listNum == "") {
      listNum = 50
    }
    this.setData({listNum: listNum})
    // 从本地取数据
    let name = wx.getStorageSync("name")
    let imageUrl = wx.getStorageSync("imageUrl")
    if (name != '' && imageUrl != '') {
      this.setData({
        name: name,
        imageUrl: imageUrl
      })
      return
    }
    const _this = this
    wx.getSetting({
      success(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function (res) {
              let name = res.userInfo.nickName
              let avatar = res.userInfo.avatarUrl
              _this.setData({
                name:  name,
                imageUrl: avatar
              })
              wx.setStorageSync("name", name)
              wx.setStorageSync("imageUrl", avatar)
              _this.modifyName(name)
            }
          })
        }
      }
    })

  },

 verifyVIP() {
   const that = this
   post("/api/wx/user-role")
     .then(res => {
       const userRole = res.response
       wx.setStorageSync("role", userRole)
       that.setData({ vip: userRole == "vip"})
       wx.stopPullDownRefresh();
     })
     .catch(err => {
       debug(err)
     })
 },

 modifyName(name) {
   post("/api/wx/modify-name", { name: name })
     .then(res => {
       debug(res)
     })
     .catch(err => {
       debug(err)
     })
 },

  getUserInfo(e) {
    let name = e.detail.userInfo.nickName
    let avatar = e.detail.userInfo.avatarUrl
    this.setData({
      name: name,
      imageUrl: avatar
    })
    wx.setStorageSync("name", name)
    wx.setStorageSync("imageUrl", avatar)
    this.modifyName(name)
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
      this.verifyVIP()
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