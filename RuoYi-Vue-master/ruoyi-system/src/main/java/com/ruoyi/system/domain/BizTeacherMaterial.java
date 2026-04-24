package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class BizTeacherMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long materialId;

    private Long teacherUserId;

    @Excel(name = "教师姓名")
    private String teacherName;

    @Excel(name = "院系/部门")
    private String departmentName;

    @Excel(name = "材料标题")
    private String title;

    @Excel(name = "材料类型")
    private String materialType;

    @Excel(name = "项目资金")
    private BigDecimal projectAmount;

    private String description;

    @Excel(name = "提交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitDate;

    private String attachment;

    @Excel(name = "审核状态")
    private String auditStatus;

    private Long auditByUserId;

    private String auditByName;

    private Date auditTime;

    private String auditRemark;

    private Date beginDate;

    private Date endDate;

    public Long getMaterialId()
    {
        return materialId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getTeacherUserId()
    {
        return teacherUserId;
    }

    public void setTeacherUserId(Long teacherUserId)
    {
        this.teacherUserId = teacherUserId;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMaterialType()
    {
        return materialType;
    }

    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    public BigDecimal getProjectAmount()
    {
        return projectAmount;
    }

    public void setProjectAmount(BigDecimal projectAmount)
    {
        this.projectAmount = projectAmount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getSubmitDate()
    {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate)
    {
        this.submitDate = submitDate;
    }

    public String getAttachment()
    {
        return attachment;
    }

    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }

    public Long getAuditByUserId()
    {
        return auditByUserId;
    }

    public void setAuditByUserId(Long auditByUserId)
    {
        this.auditByUserId = auditByUserId;
    }

    public String getAuditByName()
    {
        return auditByName;
    }

    public void setAuditByName(String auditByName)
    {
        this.auditByName = auditByName;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public String getAuditRemark()
    {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark)
    {
        this.auditRemark = auditRemark;
    }

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
}
