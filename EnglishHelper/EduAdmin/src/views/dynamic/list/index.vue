<template>
  <div>
    <div class="form-container">

      关于我们：about；免责声明：duty
      <router-link
        :to="{path:'/dynamic/add_html'}"
      >
        <el-button type="primary" style="float: right">新增动态文章</el-button>
      </router-link>
    </div>

    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="dynamicList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column label="序号" width="95" align="center">
          <template slot-scope="scope">
            {{ scope.$index }}
          </template>
        </el-table-column>
        <el-table-column label="名称" align="center">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="主题" width="160" align="center">
          <template slot-scope="scope">
            {{ scope.row.subject }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="editDynamic(scope.row)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteDynamic(scope.row.id)"
            >删除
            </el-button>
          </template>

        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { getDyContentList, deleteDyContent } from '@/api/dynamic'
export default {
  data() {
    return {
      dynamicList: [],
      listLoading: true
    }
  },
  computed: {
    emptyData: function() {
      return this.dynamicList.length === 0
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.fetchDynamics()
  },
  methods: {
    fetchDynamics: function() {
      this.listLoading = true
      const _this = this
      getDyContentList().then(res => {
        if (res.code === 200) {
          _this.dynamicList = res.response
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    editDynamic: function(row) {
      this.$router.push({ name: 'edit_dynamic_html', params: { dynamicId: row.id }})
    },
    deleteDynamic: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteDyContent(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchDynamics()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewDynamic: function(article) {
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
