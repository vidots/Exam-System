<template>
  <div class="app-container">
    <div style="text-align: center;margin-top: 12px;margin-bottom: 12px">
      {{ bookName }}
    </div>
    <div class="form-container">
      <el-form :model="params">
        <el-button type="primary" @click="handleAdd" style="float: right; margin-bottom: 12px">新增章节</el-button>
      </el-form>
    </div>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="chapters"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="章节名" align="center">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="jumpToChapter(scope.row.id)">查看段落详情</el-button>
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        layout="prev, pager, next"
        :total="total"
        :page-size="params.size"
        :current-page="params.page"
        style="float:right"
        @current-change="changePage"
      />
    </div>
    <el-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" width="60%">
      <el-form :model="itemValue">
        <el-form-item label="章节名称" :label-width="labelWidth">
          <el-input v-model="itemValue.name"></el-input>
        </el-form-item>
        <el-form-item label="章节描述" :label-width="labelWidth">
          <el-input type="textarea" v-model="itemValue.intro"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getChapterList, addChapter, updateChapter, deleteChapter } from '@/api/chapter'
import { getBook } from '@/api/book'

export default {
  data() {
    return {
      params: {
        page: 1,
        size: 7
      },
      bookName: '',
      total: 0,
      dialogVisible: false,
      chapters: null,
      listLoading: true,
      add: true, // 默认是添加文章
      labelWidth: '120px',
      itemValue: {
        id: '',
        name: '',
        bookId: '',
        intro: ''
      }
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加章节' : '修改章节'
    }
  },
  mounted() {
    this.itemValue.bookId = this.$route.params.bookId
    this.fetchChapters()
    this.fetchBook()
  },
  methods: {
    fetchChapters() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      obj.bookId = this.itemValue.bookId
      getChapterList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.chapters = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchBook() {
      const _this = this
      getBook(_this.itemValue.bookId)
        .then(res => {
          if (res.code === 200) {
            _this.bookName = res.response.name
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    changePage: function(page) {
      this.params.page = page
      this.fetchChapters()
    },
    submitForm() {
      const _this = this
      if (this.add) {
        addChapter(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchChapters()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateChapter(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchChapters()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { name: '', intro: '' })
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
          // deleteChapter(row.id)
          _this.$message('主人，无法删除哦')
          _this.fetchChapters()
        })
    },
    jumpToChapter(id) {
      this.$router.push({ name: 'edit_chapter', params: { bookId: this.itemValue.bookId, chapterId: id }})
    }
  }
}
</script>

<style scoped>
  .table-container {
    text-align: center;
    margin: 20px 40px;
  }
  .add-type {
    margin-left: 40px;
  }
</style>
