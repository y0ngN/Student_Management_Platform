<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="92px">
      <el-form-item label="材料标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入材料标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="材料类型" prop="materialType">
        <el-select v-model="queryParams.materialType" placeholder="请选择" clearable>
          <el-option label="论文" value="论文" />
          <el-option label="资助项目" value="资助项目" />
          <el-option label="项目提案" value="项目提案" />
          <el-option label="其他" value="其他" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择" clearable>
          <el-option label="待审核" value="0" />
          <el-option label="已通过" value="1" />
          <el-option label="已驳回" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <right-toolbar @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="list">
      <el-table-column label="ID" prop="materialId" width="70" />
      <el-table-column label="教师" prop="teacherName" width="120" />
      <el-table-column label="院系/部门" prop="departmentName" width="140" />
      <el-table-column label="标题" prop="title" min-width="180" show-overflow-tooltip />
      <el-table-column label="类型" prop="materialType" width="120" />
      <el-table-column label="项目资金" prop="projectAmount" width="120" />
      <el-table-column label="提交日期" width="120">
        <template slot-scope="scope">{{ parseTime(scope.row.submitDate, '{y}-{m}-{d}') }}</template>
      </el-table-column>
      <el-table-column label="审核状态" width="110">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.auditStatus === '1'" type="success">已通过</el-tag>
          <el-tag v-else-if="scope.row.auditStatus === '2'" type="danger">已驳回</el-tag>
          <el-tag v-else>待审核</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="handleAudit(scope.row, '1')" v-hasPermi="['biz:teacherMaterial:audit']">通过</el-button>
          <el-button type="text" size="mini" @click="handleAudit(scope.row, '2')" v-hasPermi="['biz:teacherMaterial:audit']">驳回</el-button>
          <el-button type="text" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['biz:teacherMaterial:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="院系/部门" prop="departmentName">
          <el-input v-model="form.departmentName" placeholder="例如：计算机学院" />
        </el-form-item>
        <el-form-item label="材料标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="材料类型" prop="materialType">
          <el-select v-model="form.materialType" placeholder="请选择类型">
            <el-option label="论文" value="论文" />
            <el-option label="资助项目" value="资助项目" />
            <el-option label="项目提案" value="项目提案" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="项目资金">
          <el-input-number v-model="form.projectAmount" :min="0" :precision="2" :step="1000" controls-position="right" />
        </el-form-item>
        <el-form-item label="提交日期" prop="submitDate">
          <el-date-picker v-model="form.submitDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" />
        </el-form-item>
        <el-form-item label="材料说明">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入内容说明" />
        </el-form-item>
        <el-form-item label="附件上传">
          <file-upload v-model="form.attachment" :file-size="20" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTeacherMaterial,
  getTeacherMaterial,
  addTeacherMaterial,
  updateTeacherMaterial,
  delTeacherMaterial,
  auditTeacherMaterial
} from '@/api/biz/teacherMaterial'

export default {
  name: 'TeacherMaterial',
  data() {
    return {
      loading: false,
      total: 0,
      list: [],
      open: false,
      title: '',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        materialType: undefined,
        auditStatus: undefined
      },
      form: {},
      rules: {
        departmentName: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        materialType: [{ required: true, message: '类型不能为空', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listTeacherMaterial(this.queryParams).then((res) => {
        this.list = res.rows || []
        this.total = res.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    reset() {
      this.form = {
        materialId: undefined,
        departmentName: undefined,
        title: undefined,
        materialType: undefined,
        projectAmount: 0,
        submitDate: undefined,
        description: undefined,
        attachment: undefined
      }
      this.resetForm('form')
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增教师材料'
    },
    handleUpdate(row) {
      this.reset()
      getTeacherMaterial(row.materialId).then((res) => {
        this.form = res.data
        this.open = true
        this.title = '编辑教师材料'
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        const api = this.form.materialId ? updateTeacherMaterial : addTeacherMaterial
        api(this.form).then(() => {
          this.$modal.msgSuccess('保存成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleAudit(row, status) {
      const text = status === '1' ? '通过' : '驳回'
      this.$modal.prompt(`请输入${text}备注（可选）`).then(({ value }) => {
        return auditTeacherMaterial({
          materialId: row.materialId,
          auditStatus: status,
          auditRemark: value
        })
      }).then(() => {
        this.$modal.msgSuccess('审核完成')
        this.getList()
      }).catch(() => {})
    },
    handleDelete(row) {
      this.$modal.confirm(`是否确认删除材料ID为 ${row.materialId} 的数据项？`).then(() => {
        return delTeacherMaterial(row.materialId)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    cancel() {
      this.open = false
    }
  }
}
</script>
