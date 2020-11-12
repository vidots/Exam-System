<template>
  <div class="app-container">
    <div style="text-align: center;margin-top: 12px;margin-bottom: 12px">
      {{ unitName }}
    </div>
    <div class="form-container">
      <el-form :model="params">
        <el-button type="primary" style="float: right; margin-bottom: 12px" @click="handleAdd">新增概念</el-button>
        <el-button type="primary" style="float: right; margin-right: 12px; margin-bottom: 12px" @click="previewFull">预览概念</el-button>
      </el-form>
    </div>
    <div class="table-container">
      <el-table
        v-loading="listLoading"
        :data="concepts"
        element-loading-text="主人，小白兔正在加载中"
        border
        highlight-current-row
      >
        <el-table-column align="center" label="序号" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="概念" align="center">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :close-on-click-modal="false" :title="dialogTitle" :visible.sync="dialogVisible" width="80%" @opened="dialogOpen">
      <el-form :model="itemValue">
        <el-form-item label="概念预览" :label-width="labelWidth">
          <div id="math-preview" />
        </el-form-item>
        <el-form-item label="概念名称" :label-width="labelWidth">
          <el-input v-model="itemValue.name" />
        </el-form-item>
        <el-form-item label="概念内容" :label-width="labelWidth">
          <el-input v-model="itemValue.detail" type="textarea" rows="4" @input="startPreview($event)" />
        </el-form-item>
        <el-form-item label="图片上传" :label-width="labelWidth">
          <el-button @click="removeImage"><i class="el-icon-delete"></i></el-button>
          <el-upload
            class="avatar-uploader"
            name="image"
            :action="apiUrl"
            :show-file-list="false"
            :on-remove="handleRemove"
            :on-success="uploadSuccess">
            <img v-if="itemValue.img" :src="itemValue.img" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="fullVisible" width="80%" @opened="previewFullMath">
      <div id="math-full">
        <div  v-for="item in concepts" :key="item.id" style="margin-bottom: 12px">
          <el-card>
            <div style="margin-bottom: 12px"><strong>{{ item.name }}</strong></div>
            <div v-html="multi(item.detail)"> </div>
          </el-card>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getConceptList, addConcept, updateConcept, deleteConcept } from '@/api/concept'
import { getUnit } from '@/api/unit'
const { apiUrl } = require('@/change')
import 'katex/dist/katex.css'
import renderMathInElement from 'katex/contrib/auto-render/auto-render'

export default {
  data() {
    return {
      params: {
        page: 1,
        size: 100
      },
      apiUrl: apiUrl + "/media/upload/image",
      previewTitle: '查看段落',
      previewVisible: false,
      fullVisible: false,
      unitName: '',
      total: 0,
      dialogVisible: false,
      concepts: null,
      listLoading: true,
      add: true, // 默认是添加文章
      labelWidth: '120px',
      itemValue: {
        id: '',
        name: '',
        detail: '',
        unitId: '',
        img: ''
      }
    }
  },
  computed: {
    dialogTitle: function() {
      return this.add ? '添加概念' : '修改概念'
    }
  },
  mounted() {
    this.itemValue.unitId = this.$route.params.unitId
    this.fetchConcepts()
    this.fetchUnit()
  },
  methods: {
    multi(content) {
      const r = content.split('\n').join('<br/>')
      return r
    },
    fetchConcepts() {
      this.listLoading = true
      const _this = this
      const obj = {}
      obj.page = this.params.page
      obj.size = this.params.size
      obj.unitId = this.itemValue.unitId
      getConceptList(obj).then(res => {
        if (res.code === 200) {
          _this.total = res.response.total
          _this.concepts = res.response.records
          _this.listLoading = false
        } else {
          _this.$message('主人，发生了小故障，请重试')
        }
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        _this.$message('主人，发生了小故障，请重试')
      })
    },
    fetchUnit() {
      const _this = this
      getUnit(_this.itemValue.unitId)
        .then(res => {
          if (res.code === 200) {
            _this.unitName = res.response.name
          } else {
            _this.$message('主人，发生了小故障，请重试')
          }
        })
    },
    submitForm() {
      const _this = this
      if (this.add) {
        console.log(_this.itemValue.content)
        addConcept(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，添加成功')
              _this.dialogVisible = false
              _this.fetchConcepts()
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      } else {
        updateConcept(_this.itemValue)
          .then(res => {
            if (res.code === 200) {
              _this.$message('主人，修改成功')
              _this.fetchConcepts()
              _this.dialogVisible = false
            } else {
              _this.$message('主人，发生了小故障，请重试')
            }
          })
      }
    },
    handleAdd() {
      this.add = true
      Object.assign(this.itemValue, { content: '', detail: '', name: '' })
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
          deleteConcept(row.id).then(res => {
            _this.fetchConcepts()
          })

        })
    },
    previewFull() {
      this.fullVisible = true
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    uploadSuccess(res, file) {
      console.log(res, file)
      this.itemValue.img = res.response
    },
    startPreview(value) {
      document.getElementById('math-preview').innerHTML = this.multi(value)
      renderMathInElement(document.getElementById('math-preview'), {
        delimiters: [
          { left: '$$', right: '$$', display: false },
          { left: '$', right: '$', display: false }
        ], fleqn: true
      })
    },
    previewFullMath() {
      renderMathInElement(document.getElementById('math-full'), {
        delimiters: [
          { left: '$$', right: '$$', display: false },
          { left: '$', right: '$', display: false }
        ]
      })
    },
    removeImage() {
      this.itemValue.img = ''
    },
    dialogOpen() {
      document.getElementById('math-preview').innerHTML = this.multi(this.itemValue.detail)
      renderMathInElement(document.getElementById('math-preview'), {
        delimiters: [
          { left: '$$', right: '$$', display: false },
          { left: '$', right: '$', display: false }
        ]
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
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    display:block;
    width:30%;
    margin:0 auto;
  }
  #math-preview {
    line-height: 10px;
    font-size: 21px;
  }
  .card-container {
    display: flex;
    flex-direction: column;
  }
  .card-left {
    flex-grow: 1;
  }
  .card-right {
    flex-grow: 1;
  }
</style>
