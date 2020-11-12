<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-input placeholder="输入词汇" v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchPhrases">查询</el-button>
        <router-link
          :to="{path:'/english/phrase/add'}"
        >
          <el-button type="primary" style="float: right">新增短语</el-button>
        </router-link>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="phraseList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="单词名称" width="280" align="center">
          <template slot-scope="scope">
            {{ scope.row.phrase }}
          </template>
        </el-table-column>
        <el-table-column label="短语翻译" align="center">
          <template slot-scope="scope">
            {{ scope.row.phraseTranslation }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editPhrase(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deletePhrase(scope.row.id)"
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
import { getPhraseList, deletePhrase } from '@/api/phrase'
export default {
  data() {
    return {
      phraseList: [],
      total: 0,
      listLoading: true,
      params: {
        query: '',
        page: 1,
        size: 6
      }
    }
  },
  computed: {
    emptyData: function() {
      return this.phraseList.length === 0
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchPhrases()
  },
  methods: {
    fetchPhrases: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getPhraseList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.phraseList = res.response.records
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
      this.fetchPhrases()
    },
    editPhrase: function(id) {
      this.$router.push({ name: 'edit_phrase', params: { phraseId: id }})
    },
    deletePhrase: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deletePhrase(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchPhrases()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewPhrase: function(phrase) {
    },
    firstChange: function() {
      this.fetchPhrases()
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
