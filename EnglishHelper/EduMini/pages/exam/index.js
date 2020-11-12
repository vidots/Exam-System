const {
  jsonPost,
  post
} = require("../../utils/api.js")
const { debug } = require('../../utils/debug')
import Notify from '../../vant/notify/notify'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperId: '',
    questions: [],
    current: 0,
    answers: [], // 不按照顺序
    questionCount: 0,
    currentQuestion: {},
    chooseOne: false,
    chooseTwo: false,
    chooseThree: false,
    chooseFour: false,
    showAnalysis: false,
    rightAnswer: '',
    scrollviewHeight: 0,
    isSubmit: false,
    isShowPopup: false,
    myAnswer: '',
    showSheet: false,
    actions: [
      {loading: true, name: '' }
    ]
  },

  onClose() {
    this.setData({
      isShowPopup: false
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      paperId: options.paper_id
    })
    const _this = this;
    post("/api/wx/paper/" + this.data.paperId)
      .then(res => {
        debug(res)
        const questions = res.response.questions
        const answers = res.response.userAnswers
        const questionCount = questions.length
        const answerCount = answers.length
        const current = questionCount == answerCount ? 0 : answerCount
        const currentQuestion = questions[current]
        const rightAnswer = currentQuestion.answer.toUpperCase().split("").join("、")
        _this.setData({
          questions: questions,
          questionCount: questionCount,
          answers: answers,
          current: current, // 如果用户答完题了，那么从第一道题开始
          currentQuestion: currentQuestion,
          rightAnswer: rightAnswer,
          isSubmit: questionCount === answerCount
        })
        this.initAnswer(_this.getMyAnswer(current))
      })
  },
  clickOne(e) {
    if (this.data.isSubmit) {
      Notify("该试卷已经做完啦，只能查看哦")
      return
    }
    const selected = !this.data.chooseOne
    this.setData({
      chooseOne: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseTwo: false,
        chooseThree: false,
        chooseFour: false
      })
    }
  },
  clickTwo(e) {
    if (this.data.isSubmit) {
      Notify("该试卷已经做完啦，只能查看哦")
      return
    }
    const selected = !this.data.chooseTwo
    this.setData({
      chooseTwo: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseThree: false,
        chooseFour: false
      })
    }
  },
  clickThree(e) {
    if (this.data.isSubmit) {
      Notify("该试卷已经做完啦，只能查看哦")
      return
    }
    const selected = !this.data.chooseThree
    this.setData({
      chooseThree: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseTwo: false,
        chooseFour: false
      })
    }
  },
  clickFour(e) {
    if (this.data.isSubmit) {
      Notify("该试卷已经做完啦，只能查看哦")
      return
    }
    const selected = !this.data.chooseFour
    this.setData({
      chooseFour: selected
    })
    if (this.data.currentQuestion.category == 0 && selected) {
      this.setData({
        chooseOne: false,
        chooseTwo: false,
        chooseThree: false
      })
    }
  },
  jumpToIndex(e) {
    const targetIndex = e.currentTarget.dataset.index
    const targetQuestion = this.data.questions[targetIndex]
    const rightAnswer = targetQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: targetIndex,
      rightAnswer: rightAnswer,
      currentQuestion: targetQuestion,
      isShowPopup: false
    })
    this.initAnswer(this.getMyAnswer(targetIndex))
  },
  // 根据获取指定索引的题目的做的答案
  getMyAnswer(current) {
    if (this.data.answers.length <= current) return ""
    return this.data.answers[current].answer
  },
  // 计算Scrollview的高度
  computeScrollViewHeight() {
    let that = this
    let query = wx.createSelectorQuery().in(this)
    query.select('.footer-group').boundingClientRect(function(res) {
      let footerHeight = res.height
      //获取屏幕可用高度
      let screenHeight = wx.getSystemInfoSync().windowHeight
      let scrollHeight = screenHeight - footerHeight - 24
      that.setData({
        scrollviewHeight: scrollHeight
      })
    }).exec()
  },

  // 是否显示答案
  startAnalyze(e) {
    this.setData({
      showAnalysis: !this.data.showAnalysis
    })
  },

  // 初始化用户作的答案：从服务器获取、点击上一题、下一题
  initAnswer(answer) {
    this.setData({
      chooseOne: answer.indexOf('A') > -1,
      chooseTwo: answer.indexOf('B') > -1,
      chooseThree: answer.indexOf('C') > -1,
      chooseFour: answer.indexOf('D') > -1,
      myAnswer: answer.split("").join("、")
    })
  },

  //点击上一题的操作
  preQuestion(e) {
    if (this.data.current == 0) {
      Notify("已经是第一题了")
      return
    }
    if (!this.data.isSubmit) {
      // 获取用户当前的答案
      const canswer = this.getStorageAnswer()
      if (canswer == "") {
        Notify("当前题目答完才能进入下一步哦")
        return
      } else if (this.data.currentQuestion.category == 1 && canswer.length < 2) {
        Notify("此题为多选题，题目答完才能进入下一步哦")
        return
      }
      this.insertCurrentAnswer(canswer)
    }

    const preIndex = this.data.current - 1
    const preQuestion = this.data.questions[preIndex]
    const rightAnswer = preQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: preIndex,
      rightAnswer: rightAnswer,
      currentQuestion: preQuestion,
      showAnalysis: false
    })
    this.initAnswer(this.getMyAnswer(preIndex))
  },
  // 点击下一题的操作
  nextQuestion(e) {
    if (!this.data.isSubmit) {
      //获取用户当前的答案
      const canswer = this.getStorageAnswer()
      if (canswer == "") {
        Notify("当前题目答完才能进入下一步哦")
        return
      } else if (this.data.currentQuestion.category == 1 && canswer.length < 2) {
        Notify("此题为多选题，题目答完才能进入下一步哦")
        return
      }
      this.insertCurrentAnswer(canswer)
    }
    if (this.data.current == this.data.questions.length - 1) {
      if (!this.data.isSubmit) {
        this.submitAnswers()
        this.setData({
          isSubmit: true
        })
      }
      this.setData({
        isShowPopup: true
      })
      return
    }
    const nextIndex = this.data.current + 1
    const nextQuestion = this.data.questions[nextIndex]
    const rightAnswer = nextQuestion.answer.toUpperCase().split("").join("、")
    this.setData({
      current: nextIndex,
      rightAnswer: rightAnswer,
      currentQuestion: nextQuestion,
      showAnalysis: false
    })
    this.initAnswer(this.getMyAnswer(nextIndex))
  },


  // 用户点击上一题、下一题插入当前答案
  insertCurrentAnswer(answerStr) {
    if (this.data.answers.length <= this.data.current) {
      this.setData({
        answers: this.data.answers.concat([{
          questionId: this.data.currentQuestion.id,
          paperId: this.data.paperId,
          answer: answerStr
        }])
      })
    } else {
      const answers = this.data.answers
      const answerKey = 'answers[' + this.data.current + '].answer'
      this.setData({
        [answerKey]: answerStr
      })
    }
  },

  // 获取用户当前做的答案：以AB的形式
  getStorageAnswer() {
    const answer = (this.data.chooseOne ? "A" : "") +
      (this.data.chooseTwo ? "B" : "") +
      (this.data.chooseThree ? "C" : "") +
      (this.data.chooseFour ? "D" : "")
    return answer
  },

  submitAnswers() {
    jsonPost("/api/wx/paper/" + this.data.paperId + "/submit", JSON.stringify(this.data.answers))
      .then(res => {
        debug(res)
      })
      .catch(err => {
        debug(err)
      })
  },
  showMore: function(e) {
      this.setData({showSheet: true, actions: [
        { loading: true, name: '' }
      ]})
      const _this = this
    post("/api/wx/collect/is", {questionId: this.data.currentQuestion.id})
    .then(res => {
      debug(res)
      if(res.code == 200) {
        _this.setData({actions: [
          { loading: false, name: res.response == "true" ? "取消收藏" : "收藏" }
        ]})
      }
    }).catch(err => {
      debug(err)
    })
  },
  onClose: function() {
      this.setData({showSheet: false})
  },
  onSelect: function(e) {
    if(e.detail.name == "收藏") {
      post("/api/wx/collect/add", {
        questionId: this.data.currentQuestion.id
      }).then(res => {
        if(res.code == 200) {

        } else {
          debug(res.response)
        }
      }).catch(err => {
        debug(err)
      })
    } else if(e.detail.name == "取消收藏") {
      post("/api/wx/collect/cancel", {
        questionId: this.data.currentQuestion.id
      }).then(res => {
        if (res.code == 200) {

        } else {
          debug(res.response)
        }
      }).catch(err => {
        debug(err)
      }) 
    } 
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    this.computeScrollViewHeight();
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  showCard: function() {
    this.setData({
      isShowPopup: true
    })
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
    if (!this.data.isSubmit) {
      this.submitAnswers()
    }
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