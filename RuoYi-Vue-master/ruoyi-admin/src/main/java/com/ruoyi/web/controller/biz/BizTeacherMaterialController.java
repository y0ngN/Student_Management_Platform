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
import com.ruoyi.system.domain.BizTeacherMaterial;
import com.ruoyi.system.service.IBizTeacherMaterialService;

@RestController
@RequestMapping("/biz/teacherMaterial")
public class BizTeacherMaterialController extends BaseController
{
    @Autowired
    private IBizTeacherMaterialService teacherMaterialService;

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTeacherMaterial query)
    {
        if (isTeacherRole() && !isAdminRole())
        {
            query.setTeacherUserId(getUserId());
        }
        startPage();
        List<BizTeacherMaterial> list = teacherMaterialService.selectTeacherMaterialList(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:list')")
    @GetMapping("/{materialId}")
    public AjaxResult getInfo(@PathVariable Long materialId)
    {
        BizTeacherMaterial material = teacherMaterialService.selectTeacherMaterialById(materialId);
        if (material == null)
        {
            return error("记录不存在");
        }
        if (isTeacherRole() && !isAdminRole() && !getUserId().equals(material.getTeacherUserId()))
        {
            return error("无权限访问该记录");
        }
        return success(material);
    }

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:add')")
    @Log(title = "教师科研材料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizTeacherMaterial material)
    {
        if (isTeacherRole() && !isAdminRole())
        {
            material.setTeacherUserId(getUserId());
            material.setTeacherName(getLoginUser().getUser().getNickName());
        }
        else if (material.getTeacherUserId() == null)
        {
            return error("管理员新增时必须指定教师用户ID");
        }
        if (material.getSubmitDate() == null)
        {
            material.setSubmitDate(new Date());
        }
        material.setAuditStatus("0");
        material.setCreateBy(getUsername());
        return toAjax(teacherMaterialService.insertTeacherMaterial(material));
    }

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:edit')")
    @Log(title = "教师科研材料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizTeacherMaterial material)
    {
        BizTeacherMaterial old = teacherMaterialService.selectTeacherMaterialById(material.getMaterialId());
        if (old == null)
        {
            return error("记录不存在");
        }
        if (isTeacherRole() && !isAdminRole())
        {
            if (!getUserId().equals(old.getTeacherUserId()))
            {
                return error("无权限修改该记录");
            }
            if ("1".equals(old.getAuditStatus()))
            {
                return error("已审核通过的数据不允许修改");
            }
            material.setTeacherUserId(old.getTeacherUserId());
            material.setTeacherName(old.getTeacherName());
            material.setAuditStatus("0");
        }
        material.setUpdateBy(getUsername());
        return toAjax(teacherMaterialService.updateTeacherMaterial(material));
    }

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:remove')")
    @Log(title = "教师科研材料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(teacherMaterialService.deleteTeacherMaterialByIds(materialIds));
    }

    @PreAuthorize("@ss.hasPermi('biz:teacherMaterial:audit')")
    @Log(title = "教师科研材料审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody BizTeacherMaterial material)
    {
        if (material.getMaterialId() == null)
        {
            return error("materialId不能为空");
        }
        if (!"1".equals(material.getAuditStatus()) && !"2".equals(material.getAuditStatus()))
        {
            return error("auditStatus仅允许1(通过)或2(驳回)");
        }
        material.setAuditByUserId(getUserId());
        material.setAuditByName(getLoginUser().getUser().getNickName());
        material.setAuditTime(new Date());
        material.setUpdateBy(getUsername());
        return toAjax(teacherMaterialService.auditTeacherMaterial(material));
    }

    private boolean isAdminRole()
    {
        return hasRoleKey("admin");
    }

    private boolean isTeacherRole()
    {
        return hasRoleKey("teacher");
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
