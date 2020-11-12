<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="categoryChange" v-model="params.category" style="margin-right: 30px" placeholder="选择句子类型">
          <el-option
            v-for="item in categories"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input placeholder="输入要查询的句子" v-model="params.query" style="width: 240px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchSentences">查询</el-button>
        <router-link
          :to="{path:'/english/sentence/add'}"
        >
          <el-button type="primary" style="float: right">新增句子</el-button>
        </router-link>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="sentenceList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="句子" align="center">
          <template slot-scope="scope">
            {{ scope.row.sentence }} <br>
            {{ scope.row.translation }}
          </template>
        </el-table-column>
        <el-table-column label="来源" width="180px" align="center">
          <template slot-scope="scope">
            {{ scope.row.source === '' ? (scope.row.category === 1 ? '考研' : '其它') : scope.row.source }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editSentence(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteSentence(scope.row.id)"
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
import { getSentenceList, deleteSentence } from '@/api/sentence'
export default {
  data() {
    return {
      categories: [{ label: '考研', value: '1' }, { label: '全部句子', value: '0' }],
      sentenceList: [],
      total: 0,
      listLoading: true,
      params: {
        category: '',
        query: '',
        page: 1,
        size: 6
      }
    }
  },
  computed: {
    emptyData: function() {
      return this.sentenceList.length === 0
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchSentences()
  },
  methods: {
    fetchSentences: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      if (_this.params.category.trim() !== '') {
        obj.category = _this.params.category
      }
      getSentenceList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.sentenceList = res.response.records
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
      this.fetchSentences()
    },
    editSentence: function(id) {
      this.$router.push({ name: 'edit_sentence', params: { sentenceId: id }})
    },
    deleteSentence: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteSentence(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchSentences()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewSentence: function(sentence) {
    },
    categoryChange: function() {
      this.fetchSentences()
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
