<template>
  <div class="form-container">
    <div class="news-container">
      <el-input placeholder="请输入文章标题" v-model="news.title" style="width: 540px"/>
      <el-button @click="addNews" >发布</el-button>
    </div>
    <el-input type="textarea" rows="24" v-model="news.content" />
    <div style="text-align: center; margin: 12px">
      青春是一个短暂的美梦, 当你醒来时, 它早已消失无踪
    </div>
    <el-input v-model="news.summary" placeholder="输入文章摘要" rows="6" type="textarea" style="margin-bottom: 36px" />
  </div>
</template>

<script>
import { addNews } from '@/api/news'

export default {
  name: 'Index',
  mounted() {
  },
  data() {
    return {
      news: {
        title: '',
        summary: '',
        content: '',
        format: 'markdown'
      }
    }
  },
  methods: {
    addNews: function() {
      const _this = this
      if (_this.news.title === '' || _this.news.content === '') {
        _this.$message({
          showClose: true,
          message: '请填写完整文章内容',
          type: 'error'
        })
        return
      }
      addNews(_this.news)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.$router.push('/news/list')
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    }
  }
}
</script>

<style scoped>
.form-container {
  padding-left: 36px;
  padding-right: 36px;
  padding-top: 12px;
}
.news-container {
  margin: 12px 24px 24px;
}
</style>
