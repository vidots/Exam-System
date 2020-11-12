<template>
  <div>
    <div style="text-align: center;margin-top: 12px;margin-bottom: 12px">
      {{ paperName }}
    </div>
    <div class="form-container">
      <el-form :model="params">
        <el-input v-model="params.query" placeholder="输入关键字" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchQuestions">查询</el-button>
        <router-link
          :to="{path:'/library/' + params.paperId + '/question/add'}"
        >
          <el-button type="primary" style="float: right">新增问题</el-button>
        </router-link>
        <el-button @click="previewPaper" type="primary" style="float: right; margin-right: 12px">预览试卷</el-button>
      </el-form>
    </div>
    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="questionList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="题干名称" align="center">
          <template slot-scope="scope">
            {{ truncate(scope.row.content) }}
          </template>
        </el-table-column>
        <el-table-column label="类型" width="80" align="center">
          <template slot-scope="scope">
            {{ scope.row.category === 0 ? '单选' : '多选' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editQuestion(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="removeQuestion(scope.row.id)"
            >删除
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="previewQuestion(scope.row)"
            >预览
            </el-button>
          </template>

        </el-table-column>
      </el-table>
      <el-pagination
        layout="prev, pager, next"
        :total="total"
        :page-size="params.size"
        :current-page="params.page"
        style="float:right"
        @current-change="changePage"
      />
    </div>
    <el-dialog :visible.sync="fullVisible" width="80%">
      <div>
        <div  v-for="(item, index) in allQuestions" :key="item.id" style="margin-bottom: 12px">
          <el-card>
            <div style="margin-bottom: 12px">{{ index + 1 }}. {{ item.content }}</div>
            <div style="margin-bottom: 12px"> A.{{ item.optionOne }}</div>
            <div style="margin-bottom: 12px"> B.{{ item.optionTwo }}</div>
            <div style="margin-bottom: 12px"> C.{{ item.optionThree }}</div>
            <div style="margin-bottom: 12px"> D.{{ item.optionFour }}</div>
            <div style="margin-bottom: 12px"> 答案：{{ item.answer }}</div>
            <div style="margin-bottom: 12px"> 分析：{{ item.analysis }}</div>
          </el-card>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getQuestionList, deleteQuestion } from '@/api/question'
import { getPaper } from '@/api/paper'
export default {
  data() {
    return {
      paperName: '',
      questionList: [],
      allQuestions: [],
      total: 0,
      listLoading: true,
      fullVisible: false,
      params: {
        paperId: '',
        query: '',
        page: 1,
        size: 6
      }
    }
  },
  computed: {
    emptyData: function() {
      return this.questionList.length === 0
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.params.paperId = this.$route.params.paperId
    _this.fetchPaper()
    _this.fetchQuestions()
  },
  methods: {
    truncate(questionStr = '') {
      const len = questionStr.length
      const MAX_LEN = 80
      if (len < MAX_LEN) return questionStr
      return questionStr.substring(0, MAX_LEN / 2) + ' ................ ' + questionStr.substring(len - MAX_LEN / 2, len)
    },
    fetchPaper() {
      const _this = this
      getPaper(_this.params.paperId)
        .then(res => {
          if (res.code === 200) {
            _this.paperName = res.response.name
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    fetchQuestions: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      obj.paperId = this.params.paperId
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getQuestionList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.questionList = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    changePage: function(page) {
      this.params.page = page
      this.fetchQuestions()
    },
    editQuestion: function(id) {
      this.$router.push({ name: 'edit_question', params: { questionId: id, paperId: this.params.paperId }})
    },
    removeQuestion: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteQuestion(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchQuestions()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewQuestion: function(row) {
      console.log(row)
    },
    previewPaper: function () {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = 1
      obj.size = 100
      obj.paperId = this.params.paperId
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getQuestionList(obj).then(res => {
        if (res.code === 200) {
          _this.allQuestions = res.response.records
          _this.listLoading = false
          this.fullVisible = true
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    }
  }
}
</script>
<style>
  .table-container, .form-container {
    margin-top: 24px;
    margin-left: 45px;
    margin-right: 45px;
  }
</style>
