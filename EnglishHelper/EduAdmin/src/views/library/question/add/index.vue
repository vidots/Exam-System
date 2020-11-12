<template>
  <div class="form-container">
    <div class="form-check">
      {{ paperName }}
    </div>
    <el-divider/>
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
          <el-input class="input-item" type="textarea" :rows="3" v-model="item.content"></el-input>
        </el-form-item>
        <el-form-item label="选项A">
          <el-input class="input-item" type="textarea" :rows="2" v-model="item.optionOne"></el-input>
        </el-form-item>
        <el-form-item label="选项B">
          <el-input class="input-item" type="textarea" :rows="2" v-model="item.optionTwo"></el-input>
        </el-form-item>
        <el-form-item label="选项C">
          <el-input class="input-item" type="textarea" :rows="2" v-model="item.optionThree"></el-input>
        </el-form-item>
        <el-form-item label="选项D">
          <el-input class="input-item" type="textarea" :rows="2" v-model="item.optionFour"></el-input>
        </el-form-item>
        <el-form-item label="答案">
          <el-checkbox-group v-model="options">
            <el-checkbox label="A"></el-checkbox>
            <el-checkbox label="B"></el-checkbox>
            <el-checkbox label="C"></el-checkbox>
            <el-checkbox label="D"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="解析">
          <el-input class="input-item" type="textarea" :rows="3" v-model="item.analysis"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onAdd">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addQuestion } from '@/api/question'
import { getPaper } from '@/api/paper'

export default {
  name: 'Index',
  mounted() {
    const _this = this
    _this.item.paperId = _this.$route.params.paperId
    _this.fetchPaper()
  },
  data() {
    return {
      categories: [
        { value: 0, label: '单选题' },
        { value: 1, label: '多选题' }
      ],
      options: [],
      paperName: '',
      item: {
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
  methods: {
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
    onAdd: function() {
      this.executeAddWord()
    },
    executeAddWord: function() {
      const _this = this
      _this.item.answer = _this.options.join('')
      if (_this.item.category === 0 && _this.item.answer.length > 1) {
        _this.$message({
          showClose: true,
          message: '单选题只有一个答案',
          type: 'error'
        })
        return
      }
      if (_this.item.category === 1 && _this.item.answer.length === 1) {
        _this.$message({
          showClose: true,
          message: '多选题答案多于一个',
          type: 'error'
        })
        return
      }
      if (_this.item.content === '' || _this.item.optionOne === '' || _this.item.optionTwo === '' ||
        _this.item.optionThree === '' || _this.item.optionFour === '' || _this.item.answer === ''
      ) {
        _this.$message({
          showClose: true,
          message: '确保填写的题目完整',
          type: 'error'
        })
        return
      }
      addQuestion(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.item.content = ''
            _this.item.optionOne = ''
            _this.item.optionTwo = ''
            _this.item.optionThree = ''
            _this.item.optionFour = ''
            _this.item.analysis = ''
            _this.options = []
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
