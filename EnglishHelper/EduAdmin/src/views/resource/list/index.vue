<template>
  <div>
    <div class="form-container">
      <el-form :model="params">
        <el-select @change="typeChange" style="margin-right: 30px" v-model="params.resourceType" placeholder="选择资源类型">
          <el-option
            v-for="item in resourceTypes"
            :key="item.id"
            :label="item.typeName"
            :value="item.id + ''"
          />
        </el-select>
        <el-input v-model="params.query" style="width: 140px" />
        <el-button type="primary" style="margin-left: 30px" @click="fetchResources">查询</el-button>
        <el-button class="add-type" type="primary" style="float: right" @click="handleAdd" plain>添加资源</el-button>
      </el-form>
    </div>
    <div v-if="!emptyData" class="table-container">
      <el-table
        v-loading="listLoading"
        :data="resourceList"
        element-loading-text="主人，小白兔正在加载中"
        border
      >
        <el-table-column  label="序号" width="60" align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="资源名称" align="center" width="240">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="资源类别" width="160" align="center">
          <template slot-scope="scope">
            {{ resourceTypeFilter(scope.row.typeId) }}
          </template>
        </el-table-column>
        <el-table-column label="资源链接" width="240" align="center">
          <template slot-scope="scope">
            <a target="_blank" style="color: blue" v-bind:href="scope.row.link" >{{ scope.row.link }}</a>
          </template>
        </el-table-column>
        <el-table-column label="资源密码" width="160" align="center">
          <template slot-scope="scope">
            {{ scope.row.passw }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="handleEdit(scope.row)"
            >编辑
            </el-button>
            <el-button
              size="small"
              type="text"
              @click="deleteResource(scope.row.id)"
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
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="60%">
      <el-form :model="itemValue">
        <el-form-item label="资源类别" :label-width="labelWidth">
          <el-select @change="typeChange" style="margin-right: 30px" v-model="itemValue.typeId" placeholder="选择资源类型">
            <el-option
              v-for="item in resourceTypes_"
              :key="item.id"
              :label="item.typeName"
              :value="item.id + ''"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="资源名称" :label-width="labelWidth">
          <el-input v-model="itemValue.title" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="资源链接" :label-width="labelWidth">
          <el-input v-model="itemValue.link" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="资源密码" :label-width="labelWidth">
          <el-input v-model="itemValue.passw" auto-complete="off"></el-input>
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
import { getRTList } from '@/api/resource_type'
import { getResourceList, deleteResource, addResource, updateResource } from '@/api/resource'
export default {
  data() {
    return {
      resourceTypes: [],
      resourceTypes_: [],
      resourceList: [],
      total: 0,
      listLoading: true,
      add: true,
      dialogVisible: false,
      params: {
        resourceType: '',
        query: '',
        page: 1,
        size: 6
      },
      itemValue: {
        id: -1,
        typeId: '',
        title: '',
        link: '',
        passw: ''
      },
      labelWidth: '120px'
    }
  },
  created() {
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加资源类型' : '修改资源类型'
    },
    emptyData: function() {
      return this.resourceList.length === 0
    }
  },
  mounted() {
    const _this = this
    this.fetchResourceTypes(() => {
      _this.fetchResources()
    })
  },
  methods: {
    resourceTypeFilter: function(typeId) {
      const resourceType = this.resourceTypes_.filter(function(data) {
        return data.id === typeId
      })[0]
      console.log('.......', resourceType)
      return resourceType.typeName
    },
    fetchResources: function() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      if (_this.params.resourceType !== '-1' && _this.params.resourceType !== '') {
        obj.resourceType = _this.params.resourceType
      }
      if (_this.params.query.trim() !== '') {
        obj.query = _this.params.query
      }
      getResourceList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.resourceList = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
      }).catch(_ => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchResourceTypes: function(callback) {
      this.listLoading = true
      getRTList().then(res => {
        if (res.code === 200) {
          this.resourceTypes_ = res.response
          this.resourceTypes = res.response.concat([{ 'id': -1, 'typeName': '全部资源' }])
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
      this.fetchResources()
    },
    deleteResource: function(id) {
      const _this = this
      this.$confirm('确定删除吗？')
        .then(_ => {
          deleteResource(id)
            .then(res => {
              if (res.code === 200) {
                _this.$message('主人，删除成功')
                _this.fetchResources()
              } else {
                _this.$message('主人，发生了小故障，请重试')
              }
            })
        })
    },
    previewResource: function(resource) {
    },
    typeChange: function() {
      this.fetchResources()
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { title: '', link: '', passw: '', typeId: '' })
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.add = false
      Object.assign(this.itemValue, row)
      this.dialogVisible = true
    },
    submitForm() {
      const _this = this
      if (this.add) {
        addResource(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchResources()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateResource(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchResources()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
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
