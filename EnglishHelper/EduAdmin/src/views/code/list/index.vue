<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="typeChange" style="margin-right: 30px" v-model="params.used" placeholder="选择文章类型">
          <el-option
            v-for="item in uses"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button @click="generate" type="primary" style="float: right; margin-right: 20px">生成激活码</el-button>
      </el-form>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="codeList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column  label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="激活码" align="center">
          <template slot-scope="scope">
            {{ scope.row.code }}
          </template>
        </el-table-column>
        <el-table-column label="使用情况" width="160" align="center">
          <template slot-scope="scope">
            {{ scope.row.used === 0 ? '未使用' : '已使用' }}
          </template>
        </el-table-column>
        <el-table-column label="创建时间" width="160" align="center">
          <template slot-scope="scope">
             {{ scope.row.createTime | timestampBeautify }}
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
import { getCodeList, generateCode } from '@/api/code'
export default {
  data() {
    return {
      uses: [{
        label: '全部激活码',
        value: -1
      }, {
        label: '未使用激活码',
        value: 0
      }, {
        label: '已使用激活码',
        value: 1
      }],
      codeList: [],
      total: 0,
      listLoading: true,
      params: {
        used: -1,
        page: 1,
        size: 10
      }
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchCodes()
  },
  methods: {
    generate: function() {
      const _this = this
      generateCode().then(res => {
        if (res.code === 200) {
          _this.params.page = 1
          _this.params.size = 10
          _this.fetchCodes()
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      })
    },
    fetchCodes: function() {
      this.listLoading = true
      const _this = this
      getCodeList(_this.params).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.codeList = res.response.records
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
      this.fetchCodes()
    },
    typeChange: function() {
      this.fetchCodes()
    }
  },
  computed: {
    emptyData: function() {
      return this.codeList.length === 0
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
