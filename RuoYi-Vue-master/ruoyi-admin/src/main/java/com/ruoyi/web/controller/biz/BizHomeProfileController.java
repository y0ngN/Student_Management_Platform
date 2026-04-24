package com.ruoyi.web.controller.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizHomeProfileUpdate;
import com.ruoyi.system.service.IBizHomeProfileService;

@RestController
@RequestMapping("/biz/home")
public class BizHomeProfileController extends BaseController
{
    @Autowired
    private IBizHomeProfileService homeProfileService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public AjaxResult profile()
    {
        SysUser user = getLoginUser().getUser();
        return success(homeProfileService.getHomeProfile(getUserId(), user.getRoles(), user));
    }

    @PreAuthorize("isAuthenticated()")
    @Log(title = "首页个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/profile")
    public AjaxResult updateProfile(@RequestBody BizHomeProfileUpdate update)
    {
        SysUser user = getLoginUser().getUser();
        int rows = homeProfileService.updateHomeProfile(getUserId(), user.getRoles(), user, update);
        return toAjax(rows);
    }
}
