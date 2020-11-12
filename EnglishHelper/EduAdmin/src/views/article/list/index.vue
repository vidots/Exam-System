<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="typeChange" style="margin-right: 30px" v-model="params.articleType" placeholder="选择文章类型">
          <el-option
            v-for="item in articleTypes"
            :key="item.id"
            :label="item.typeName"
            :value="item.id + ''"
          />
        </el-select>
        <el-input v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchArticles">查询</el-button>
        <router-link
          :to="{path:'/article/add_html'}"
        >
          <el-button type="primary" style="float: right">新增HTML文章</el-button>
        </router-link>
        <router-link
          :to="{path:'/article/add_md'}"
        >
          <el-button type="primary" style="float: right; margin-right: 20px">新增MD文章</el-button>
        </router-link>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="articleList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column  label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="文章名称" align="center">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="文章类别" width="160" align="center">
          <template slot-scope="scope">
            {{ articleTypeFilter(scope.row.typeId) }}
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
              @click="editArticle(scope.row)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteArticle(scope.row.id)"
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
import { getATList } from '@/api/article_type'
import { getArticleList, deleteArticle } from '@/api/article'
export default {
  data() {
    return {
      articleTypes: [],
      articleList: [],
      total: 0,
      listLoading: true,
      params: {
        articleType: '',
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
    this.fetchArticleTypes(() => {
      _this.fetchArticles()
    })
  },
  methods: {
    articleTypeFilter: function(typeId) {
      const articleType = this.articleTypes.filter(function(data) {
        return data.id === typeId
      })[0]
      return articleType.typeName
    },
    fetchArticles: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.articleType !== '-1' && _this.params.articleType !== '') {
        obj.articleType = _this.params.articleType
      }
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getArticleList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.articleList = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchArticleTypes: function(callback) {
      this.listLoading = true
      getATList().then(res => {
        if (res.code === 200) {
          this.articleTypes = res.response.concat([{ 'id': -1, 'typeName': '全部文章' }])
          this.listLoading = false
          callback()
        } else {
          this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        this.$message('主人，发生了小故障，请重试')
      })
    },
    changePage: function(page) {
      this.params.page = page
      this.fetchArticles()
    },
    editArticle: function(row) {
      if (row.format === 'html') {
        this.$router.push({ name: 'edit_article_html', params: { articleId: row.id }})
      } else {
        this.$router.push({ name: 'edit_article_md', params: { articleId: row.id }})
      }
    },
    deleteArticle: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteArticle(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchArticles()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewArticle: function(article) {
    },
    typeChange: function() {
      this.fetchArticles()
    }
  },
  computed: {
    emptyData: function() {
      return this.articleList.length === 0
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
