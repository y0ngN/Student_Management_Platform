package com.ruoyi.web.controller.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.BizDashboardQuery;
import com.ruoyi.system.service.IBizDashboardService;

@RestController
@RequestMapping("/biz/dashboard")
public class BizDashboardController extends BaseController
{
    @Autowired
    private IBizDashboardService dashboardService;

    @PreAuthorize("@ss.hasPermi('biz:dashboard:view')")
    @GetMapping("/overview")
    public AjaxResult overview(BizDashboardQuery query)
    {
        return success(dashboardService.selectOverview(query));
    }

    @PreAuthorize("@ss.hasPermi('biz:dashboard:view')")
    @GetMapping("/roleStat")
    public AjaxResult roleStat(BizDashboardQuery query)
    {
        return success(dashboardService.selectRoleStat(query));
    }

    @PreAuthorize("@ss.hasPermi('biz:dashboard:view')")
    @GetMapping("/departmentStat")
    public AjaxResult departmentStat(BizDashboardQuery query)
    {
        return success(dashboardService.selectDepartmentStat(query));
    }

    @PreAuthorize("@ss.hasPermi('biz:dashboard:view')")
    @GetMapping("/monthlyTrend")
    public AjaxResult monthlyTrend(BizDashboardQuery query)
    {
        return success(dashboardService.selectMonthlyTrend(query));
    }

    @PreAuthorize("@ss.hasPermi('biz:dashboard:view')")
    @GetMapping("/rawSubmissions")
    public AjaxResult rawSubmissions(BizDashboardQuery query)
    {
        return success(dashboardService.selectRawSubmissions(query));
    }
}
