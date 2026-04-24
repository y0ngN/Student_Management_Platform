package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizDashboardQuery;

public interface IBizDashboardService
{
    Map<String, Object> selectOverview(BizDashboardQuery query);

    List<Map<String, Object>> selectRoleStat(BizDashboardQuery query);

    List<Map<String, Object>> selectDepartmentStat(BizDashboardQuery query);

    List<Map<String, Object>> selectMonthlyTrend(BizDashboardQuery query);

    Map<String, Object> selectRawSubmissions(BizDashboardQuery query);
}
