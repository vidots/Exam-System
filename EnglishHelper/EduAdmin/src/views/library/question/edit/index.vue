<template>
  <div class="form-container">
    <div  style="margin-top: 12px;margin-bottom: 12px">
      {{ paperName }}
    </div>
    <el-divider />
    <div class="form-add">
      <el-form :model="item" label-position="left" label-width="180px">
        <el-form-item label="类型">
          <el-select v-model="item.category" style="margin-right: 30px">
            <el-option
              v-for="item in categories"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="题干">
          <el-input v-model="item.content" class="input-item" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="选项A">
          <el-input v-model="item.optionOne" class="input-item" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="选项B">
          <el-input v-model="item.optionTwo" class="input-item" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="选项C">
          <el-input v-model="item.optionThree" class="input-item" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="选项D">
          <el-input v-model="item.optionThree" class="input-item" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="答案">
          <el-checkbox-group v-model="options">
            <el-checkbox label="A" />
            <el-checkbox label="B" />
            <el-checkbox label="C" />
            <el-checkbox label="D" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="解析">
          <el-input v-model="item.analysis" class="input-item" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { updateQuestion, getQuestion } from '@/api/question'
import { getPaper } from '@/api/paper'

export default {
  name: 'Index',
  data() {
    return {
      categories: [
        { value: 0, label: '单选题' },
        { value: 1, label: '多选题' }
      ],
      options: [],
      paperName: '',
      item: {
        id: '',
        paperId: '',
        content: '',
        optionOne: '',
        optionTwo: '',
        optionThree: '',
        optionFour: '',
        answer: '',
        category: 0,
        analysis: ''
      }
    }
  },
  mounted() {
    const _this = this
    _this.item.paperId = _this.$route.params.paperId
    _this.item.id = _this.$route.params.questionId
    _this.fetchPaper()
    _this.fetchQuestion()
  },
  methods: {
    fetchQuestion() {
      const _this = this
      getQuestion(_this.item.id)
        .then(res => {
          if (res.code === 200) {
            _this.item = res.response
            if (_this.item.answer.indexOf('A') > -1) _this.options.push('A')
            if (_this.item.answer.indexOf('B') > -1) _this.options.push('B')
            if (_this.item.answer.indexOf('C') > -1) _this.options.push('C')
            if (_this.item.answer.indexOf('D') > -1) _this.options.push('D')
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    fetchPaper() {
      const _this = this
      getPaper(_this.item.paperId)
        .then(res => {
          if (res.code === 200) {
            _this.paperName = res.response.name
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    onUpdate: function() {
      this.executeAddUpdate()
    },
    executeAddUpdate: function() {
      const _this = this
      _this.item.answer = ''
      // eslint-disable-next-line eqeqeq
      if (_this.item.category == 0 && _this.options.length > 1) {
        _this.$message({
          showClose: true,
          message: '单选题只有一个答案',
          type: 'error'
        })
        return
      }
      // eslint-disable-next-line eqeqeq
      if (_this.item.category == 1 && _this.options.length < 2) {
        _this.$message({
          showClose: true,
          message: '多选题不止一个答案',
          type: 'error'
        })
        return
      }
      _this.item.answer = this.options.join("")
      if (_this.item.content === '' || _this.item.optionOne === '' || _this.item.optionTwo === '' ||
          _this.item.optionThree === '' || _this.item.optionFour === '' || _this.item.answer === ''
      ) {
        _this.$message({
          showClose: true,
          message: '确保填写的信息完整',
          type: 'error'
        })
        return
      }
      updateQuestion(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，更新成功')
            _this.item.content = ''
            _this.item.optionOne = ''
            _this.item.optionTwo = ''
            _this.item.optionThree = ''
            _this.item.optionFour = ''
            _this.options = []
            this.$router.push('/library/' + _this.item.paperId + '/question/list')
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    }
  }
}
</script>

<style scoped>
  .form-check {
    text-align: center;
    margin-top: 24px;
  }
  .form-add {
    padding-left: 36px;
    padding-right: 36px;
  }
  .form-container {
    padding-left: 24px;
    padding-right: 24px;
  }
  .input-item {
    width: 640px;
  }
</style>
