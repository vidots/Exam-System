<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="seriesChange" v-model="params.seriesId" style="margin-right: 30px">
          <el-option
            v-for="item in series"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
        <el-input placeholder="输入关键字" v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchPapers">查询</el-button>
        <el-button type="primary" @click="handleAdd" style="float: right">新增试卷</el-button>
      </el-form>
    </div>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="papers"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="试卷名" align="center">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="jumpToPaper(scope.row.id)">查看试卷题目</el-button>
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
        <el-form-item label="试卷分类" :label-width="labelWidth">
          <el-select v-model="itemValue.seriesId" style="margin-right: 30px">
            <el-option
              v-for="item in series"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="试卷名称" :label-width="labelWidth">
          <el-input v-model="itemValue.name"></el-input>
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
import { getPaperList, addPaper, updatePaper, deletePaper } from '@/api/paper'
import { getSeriesList } from '@/api/series'

export default {
  data() {
    return {
      params: {
        seriesId: 1,
        query: '',
        page: 1,
        size: 6
      },
      total: 0,
      series: [],
      dialogVisible: false,
      papers: [],
      listLoading: true,
      add: true, // 默认是添加文章
      labelWidth: '120px',
      itemValue: {
        id: '',
        seriesId: 1,
        name: ''
      }
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加试卷' : '修改试卷'
    }
  },
  created() {
    const _this = this
    this.fetchSeries(() => {
      if (_this.series.length > 0) {
        _this.params.seriesId = _this.series[0].id
      }
      _this.fetchPapers()
    })
  },
  methods: {
    fetchSeries(callback) {
      const _this = this
      getSeriesList().then(res => {
        if (res.code === 200) {
          _this.series = res.response
          callback()
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchPapers() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      obj.seriesId = _this.params.seriesId
      getPaperList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.papers = res.response.records
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
      this.fetchPapers()
    },
    submitForm() {
      const _this = this
      if (this.add) {
        addPaper(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchPapers()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updatePaper(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchPapers()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { seriesId: '', name: '' })
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.add = false
      Object.assign(this.itemValue, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确定删除吗？')
        .then(_ => {
          deletePaper(row.id)
        })
    },
    jumpToPaper(id) {
      this.$router.push({ name: 'list_question', params: { paperId: id }})
    },
    seriesChange() {
      this.fetchPapers()
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
