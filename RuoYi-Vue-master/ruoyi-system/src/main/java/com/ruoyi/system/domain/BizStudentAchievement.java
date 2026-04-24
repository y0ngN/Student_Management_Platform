package com.ruoyi.system.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class BizStudentAchievement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long achievementId;

    private Long studentUserId;

    @Excel(name = "学生姓名")
    private String studentName;

    @Excel(name = "学号")
    private String studentNo;

    @Excel(name = "院系/部门")
    private String departmentName;

    @Excel(name = "成果标题")
    private String achievementTitle;

    @Excel(name = "成果类型")
    private String achievementType;

    @Excel(name = "成果等级")
    private String achievementLevel;

    @Excel(name = "获奖/完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date awardDate;

    private String description;

    private String attachment;

    @Excel(name = "审核状态")
    private String auditStatus;

    private Long auditByUserId;

    private String auditByName;

    private Date auditTime;

    private String auditRemark;

    private Date beginDate;

    private Date endDate;

    public Long getAchievementId()
    {
        return achievementId;
    }

    public void setAchievementId(Long achievementId)
    {
        this.achievementId = achievementId;
    }

    public Long getStudentUserId()
    {
        return studentUserId;
    }

    public void setStudentUserId(Long studentUserId)
    {
        this.studentUserId = studentUserId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getStudentNo()
    {
        return studentNo;
    }

    public void setStudentNo(String studentNo)
    {
        this.studentNo = studentNo;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getAchievementTitle()
    {
        return achievementTitle;
    }

    public void setAchievementTitle(String achievementTitle)
    {
        this.achievementTitle = achievementTitle;
    }

    public String getAchievementType()
    {
        return achievementType;
    }

    public void setAchievementType(String achievementType)
    {
        this.achievementType = achievementType;
    }

    public String getAchievementLevel()
    {
        return achievementLevel;
    }

    public void setAchievementLevel(String achievementLevel)
    {
        this.achievementLevel = achievementLevel;
    }

    public Date getAwardDate()
    {
        return awardDate;
    }

    public void setAwardDate(Date awardDate)
    {
        this.awardDate = awardDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
