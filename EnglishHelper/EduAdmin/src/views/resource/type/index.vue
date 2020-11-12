<template>
  <div class="app-container">
    <el-button class="add-type" type="primary" @click="handleAdd" plain>添加类别</el-button>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="类别名称" width="180" align="center">
          <template slot-scope="scope">
            {{ scope.row.typeName }}
          </template>
        </el-table-column>
        <el-table-column label="类别描述" align="center">
          <template slot-scope="scope">
            {{ scope.row.typeDesc }}
          </template>
        </el-table-column>
        <el-table-column label="权限" align="center" width="120">
          <template slot-scope="scope">
            {{ scope.row.needVip == 0 ? '用户可用' : '会员独享' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
        <el-form-item label="类别名称" :label-width="labelWidth">
          <el-input v-model="itemValue.typeName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类别描述" :label-width="labelWidth">
          <el-input v-model="itemValue.typeDesc" type="textarea" :row="3" ></el-input>
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
import { getRTList, deleteRT, updateRT, addRT } from '@/api/resource_type'

export default {
  data() {
    return {
      dialogVisible: false,
      list: null,
      listLoading: true,
      add: true,
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
        id: -1,
        typeName: '',
        typeDesc: '',
        needVip: 0
      },
      labelWidth: '120px'
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加资源类型' : '修改资源类型'
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      const _this = this
      getRTList().then(res => {
        if (res.code === 200) {
          _this.list = res.response
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    submitForm() {
      const _this = this
      if (this.add) {
        addRT(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchData()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateRT(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchData()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { typeName: '', typeDesc: '', needVip: 0 })
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
          deleteRT(row.id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchData()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
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
