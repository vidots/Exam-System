<template>
  <div class="form-container">
    <div class="news-container">
      <el-input placeholder="请输入文章标题" v-model="news.title" style="width: 540px"/>
      <el-button @click="addNews" >发布</el-button>
    </div>
    <editor id="tinymceEditor" v-model="content" :init="initObj" />
    <div style="text-align: center; margin: 12px">
      青春是一个短暂的美梦, 当你醒来时, 它早已消失无踪
    </div>
    <el-input v-model="news.summary" placeholder="输入文章摘要" rows="6" type="textarea" style="margin-bottom: 36px" />
  </div>
</template>

<script>
import { addNews } from '@/api/news'
const { apiUrl } = require('@/change')

// eslint-disable-next-line no-unused-vars
import tinymce from 'tinymce/tinymce'
import Editor from '@tinymce/tinymce-vue'
import 'tinymce/plugins/fullscreen'
import 'tinymce/plugins/preview'
import 'tinymce/themes/silver'
import 'tinymce/plugins/textcolor'
import 'tinymce/plugins/advlist'
import 'tinymce/plugins/table'
import 'tinymce/plugins/lists'
import 'tinymce/plugins/paste'
import 'tinymce/plugins/image'
import 'tinymce/plugins/link'
import 'tinymce/plugins/imagetools'

export default {
  name: 'Index',
  components: {
    editor: Editor
  },
  mounted() {
    this.fetchNewsTypes()
  },
  data() {
    return {
      content: '',
      news: {
        title: '',
        summary: '',
        format: 'html'
      },
      initObj: {
        skin_url: `/tinymce/skins/ui/oxide`,
        language_url: `/tinymce/langs/zh_CN.js`,
        language: 'zh_CN',
        height: 360,
        browser_spellcheck: true, // 拼写检查
        branding: false, // 去水印
        statusbar: false, // 隐藏编辑器底部的状态栏
        paste_data_images: true, // 允许粘贴图像
        menubar: false, // 隐藏最上方menu
        plugins: 'advlist table lists paste preview fullscreen image imagetools link',
        toolbar: 'fontsizeselect forecolor backcolor bold italic underline strikethrough removeformat | alignleft aligncenter alignright alignjustify | link image numlist bullist preview fullscreen',
        images_upload_handler: function(blobInfo, success, failure) {
          const xhr = new XMLHttpRequest()
          xhr.withCredentials = false
          xhr.open('POST', apiUrl + '/media/upload/image')
          xhr.onload = function() {
            if (xhr.status !== 200) {
              failure('发生错误， 请稍后再试')
              return
            }
            const json = JSON.parse(xhr.responseText)
            if (!json || typeof json.response !== 'string') {
              failure('服务器返回的数据错误')
              return
            }
            success(json.response)
          }
          const formData = new FormData()
          formData.append('image', blobInfo.blob(), blobInfo.filename())
          xhr.send(formData)
        }
      }
    }
  },
  methods: {
    addNews: function() {
      const data = { content: this.content }
      Object.assign(data, this.news)
      const _this = this
      if (data.title === '') {
        _this.$message({
          showClose: true,
          message: '文章标题和文章类别不能为空',
          type: 'error'
        })
        return
      }
      addNews(data)
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
