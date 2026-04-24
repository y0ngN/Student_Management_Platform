package com.ruoyi.web.controller.biz;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizStudentAchievement;
import com.ruoyi.system.service.IBizStudentAchievementService;

@RestController
@RequestMapping("/biz/studentAchievement")
public class BizStudentAchievementController extends BaseController
{
    @Autowired
    private IBizStudentAchievementService studentAchievementService;

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizStudentAchievement query)
    {
        if (isStudentRole() && !isAdminRole())
        {
            query.setStudentUserId(getUserId());
        }
        startPage();
        List<BizStudentAchievement> list = studentAchievementService.selectStudentAchievementList(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:list')")
    @GetMapping("/{achievementId}")
    public AjaxResult getInfo(@PathVariable Long achievementId)
    {
        BizStudentAchievement achievement = studentAchievementService.selectStudentAchievementById(achievementId);
        if (achievement == null)
        {
            return error("记录不存在");
        }
        if (isStudentRole() && !isAdminRole() && !getUserId().equals(achievement.getStudentUserId()))
        {
            return error("无权限访问该记录");
        }
        return success(achievement);
    }

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:add')")
    @Log(title = "学生成果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizStudentAchievement achievement)
    {
        if (isStudentRole() && !isAdminRole())
        {
            achievement.setStudentUserId(getUserId());
            achievement.setStudentName(getLoginUser().getUser().getNickName());
        }
        else if (achievement.getStudentUserId() == null)
        {
            return error("管理员新增时必须指定学生用户ID");
        }
        if (achievement.getAwardDate() == null)
        {
            achievement.setAwardDate(new Date());
        }
        achievement.setAuditStatus("0");
        achievement.setCreateBy(getUsername());
        return toAjax(studentAchievementService.insertStudentAchievement(achievement));
    }

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:edit')")
    @Log(title = "学生成果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizStudentAchievement achievement)
    {
        BizStudentAchievement old = studentAchievementService.selectStudentAchievementById(achievement.getAchievementId());
        if (old == null)
        {
            return error("记录不存在");
        }
        if (isStudentRole() && !isAdminRole())
        {
            if (!getUserId().equals(old.getStudentUserId()))
            {
                return error("无权限修改该记录");
            }
            if ("1".equals(old.getAuditStatus()))
            {
                return error("已审核通过的数据不允许修改");
            }
            achievement.setStudentUserId(old.getStudentUserId());
            achievement.setStudentName(old.getStudentName());
            achievement.setAuditStatus("0");
        }
        achievement.setUpdateBy(getUsername());
        return toAjax(studentAchievementService.updateStudentAchievement(achievement));
    }

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:remove')")
    @Log(title = "学生成果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{achievementIds}")
    public AjaxResult remove(@PathVariable Long[] achievementIds)
    {
        return toAjax(studentAchievementService.deleteStudentAchievementByIds(achievementIds));
    }

    @PreAuthorize("@ss.hasPermi('biz:studentAchievement:audit')")
    @Log(title = "学生成果审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody BizStudentAchievement achievement)
    {
        if (achievement.getAchievementId() == null)
        {
            return error("achievementId不能为空");
        }
        if (!"1".equals(achievement.getAuditStatus()) && !"2".equals(achievement.getAuditStatus()))
        {
            return error("auditStatus仅允许1(通过)或2(驳回)");
        }
        achievement.setAuditByUserId(getUserId());
        achievement.setAuditByName(getLoginUser().getUser().getNickName());
        achievement.setAuditTime(new Date());
        achievement.setUpdateBy(getUsername());
        return toAjax(studentAchievementService.auditStudentAchievement(achievement));
    }

    private boolean isAdminRole()
    {
        return hasRoleKey("admin");
    }

    private boolean isStudentRole()
    {
        return hasRoleKey("student");
    }

    private boolean hasRoleKey(String roleKey)
    {
        List<SysRole> roleList = getLoginUser().getUser().getRoles();
        if (roleList == null)
        {
            return false;
        }
        for (SysRole role : roleList)
        {
            if (role != null && roleKey.equals(role.getRoleKey()))
            {
                return true;
            }
        }
        return false;
    }
}
