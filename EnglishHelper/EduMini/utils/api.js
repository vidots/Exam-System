const { debug } = require('./debug')
/***
 * 注意：封装请求的Api：调用时，只有res.code(服务器自定义的代码)为200才会进入then，其余都是走catch
 */
const BASE_URL = "https://www.poplarky.com"
// const BASE_URL = "http://vidots.5gzvip.idcfengye.com"
// const BASE_URL = "http://127.0.0.1:8088"

const CODE_UNAUTHORIZED = 401  // 用戶未登录
const OK = 200  // 请求成功
const InnerError = 500 // 服务器内部错误
const TRYAGIN = 402 // 稍后再试

let logining = false

// 登录：成功-内部保存token，回调参数为null；失败-回调参数是错误信息
const login = function(callback) {
  if(logining) return
  logining = true
  wx.login({
    success(res) {
      if(res.code) {
        post('/api/wx/login', {
          "code": res.code
        }).then(res => {
          if(res.code == OK) {
            debug(res.response)
            wx.setStorageSync("token", res.response.token)
            wx.setStorageSync("role", res.response.role)
            logining = false
            callback(null)
          } else {
            callback(res.message)
            logining = false
          }
        }).catch(msg => {
          callback(msg)
          logining = false
        })
      } else {
        callback(res.errMsg)
      }
    }
  })
}

// 请求过程中，存在一种情况：本地token存在，但是服务端已经过期(CODE_UNAUTHORIZED)，需要再次登录，然后再请求
// 成功resolve的参数是res；失败reject的是消息
const post = function(url, data) {
  return new Promise(function (resolve, reject) {
    wx.showNavigationBarLoading()
    wx.request({
      url: BASE_URL + url,
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'token': wx.getStorageSync('token')
      },
      method: 'POST',
      data,
      success(res) {
        if (res.statusCode != 200 || typeof res.data != 'object') {
          reject('网络出错')
          return false
        }
        if (res.data.code == OK) {
          resolve(res.data)
          return true
        } 
        // 单独处理
        if (res.data.code == CODE_UNAUTHORIZED) {
          debug("token失效")
          login(msg => {
            if(msg == null) {
              wx.request({
                url: BASE_URL + url,
                header: {
                  'content-type': 'application/x-www-form-urlencoded',
                  'token': wx.getStorageSync('token')
                },
                method: 'POST',
                data,
                success(res) {
                  debug(res.data)
                  resolve(res.data)
                  return true
                }
              })
            } else {
              resolve(msg)
            }
          })
        } else {
          reject(res.data)
          return false
        }
        
      },
      fail(res) {
        reject(res.errMsg)
        return false
      },
      complete(res) {
        wx.hideNavigationBarLoading()
      }
    })
  })
}





const jsonPost = function (url, data) {
  return new Promise(function (resolve, reject) {
    wx.showNavigationBarLoading()
    wx.request({
      url: BASE_URL + url,
      header: {
        'token': wx.getStorageSync('token')
      },
      method: 'POST',
      data,
      success(res) {
        if (res.statusCode != 200 || typeof res.data != 'object') {
          reject('网络出错')
          return false
        }
        if (res.data.code == OK) {
          resolve(res.data)
          return true
        }
        // 单独处理
        if (res.data.code == CODE_UNAUTHORIZED) {
          debug("token失效")
          login(msg => {
            if (msg == null) {
              wx.request({
                url: BASE_URL + url,
                header: {
                  'content-type': 'application/x-www-form-urlencoded',
                  'token': wx.getStorageSync('token')
                },
                method: 'POST',
                data,
                success(res) {
                  resolve(res.data)
                  return true
                }
              })
            } else {
              resolve(msg)
            }
          })
        }
        reject(res.data)
        return false
      },
      fail(res) {
        reject(res.errMsg)
        return false
      },
      complete(res) {
        wx.hideNavigationBarLoading()
      }
    })
  })
}

module.exports = {
  login,
  post,
  jsonPost
}