<template>
  <div class="form-container">
    <div class="form-add">
      <el-form :model="item" label-position="left" label-width="120px">
        <el-form-item label="句子">
          <el-input class="input-item" :rows="4"  type="textarea" v-model="item.sentence"></el-input>
        </el-form-item>
        <el-form-item label="翻译">
          <el-input class="input-item" :rows="4" type="textarea" v-model="item.translation"></el-input>
        </el-form-item>
        <el-form-item label="来源">
          <el-input class="input-item" v-model="item.source"></el-input>
        </el-form-item>
        <el-form-item label="所属">
          <el-select v-model="item.category" placeholder="请选择所属类别">
            <el-option label="考研" value="1"></el-option>
            <el-option label="其它" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onAdd">添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addSentence } from '@/api/sentence'

export default {
  name: 'Index',
  mounted() {
  },
  data() {
    return {
      item: {
        sentence: '',
        translation: '',
        source: '',
        category: '1'
      }
    }
  },
  methods: {
    onAdd: function() {
      this.executeAddSentence()
    },
    executeAddSentence: function() {
      const _this = this
      if (_this.item.sentence === '' || _this.item.translation === '' || _this.item.source === '') {
        _this.$message({
          showClose: true,
          message: '确保填写的信息完整',
          type: 'error'
        })
        return
      }
      addSentence(_this.item)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.item.sentence = ''
            _this.item.translation = ''
            _this.item.source = ''
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
