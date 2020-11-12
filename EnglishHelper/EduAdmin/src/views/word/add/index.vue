<template>
  <div class="form-container">
    <div class="form-check">
      {{ paperName }}
    </div>
    <el-divider/>
    <div class="form-add">
      <el-form :model="item" label-position="top" label-width="180px">
        <el-form-item label="单词">
          <el-input class="input-item" v-model="item.word"></el-input>
        </el-form-item>
        <el-form-item label="音标">
          <el-input class="input-item" v-model="item.symbol"></el-input>
        </el-form-item>
        <el-form-item label="词义一">
          <el-input class="input-item" v-model="item.translationOne"></el-input>
        </el-form-item>
        <el-form-item label="词义二">
          <el-input class="input-item" v-model="item.translationTwo"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onAdd">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addWord, getWordExist } from '@/api/word'

export default {
  name: 'Index',
  mounted() {
  },
  data() {
    return {
      paperName: '',
      item: {
        word: '',
        symbol: '',
        translationOne: '',
        translationTwo: ''
      }
    }
  },
  methods: {
    checkRepeat: function() {
      const _this = this
      getWordExist(_this.repeat).then(res => {
        if (res.code === 200) {
          if (res.response) {
            _this.$message('主人，该单词已存在于词库中啦')
          } else {
            _this.$message('主人，可以添加该单词啦')
          }
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
      if (_this.item.word === '' || _this.item.symbol === '' || _this.item.translationOne === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的单词完整',
          type: 'error'
        })
        return
      }
      addWord(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.item.word = ''
            _this.item.symbol = ''
            _this.item.translationOne = ''
            _this.item.translationTwo = ''
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
