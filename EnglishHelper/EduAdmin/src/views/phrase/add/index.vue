<template>
  <div class="form-container">
    <div class="form-add">
      <el-form :model="item" label-position="top" label-width="180px">
        <el-form-item label="短语">
          <el-input class="input-item" v-model="item.phrase"></el-input>
        </el-form-item>
        <el-form-item label="短语翻译">
          <el-input class="input-item" v-model="item.phraseTranslation"></el-input>
        </el-form-item>
        <el-form-item label="例句">
          <el-input class="input-item" v-model="item.sentence"></el-input>
        </el-form-item>
        <el-form-item label="例句翻译">
          <el-input class="input-item" v-model="item.sentenceTranslation"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onAdd">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addPhrase, getPhraseExist } from '@/api/phrase'

export default {
  name: 'Index',
  mounted() {
  },
  data() {
    return {
      item: {
        phrase: '',
        phraseTranslation: '',
        sentence: '',
        sentenceTranslation: ''
      }
    }
  },
  methods: {
    checkRepeat: function() {
      const _this = this
      getPhraseExist(_this.repeat).then(res => {
        if (res.code === 200) {
          if (res.response) {
            _this.$message('主人，该短语已存在于词库中啦')
          } else {
            _this.$message('主人，可以添加该短语啦')
          }
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      })
    },
    onAdd: function() {
      this.executeAddPhrase()
    },
    executeAddPhrase: function() {
      const _this = this
      if (_this.item.phrase === '' || _this.item.phraseTranslation === '' || _this.item.sentence === '' || _this.item.sentenceTranslation === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的短语完整',
          type: 'error'
        })
        return
      }
      addPhrase(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.item.phrase = ''
            _this.item.phraseTranslation = ''
            _this.item.sentence = ''
            _this.item.sentenceTranslation = ''
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
