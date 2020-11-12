<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="params">
        <el-button type="primary" @click="handleAdd" style="float: right; margin-bottom: 12px">新增书籍</el-button>
      </el-form>
    </div>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="books"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="书名" align="center" width="200">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="简介" align="center">
          <template slot-scope="scope">
            {{ scope.row.info }}
          </template>
        </el-table-column>
        <el-table-column label="权限" align="center" width="120">
          <template slot-scope="scope">
            {{ scope.row.needVip == 0 ? '用户可用' : '会员独享' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="jumpToBook(scope.row.id)">查看书籍章节</el-button>
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
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="60%">
      <el-form :model="itemValue">
          <el-form-item label="书籍权限" :label-width="labelWidth">
            <el-select v-model="itemValue.needVip">
              <el-option
                v-for="role in roles"
                :key="role.value"
                :label="role.label"
                :value="role.value"
              />
            </el-select>
          </el-form-item>
        <el-form-item label="书籍名称" :label-width="labelWidth">
          <el-input v-model="itemValue.name"></el-input>
        </el-form-item>
        <el-form-item label="书籍描述" :label-width="labelWidth">
          <el-input type="textarea" rows="4" v-model="itemValue.info"></el-input>
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
import { getBookList, addBook, updateBook, deleteBook } from '@/api/book'

export default {
  data() {
    return {
      params: {
        page: 1,
        size: 6
      },
      total: 0,
      dialogVisible: false,
      books: null,
      listLoading: true,
      add: true, // 默认是添加文章
      labelWidth: '120px',
      roles: [
        {
          label: '所有人可用',
          value: 0
        },
        {
          label: '会员独享',
          value: 1
        }
      ],
      itemValue: {
        id: '',
        name: '',
        info: '',
        needVip: 0
      }
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加书籍' : '修改书籍'
    }
  },
  created() {
    const _this = this
    _this.fetchBooks()
  },
  methods: {
    fetchBooks() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      getBookList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.books = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    changePage: function(page) {
      this.params.page = page
      this.fetchBooks()
    },
    submitForm() {
      const _this = this
      if (this.add) {
        addBook(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchBooks()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateBook(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchBooks()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { info: '', name: '' , needVip: 0})
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
          // deleteBook(row.id)
          _this.$message('无法删除，请联系管理员哦')
          _this.fetchBooks()
        })
    },
    jumpToBook(id) {
      this.$router.push({ name: 'list_chapter', params: { bookId: id }})
    }
  }
}
</script>

<style scoped>
  .table-container {
    text-align: center;
    margin: 20px 40px;
  }
  .form-container {
    margin-bottom: 24px;
  }
</style>
