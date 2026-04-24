package com.ruoyi.system.domain;

public class BizHomeProfileUpdate
{
    private BaseStaff staff;

    private StaffUniversityDetail staffDetail;

    private BaseStudent student;

    public BaseStaff getStaff()
    {
        return staff;
    }

    public void setStaff(BaseStaff staff)
    {
        this.staff = staff;
    }

    public StaffUniversityDetail getStaffDetail()
    {
        return staffDetail;
    }

    public void setStaffDetail(StaffUniversityDetail staffDetail)
    {
        this.staffDetail = staffDetail;
    }

    public BaseStudent getStudent()
    {
        return student;
    }

    public void setStudent(BaseStudent student)
    {
        this.student = student;
    }
}
