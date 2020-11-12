<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-input v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchNewss">查询</el-button>
        <router-link
          :to="{path:'/news/add_html'}"
        >
          <el-button type="primary" style="float: right">新增HTML文章</el-button>
        </router-link>
        <router-link
          :to="{path:'/news/add_md'}"
        >
          <el-button type="primary" style="float: right; margin-right: 20px">新增MD文章</el-button>
        </router-link>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="newsList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column  label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="咨询名称" align="center">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="更新时间" width="160" align="center">
          <template slot-scope="scope">
            {{ scope.row.updateTime | timestampBeautify }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editNews(scope.row)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteNews(scope.row.id)"
            >删除
            </el-button>
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
  </div>
</template>
<script>
import { getNewsList, deleteNews } from '@/api/news'
export default {
  data() {
    return {
      newsList: [],
      total: 0,
      listLoading: true,
      params: {
        query: '',
        page: 1,
        size: 6
      }
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchNews()
  },
  methods: {
    fetchNews: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getNewsList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.newsList = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    changePage: function(page) {
      this.params.page = page
      this.fetchNews()
    },
    editNews: function(row) {
      if (row.format === 'html') {
        this.$router.push({ name: 'edit_news_html', params: { newsId: row.id }})
      } else {
        this.$router.push({ name: 'edit_news_md', params: { newsId: row.id }})
      }
    },
    deleteNews: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteNews(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchNews()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewNews: function(news) {
    },
    typeChange: function() {
      this.fetchNews()
    }
  },
  computed: {
    emptyData: function() {
      return this.newsList.length === 0
    }
  }
}
</script>
<style>
  .table-container, .form-container {
    margin-top: 24px;
    margin-left: 45px;
    margin-right: 45px;
  }
</style>
