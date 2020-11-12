const {login, post} = require("./utils/api.js")
const {debug} = require('./utils/debug')
App({
  globalData: {
  },
  towxml: require('/towxml/index'),
  onLaunch: function() {
    let _this = this
    let token = wx.getStorageSync("token")
    debug(token)
    if(null == token || token == "") {
      // 两次登录机会
      login(msg => {
        if(msg == null) {
          _this.verifyVIP()
          wx.reLaunch({
            url: '/pages/words/type/index',
          })
        } else {
          debug("登录失败：" + msg)
        }
      })
    } else {
      this.verifyVIP()
    }
  },
  verifyVIP() {
    const that = this
    post("/api/wx/user-role")
      .then(res => {
        const userRole = res.response
        wx.setStorageSync("role", userRole)
      })
      .catch(err => {
        debug(err)
      })
  }
})