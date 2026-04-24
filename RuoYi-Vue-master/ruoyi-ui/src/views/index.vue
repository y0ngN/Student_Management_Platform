<template>
  <div class="app-container home-profile">
    <el-card>
      <div slot="header" class="clearfix">
        <span>个人信息</span>
        <el-tag style="float: right" size="mini" type="info">{{ roleLabel }}</el-tag>
      </div>

      <el-alert
        v-if="roleType === 'other'"
        title="当前账号未配置教师/学生角色，仅展示基础账号信息。"
        type="warning"
        :closable="false"
        style="margin-bottom: 16px"
      />

      <el-form ref="form" :model="formModel" :rules="rules" label-width="120px" v-loading="loading">
        <el-row :gutter="16">
          <el-col :xs="24" :md="12">
            <el-form-item label="账号">
              <el-input v-model="baseUser.userName" disabled />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="formModel.name" :disabled="roleType === 'other'" />
            </el-form-item>
          </el-col>
        </el-row>

        <template v-if="roleType === 'teacher'">
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="教职工号">
                <el-input v-model="formModel.staffId" disabled />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="所属部门" prop="deptId">
                <treeselect v-model="formModel.deptId" :options="deptOptions" :show-count="true" placeholder="请选择部门" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="性别">
                <el-select v-model="formModel.gender" placeholder="请选择">
                  <el-option label="男" value="男" />
                  <el-option label="女" value="女" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="手机号">
                <el-input v-model="formModel.phone" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="邮箱">
                <el-input v-model="formModel.email" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="入职时间">
                <el-date-picker v-model="formModel.entryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="人员状态">
                <el-select v-model="formModel.staffStatus" placeholder="请选择">
                  <el-option label="在职" value="在职" />
                  <el-option label="离职" value="离职" />
                  <el-option label="退休" value="退休" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="研究方向">
                <el-input v-model="formModel.researchDirection" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="教师资格证号">
                <el-input v-model="formModel.teacherCertNumber" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="学科类别">
                <el-input v-model="formModel.subjectCategory" />
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <template v-else-if="roleType === 'student'">
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="学号">
                <el-input v-model="formModel.studentId" disabled />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="所属学院" prop="deptId">
                <treeselect v-model="formModel.deptId" :options="deptOptions" :show-count="true" placeholder="请选择学院" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :xs="24" :md="12">
              <el-form-item label="专业">
                <el-input v-model="formModel.major" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="年级">
                <el-input v-model="formModel.currentGrade" placeholder="例如：2023级" />
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <el-form-item>
          <el-button type="primary" @click="handleSave" :disabled="roleType === 'other'">保存</el-button>
          <el-button @click="loadProfile">刷新</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { deptTreeSelect } from '@/api/system/user'
import { getHomeProfile, updateHomeProfile } from '@/api/biz/homeProfile'

export default {
  name: 'Index',
  components: { Treeselect },
  data() {
    return {
      loading: false,
      roleType: 'other',
      baseUser: {
        userName: ''
      },
      deptOptions: [],
      formModel: {
        name: '',
        deptId: undefined,
        staffId: '',
        gender: '',
        phone: '',
        email: '',
        entryDate: '',
        staffStatus: '在职',
        teacherCertNumber: '',
        subjectCategory: '',
        researchDirection: '',
        studentId: '',
        major: '',
        currentGrade: ''
      },
      raw: {},
      rules: {
        name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
        deptId: [{ required: true, message: '请选择部门', trigger: 'change' }]
      }
    }
  },
  computed: {
    roleLabel() {
      if (this.roleType === 'teacher') return '教师'
      if (this.roleType === 'student') return '学生'
      return '普通用户'
    }
  },
  created() {
    this.loadDeptTree()
    this.loadProfile()
  },
  methods: {
    loadDeptTree() {
      deptTreeSelect().then(res => {
        this.deptOptions = res.data || []
      })
    },
    loadProfile() {
      this.loading = true
      getHomeProfile().then(res => {
        const data = res.data || {}
        this.raw = data
        this.roleType = data.roleType || 'other'
        this.baseUser.userName = data.userName || ''
        this.fillForm()
      }).finally(() => {
        this.loading = false
      })
    },
    fillForm() {
      if (this.roleType === 'teacher') {
        const staff = this.raw.staff || {}
        const detail = this.raw.staffDetail || {}
        this.formModel = {
          ...this.formModel,
          name: staff.realName || '',
          deptId: staff.deptId,
          staffId: staff.staffId || '',
          gender: staff.gender || '',
          phone: staff.phone || '',
          email: staff.email || '',
          entryDate: staff.entryDate || '',
          staffStatus: staff.staffStatus || '在职',
          teacherCertNumber: detail.teacherCertNumber || '',
          subjectCategory: detail.subjectCategory || '',
          researchDirection: detail.researchDirection || '',
          studentId: '',
          major: '',
          currentGrade: ''
        }
      } else if (this.roleType === 'student') {
        const student = this.raw.student || {}
        this.formModel = {
          ...this.formModel,
          name: student.studentName || '',
          deptId: student.deptId,
          studentId: student.studentId || '',
          major: student.major || '',
          currentGrade: student.currentGrade || '',
          staffId: '',
          gender: '',
          phone: '',
          email: '',
          entryDate: '',
          staffStatus: '在职',
          teacherCertNumber: '',
          subjectCategory: '',
          researchDirection: ''
        }
      }
    },
    buildPayload() {
      if (this.roleType === 'teacher') {
        return {
          staff: {
            staffId: this.formModel.staffId,
            realName: this.formModel.name,
            gender: this.formModel.gender,
            deptId: this.formModel.deptId,
            phone: this.formModel.phone,
            email: this.formModel.email,
            entryDate: this.formModel.entryDate,
            staffStatus: this.formModel.staffStatus
          },
          staffDetail: {
            teacherCertNumber: this.formModel.teacherCertNumber,
            subjectCategory: this.formModel.subjectCategory,
            researchDirection: this.formModel.researchDirection
          }
        }
      }
      if (this.roleType === 'student') {
        return {
          student: {
            studentId: this.formModel.studentId,
            studentName: this.formModel.name,
            deptId: this.formModel.deptId,
            major: this.formModel.major,
            currentGrade: this.formModel.currentGrade
          }
        }
      }
      return {}
    },
    handleSave() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        const payload = this.buildPayload()
        updateHomeProfile(payload).then(() => {
          this.$modal.msgSuccess('保存成功')
          this.loadProfile()
        })
      })
    }
  }
}
</script>

<style scoped lang="scss">
.home-profile {
  .el-card {
    max-width: 1200px;
  }
}
</style>
