package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.StaffUniversityDetail;

public interface StaffUniversityDetailMapper
{
    StaffUniversityDetail selectByEmployeeId(String employeeId);

    int insertStaffUniversityDetail(StaffUniversityDetail detail);

    int updateStaffUniversityDetail(StaffUniversityDetail detail);
}
