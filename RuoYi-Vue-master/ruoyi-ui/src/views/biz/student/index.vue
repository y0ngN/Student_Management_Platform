<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="92px">
      <el-form-item label="成果标题" prop="achievementTitle">
        <el-input v-model="queryParams.achievementTitle" placeholder="请输入成果标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="成果类型" prop="achievementType">
        <el-select v-model="queryParams.achievementType" placeholder="请选择" clearable>
          <el-option label="竞赛获奖" value="竞赛获奖" />
          <el-option label="论文成果" value="论文成果" />
          <el-option label="证书认证" value="证书认证" />
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
      <el-table-column label="ID" prop="achievementId" width="70" />
      <el-table-column label="学生" prop="studentName" width="120" />
      <el-table-column label="学号" prop="studentNo" width="120" />
      <el-table-column label="院系/部门" prop="departmentName" width="140" />
      <el-table-column label="成果标题" prop="achievementTitle" min-width="180" show-overflow-tooltip />
      <el-table-column label="成果类型" prop="achievementType" width="120" />
      <el-table-column label="成果等级" prop="achievementLevel" width="120" />
      <el-table-column label="日期" width="120">
        <template slot-scope="scope">{{ parseTime(scope.row.awardDate, '{y}-{m}-{d}') }}</template>
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
          <el-button type="text" size="mini" @click="handleAudit(scope.row, '1')" v-hasPermi="['biz:studentAchievement:audit']">通过</el-button>
          <el-button type="text" size="mini" @click="handleAudit(scope.row, '2')" v-hasPermi="['biz:studentAchievement:audit']">驳回</el-button>
          <el-button type="text" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['biz:studentAchievement:remove']">删除</el-button>
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
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="成果标题" prop="achievementTitle">
          <el-input v-model="form.achievementTitle" placeholder="请输入成果标题" />
        </el-form-item>
        <el-form-item label="成果类型" prop="achievementType">
          <el-select v-model="form.achievementType" placeholder="请选择">
            <el-option label="竞赛获奖" value="竞赛获奖" />
            <el-option label="论文成果" value="论文成果" />
            <el-option label="证书认证" value="证书认证" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="成果等级">
          <el-input v-model="form.achievementLevel" placeholder="例如：国家级/省级/校级" />
        </el-form-item>
        <el-form-item label="日期" prop="awardDate">
          <el-date-picker v-model="form.awardDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" />
        </el-form-item>
        <el-form-item label="成果说明">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入成果说明" />
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
  listStudentAchievement,
  getStudentAchievement,
  addStudentAchievement,
  updateStudentAchievement,
  delStudentAchievement,
  auditStudentAchievement
} from '@/api/biz/studentAchievement'

export default {
  name: 'StudentAchievement',
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
        achievementTitle: undefined,
        achievementType: undefined,
        auditStatus: undefined
      },
      form: {},
      rules: {
        departmentName: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
        studentNo: [{ required: true, message: '学号不能为空', trigger: 'blur' }],
        achievementTitle: [{ required: true, message: '成果标题不能为空', trigger: 'blur' }],
        achievementType: [{ required: true, message: '成果类型不能为空', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStudentAchievement(this.queryParams).then((res) => {
        this.list = res.rows || []
        this.total = res.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    reset() {
      this.form = {
        achievementId: undefined,
        departmentName: undefined,
        studentNo: undefined,
        achievementTitle: undefined,
        achievementType: undefined,
        achievementLevel: undefined,
        awardDate: undefined,
        description: undefined,
        attachment: undefined
      }
      this.resetForm('form')
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增学生成果'
    },
    handleUpdate(row) {
      this.reset()
      getStudentAchievement(row.achievementId).then((res) => {
        this.form = res.data
        this.open = true
        this.title = '编辑学生成果'
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        const api = this.form.achievementId ? updateStudentAchievement : addStudentAchievement
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
        return auditStudentAchievement({
          achievementId: row.achievementId,
          auditStatus: status,
          auditRemark: value
        })
      }).then(() => {
        this.$modal.msgSuccess('审核完成')
        this.getList()
      }).catch(() => {})
    },
    handleDelete(row) {
      this.$modal.confirm(`是否确认删除成果ID为 ${row.achievementId} 的数据项？`).then(() => {
        return delStudentAchievement(row.achievementId)
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
