package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BaseStaff;

public interface BaseStaffMapper
{
    BaseStaff selectByUserId(Long userId);

    BaseStaff selectByStaffId(String staffId);

    int insertBaseStaff(BaseStaff staff);

    int updateBaseStaff(BaseStaff staff);
}
