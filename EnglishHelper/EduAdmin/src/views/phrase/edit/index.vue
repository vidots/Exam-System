<template>
  <div class="form-container">
    <div class="form-add">
      <el-form :model="item" label-position="top" label-width="180px">
        <el-form-item label="短语">
          <el-input v-model="item.phrase" class="input-item" />
        </el-form-item>
        <el-form-item label="短语翻译">
          <el-input v-model="item.phraseTranslation" class="input-item" />
        </el-form-item>
        <el-form-item label="例句">
          <el-input v-model="item.sentence" class="input-item" />
        </el-form-item>
        <el-form-item label="例句翻译">
          <el-input v-model="item.sentenceTranslation" class="input-item" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onUpdate">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { updatePhrase, getPhrase } from '@/api/phrase'

export default {
  name: 'Index',
  data() {
    return {
      repeat: '',
      item: {
        id: '',
        phrase: '',
        phraseTranslation: '',
        sentence: '',
        sentenceTranslation: ''
      }
    }
  },
  mounted() {
    this.item.id = this.$route.params.phraseId
    console.log(this.item.id)
    this.fillPhrase()
  },
  methods: {
    fillPhrase: function() {
      getPhrase(this.item.id).then(res => {
        if (res.code === 200) {
          if (res.response === undefined) {
            this.$message('主人，你一定操作失误啦，该单词不存在')
            this.item.id = ''
          } else {
            this.item.phrase = res.response.phrase
            this.item.phraseTranslation = res.response.phraseTranslation
            this.item.sentence = res.response.sentence
            this.item.sentenceTranslation = res.response.sentenceTranslation
          }
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
      })
    },
    onUpdate: function() {
      this.executeUpdatePhrase()
    },
    executeUpdatePhrase: function() {
      const _this = this
      if (_this.item.phrase === '' || _this.item.phraseTranslation === '' || _this.item.sentence === '' || _this.item.sentenceTranslation === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的短语完整',
          type: 'error'
        })
        return
      }
      updatePhrase(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$router.push('/english/phrase/list')
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
