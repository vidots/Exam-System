<template>
  <div class="form-container">
    <div class="form-add">
      <el-form :model="item" label-position="left" label-width="120px">
        <el-form-item label="句子">
          <el-input v-model="item.sentence" class="input-item" :rows="4" type="textarea" />
        </el-form-item>
        <el-form-item label="翻译">
          <el-input v-model="item.translation" class="input-item" :rows="4" type="textarea" />
        </el-form-item>
        <el-form-item label="来源">
          <el-input v-model="item.source" class="input-item" />
        </el-form-item>
        <el-form-item label="所属">
          <el-select v-model="item.category" placeholder="请选择所属类别">
            <el-option label="考研" value="1" />
            <el-option label="其它" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onUpdate">更新</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { updateSentence, getSentence } from '@/api/sentence'

export default {
  name: 'Index',
  data() {
    return {
      item: {
        id: '',
        sentence: '',
        translation: '',
        source: '',
        category: '1'
      }
    }
  },
  mounted() {
    this.item.id = this.$route.params.sentenceId
    this.fillSentence()
  },
  methods: {
    fillSentence: function() {
      getSentence(this.item.id).then(res => {
        if (res.code === 200) {
          if (res.response === undefined) {
            this.$message('主人，你一定操作失误啦，该句子不存在')
            this.item.id = ''
          } else {
            this.item.sentence = res.response.sentence
            this.item.translation = res.response.translation
            this.item.source = res.response.source
            this.item.category = res.response.category + ''
          }
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
      })
    },
    onUpdate: function() {
      this.executeUpdateSentence()
    },
    executeUpdateSentence: function() {
      const _this = this
      if (_this.item.sentence === '' || _this.item.translation === '' || _this.item.source === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的信息完整',
          type: 'error'
        })
        return
      }
      updateSentence(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$router.push('/english/sentence/list')
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
