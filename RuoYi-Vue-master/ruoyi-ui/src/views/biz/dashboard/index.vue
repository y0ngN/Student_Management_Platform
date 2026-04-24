<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true" label-width="92px">
      <el-form-item label="院系/部门">
        <el-input v-model="queryParams.departmentName" placeholder="可按部门筛选" clearable />
      </el-form-item>
      <el-form-item label="日期范围">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="16" class="mb16">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card><div class="card"><div class="t">教师材料总数</div><div class="v">{{ overview.teacherTotal || 0 }}</div></div></el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card><div class="card"><div class="t">学生成果总数</div><div class="v">{{ overview.studentTotal || 0 }}</div></div></el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card><div class="card"><div class="t">累计已审核</div><div class="v">{{ overview.approvedTotal || 0 }}</div></div></el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card><div class="card"><div class="t">项目资金(通过)</div><div class="v">{{ overview.approvedProjectAmount || 0 }}</div></div></el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :md="8">
        <el-card>
          <div slot="header">角色数据占比</div>
          <div ref="roleChart" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card>
          <div slot="header">部门数据分布</div>
          <div ref="deptChart" class="chart"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card>
          <div slot="header">月度趋势</div>
          <div ref="monthChart" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :xs="24" :md="12">
        <el-card>
          <div slot="header">学生原始提交信息（未排列）</div>
          <el-table :data="rawStudentRows" size="mini" stripe max-height="320">
            <el-table-column label="学生" prop="studentName" width="110" />
            <el-table-column label="学号" prop="studentNo" width="130" />
            <el-table-column label="院系/部门" prop="departmentName" width="140" />
            <el-table-column label="成果标题" prop="achievementTitle" min-width="180" show-overflow-tooltip />
            <el-table-column label="日期" width="110">
              <template slot-scope="scope">{{ parseTime(scope.row.awardDate, '{y}-{m}-{d}') }}</template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card>
          <div slot="header">教师原始提交信息（未排列）</div>
          <el-table :data="rawTeacherRows" size="mini" stripe max-height="320">
            <el-table-column label="教师" prop="teacherName" width="110" />
            <el-table-column label="院系/部门" prop="departmentName" width="140" />
            <el-table-column label="标题" prop="title" min-width="180" show-overflow-tooltip />
            <el-table-column label="类型" prop="materialType" width="120" />
            <el-table-column label="提交日期" width="110">
              <template slot-scope="scope">{{ parseTime(scope.row.submitDate, '{y}-{m}-{d}') }}</template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getOverview, getRoleStat, getDepartmentStat, getMonthlyTrend, getRawSubmissions } from '@/api/biz/dashboard'

export default {
  name: 'BizDashboard',
  data() {
    return {
      queryParams: {
        departmentName: undefined,
        beginDate: undefined,
        endDate: undefined
      },
      dateRange: [],
      overview: {},
      roleData: [],
      deptData: [],
      monthData: [],
      rawStudentRows: [],
      rawTeacherRows: [],
      roleChart: null,
      deptChart: null,
      monthChart: null
    }
  },
  created() {
    this.fetchData()
  },
  mounted() {
    this.$nextTick(() => {
      this.initCharts()
      window.addEventListener('resize', this.resizeCharts)
    })
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    this.disposeCharts()
  },
  methods: {
    buildQuery() {
      this.queryParams.beginDate = this.dateRange && this.dateRange.length ? this.dateRange[0] : undefined
      this.queryParams.endDate = this.dateRange && this.dateRange.length ? this.dateRange[1] : undefined
      return { ...this.queryParams }
    },
    fetchData() {
      const query = this.buildQuery()
      Promise.all([
        getOverview(query),
        getRoleStat(query),
        getDepartmentStat(query),
        getMonthlyTrend(query),
        getRawSubmissions(query)
      ]).then(([overviewRes, roleRes, deptRes, monthRes, rawRes]) => {
        this.overview = overviewRes.data || {}
        this.roleData = roleRes.data || []
        this.deptData = deptRes.data || []
        this.monthData = monthRes.data || []
        this.rawStudentRows = rawRes.data && rawRes.data.studentRows ? rawRes.data.studentRows : []
        this.rawTeacherRows = rawRes.data && rawRes.data.teacherRows ? rawRes.data.teacherRows : []
        this.renderCharts()
      })
    },
    handleQuery() {
      this.fetchData()
    },
    initCharts() {
      if (this.$refs.roleChart) this.roleChart = echarts.init(this.$refs.roleChart)
      if (this.$refs.deptChart) this.deptChart = echarts.init(this.$refs.deptChart)
      if (this.$refs.monthChart) this.monthChart = echarts.init(this.$refs.monthChart)
      this.renderCharts()
    },
    renderCharts() {
      this.renderRoleChart()
      this.renderDeptChart()
      this.renderMonthChart()
    },
    renderRoleChart() {
      if (!this.roleChart) return
      this.roleChart.setOption({
        tooltip: { trigger: 'item' },
        legend: { bottom: 0 },
        series: [
          {
            type: 'pie',
            radius: ['35%', '65%'],
            data: this.roleData
          }
        ]
      })
    },
    renderDeptChart() {
      if (!this.deptChart) return
      this.deptChart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: this.deptData.map(item => item.name), axisLabel: { rotate: 30 } },
        yAxis: { type: 'value' },
        series: [
          {
            type: 'bar',
            data: this.deptData.map(item => item.value),
            barMaxWidth: 36
          }
        ]
      })
    },
    renderMonthChart() {
      if (!this.monthChart) return
      this.monthChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['教师', '学生', '总量'] },
        xAxis: { type: 'category', data: this.monthData.map(item => item.month) },
        yAxis: { type: 'value' },
        series: [
          { name: '教师', type: 'line', smooth: true, data: this.monthData.map(item => item.teacherCount) },
          { name: '学生', type: 'line', smooth: true, data: this.monthData.map(item => item.studentCount) },
          { name: '总量', type: 'line', smooth: true, data: this.monthData.map(item => item.totalCount) }
        ]
      })
    },
    resizeCharts() {
      if (this.roleChart) this.roleChart.resize()
      if (this.deptChart) this.deptChart.resize()
      if (this.monthChart) this.monthChart.resize()
    },
    disposeCharts() {
      if (this.roleChart) this.roleChart.dispose()
      if (this.deptChart) this.deptChart.dispose()
      if (this.monthChart) this.monthChart.dispose()
      this.roleChart = null
      this.deptChart = null
      this.monthChart = null
    }
  }
}
</script>

<style scoped lang="scss">
.mb16 {
  margin-bottom: 16px;
}
.mt16 {
  margin-top: 16px;
}
.card {
  .t {
    color: #777;
    font-size: 14px;
    margin-bottom: 6px;
  }
  .v {
    color: #1f2d3d;
    font-size: 28px;
    font-weight: 600;
  }
}
.chart {
  height: 320px;
}
</style>
