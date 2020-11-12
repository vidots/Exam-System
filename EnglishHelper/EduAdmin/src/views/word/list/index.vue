<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="firstChange" v-model="params.first_letter" style="margin-right: 30px" placeholder="根据首字母查词">
          <el-option
            v-for="item in letters"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input placeholder="输入词汇" v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchWords">查询</el-button>
        <router-link
          :to="{path:'/english/word/add'}"
        >
          <el-button type="primary" style="float: right">新增单词</el-button>
        </router-link>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="wordList"
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
            {{ scope.row.word }} [{{ scope.row.symbol }}]
          </template>
        </el-table-column>
        <el-table-column label="单词翻译" align="center">
          <template slot-scope="scope">
            {{ scope.row.translationOne }} <br>
            {{ scope.row.translationTwo }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editWord(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteWord(scope.row.id)"
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
import { getWordList, deleteWord } from '@/api/word'
import { letters } from '@/utils/constant'
export default {
  data() {
    return {
      letters: letters.concat({ value: '', label: '全部' }),
      wordList: [],
      total: 0,
      listLoading: true,
      params: {
        first_letter: '',
        query: '',
        page: 1,
        size: 6
      }
    }
  },
  computed: {
    emptyData: function() {
      return this.wordList.length === 0
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchWords()
  },
  methods: {
    fetchWords: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      if (_this.params.first_letter.trim() !== '') {
        obj.first_letter = _this.params.first_letter
      }
      getWordList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.wordList = res.response.records
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
      this.fetchWords()
    },
    editWord: function(id) {
      this.$router.push({ name: 'edit_word', params: { wordId: id }})
    },
    deleteWord: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteWord(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchWords()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewWord: function(word) {
    },
    firstChange: function() {
      this.fetchWords()
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
