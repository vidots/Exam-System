<template>
  <div class="form-container">
    <div class="form-add">
      <el-form :model="item" label-position="top" label-width="180px">
        <el-form-item label="单词">
          <el-input v-model="item.word" class="input-item" />
        </el-form-item>
        <el-form-item label="音标">
          <el-input v-model="item.symbol" class="input-item" />
        </el-form-item>
        <el-form-item label="词义一">
          <el-input v-model="item.translationOne" class="input-item" />
        </el-form-item>
        <el-form-item label="词义二">
          <el-input v-model="item.translationTwo" class="input-item" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onUpdate">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { updateWord, getWord } from '@/api/word'

export default {
  name: 'Index',
  data() {
    return {
      repeat: '',
      item: {
        id: '',
        word: '',
        symbol: '',
        translationOne: '',
        translationTwo: ''
      }
    }
  },
  mounted() {
    this.item.id = this.$route.params.wordId
    this.fillWord()
  },
  methods: {
    fillWord: function() {
      getWord(this.item.id).then(res => {
        if (res.code === 200) {
          if (res.response === undefined) {
            this.$message('主人，你一定操作失误啦，该单词不存在')
            this.item.id = ''
          } else {
            this.item.word = res.response.word
            this.item.symbol = res.response.symbol
            this.item.translationOne = res.response.translationOne
            this.item.translationTwo = res.response.translationTwo
          }
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
      })
    },
    onUpdate: function() {
      this.executeUpdateWord()
    },
    executeUpdateWord: function() {
      const _this = this
      if (_this.item.word === '' || _this.item.symbol === '' || _this.item.translationOne === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的单词完整',
          type: 'error'
        })
        return
      }
      updateWord(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$router.push('/english/word/list')
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    }
  }
}
</script>

<style scoped>

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

