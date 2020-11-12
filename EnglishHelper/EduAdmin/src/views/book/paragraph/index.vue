<template>
  <div class="app-container">
    <div style="text-align: center;margin-top: 12px;margin-bottom: 12px">
      {{ chapterName }}
    </div>
    <div class="form-container">
      <el-form :model="params">
        <el-button type="primary" @click="handleAdd" style="float: right; margin-bottom: 12px">新增段落</el-button>
        <el-button type="primary" @click="previewFull" style="float: right; margin-right: 12px; margin-bottom: 12px">预览文章</el-button>
      </el-form>
    </div>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="paragraphs"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="段落" align="center">
          <template slot-scope="scope">
            <div v-html="truncate(scope.row.content)">
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="previewParagraph(scope.row)">查看章节段落</el-button>
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" width="80%">
      <el-form :model="itemValue">
        <el-form-item label="段落内容" :label-width="labelWidth">
          <el-input type="textarea" rows="4" v-model="itemValue.content"></el-input>
        </el-form-item>
        <el-form-item label="段落解析" :label-width="labelWidth">
          <el-input type="textarea" rows="4" v-model="itemValue.parse"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="previewTitle" :visible.sync="previewVisible" width="60%">
      <el-card>
        <div>{{ itemValue.content }}</div>
      </el-card>
      <el-card style="margin-top: 12px">
        <div>{{ itemValue.parse }}</div>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="previewVisible=false">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="fullVisible" width="80%">
      <div v-for="item in paragraphs" :key="item.id" style="margin-bottom: 12px">
        <el-card>
          <div style="margin-bottom: 12px">{{ item.content }}</div>
          <div>{{ item.parse }}</div>
        </el-card>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import { getParagraphList, addParagraph, updateParagraph, deleteParagraph } from '@/api/paragraph'
import { getChapter } from '@/api/chapter'

export default {
  data() {
    return {
      params: {
        page: 1,
        size: 100
      },
      previewTitle: '查看段落',
      previewVisible: false,
      fullVisible: false,
      chapterName: '',
      total: 0,
      dialogVisible: false,
      paragraphs: null,
      listLoading: true,
      add: true, // 默认是添加文章
      labelWidth: '120px',
      itemValue: {
        id: '',
        content: '',
        parse: '',
        chapterId: ''
      }
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加段落' : '修改段落'
    }
  },
  mounted() {
    this.itemValue.chapterId = this.$route.params.chapterId
    this.fetchParagraphs()
    this.fetchChapter()
  },
  methods: {
    truncate(content = '') {
      const len = content.length
      const MAX_LEN = 206
      content = content.replace(/\n|\r\n/g, '<br>')
      if (len < MAX_LEN) return content
      return content.substring(0, MAX_LEN / 2) + ' .......... ' + content.substring(len - MAX_LEN / 2, len)
    },
    fetchParagraphs() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      obj.chapterId = this.itemValue.chapterId
      getParagraphList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.paragraphs = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchChapter() {
      const _this = this
      getChapter(_this.itemValue.chapterId)
        .then(res => {
          if (res.code === 200) {
            _this.chapterName = res.response.name
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    submitForm() {
      const _this = this
      if (this.add) {
        console.log(_this.itemValue.content)
        addParagraph(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchParagraphs()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateParagraph(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchParagraphs()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { content: '', parse: '' })
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.add = false
      Object.assign(this.itemValue, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteParagraph(row.id)
          _this.fetchParagraphs()
        })
    },
    previewParagraph(row) {
      Object.assign(this.itemValue, row)
      this.previewVisible = true
    },
    previewFull() {
      this.fullVisible = true
    }
  }
}
</script>

<style scoped>
  .table-container {
    text-align: center;
    margin: 20px 40px;
  }
</style>
