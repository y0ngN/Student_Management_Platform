package com.ruoyi.system.domain;

public class BaseStudent
{
    private String studentId;

    private Long userId;

    private String studentName;

    private String major;

    private String currentGrade;

    private Long deptId;

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getCurrentGrade()
    {
        return currentGrade;
    }

    public void setCurrentGrade(String currentGrade)
    {
        this.currentGrade = currentGrade;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }
}
