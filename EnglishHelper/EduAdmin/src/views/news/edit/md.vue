<template>
  <div class="form-container">
    <div class="article-container">
      <el-input v-model="article.title" placeholder="请输入文章标题" style="width: 540px" />
      <el-select v-model="article.typeId" placeholder="选择文章类型">
        <el-option
          v-for="item in articleTypes"
          :key="item.id"
          :label="item.typeName"
          :value="item.id + ''"
        />
      </el-select>
      <el-button @click="editArticle">发布</el-button>
    </div>
    <el-input type="textarea" rows="24" v-model="article.content" />
    <div style="text-align: center; margin: 12px">
      青春是一个短暂的美梦, 当你醒来时, 它早已消失无踪
    </div>
    <el-input v-model="article.summary" placeholder="输入文章摘要" rows="6" type="textarea" style="margin-bottom: 36px" />
  </div>
</template>

<script>
import { getArticle, updateArticle } from '@/api/article'
import { getATList } from '@/api/article_type'

export default {
  name: 'Index',
  data() {
    return {
      article: {
        typeId: '',
        title: '',
        summary: '',
        id: '',
        content: '',
        format: 'markdown'
      },
      articleTypes: []
    }
  },
  mounted() {
    this.article.id = this.$route.params.articleId
    this.fetchArticleTypes(id => {
      this.getArticleById(id)
    })
  },
  methods: {
    editArticle: function() {
      const _this = this
      if (_this.article.title === '' || _this.article.typeId === '' || _this.article.content === '') {
        _this.$message({
          showClose: true,
          message: '文章标题和文章类别不能为空',
          type: 'error'
        })
        return
      }
      updateArticle(_this.article)
        .then(res => {
          if (res.code === 200) {
            _this.$message('主人，添加成功')
            _this.$router.push('/article/list')
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    fetchArticleTypes: function(callback) {
      getATList().then(res => {
        if (res.code === 200) {
          this.articleTypes = res.response
          callback(this.article.id)
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
      })
    },
    getArticleById: function(id) {
      getArticle(id).then(res => {
        if (res.code === 200) {
          if (res.response === undefined) {
            this.$message('主人，你一定操作失误啦，该文章不存在')
            this.article.id = ''
          } else {
            this.article.content = res.response.content
            this.article.title = res.response.title
            this.article.summary = res.response.summary
            this.article.typeId = res.response.typeId + ''
            this.article.format = res.response.format
          }
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
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
  .article-container {
    margin: 12px 24px 24px;
  }
</style>
