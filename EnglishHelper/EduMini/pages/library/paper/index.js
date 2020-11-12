const {post} = require('../../../utils/api');
const { debug } = require('../../../utils/debug')
Page({

  data: {
    seriesId: -1,
    papers: [],
    finish: [],
    total: []
  },

  onLoad: function(options) {
    const seriesId = options.series_id
    const _this = this
    post('/api/wx/paper/list?seriesId=' + seriesId).then(res => {
      let finish = []
      for(let i = 0; i < res.response['total'].length; i++) {
        let paperId = res.response['total'][i].paperId
        let blank = {paperId: paperId, finish: 0}
        for(let j = 0; j < res.response['finish'].length; j ++) {
          let f_paperId = res.response['finish'][j].paperId
          if(paperId == f_paperId) {
            let item = res.response['finish'][j]
            blank.finish = item['finish']
            break
          }
        }
        finish.push(blank)
      }
      _this.setData({
        papers: res.response['papers'],
        finish: finish,
        total: res.response['total']
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