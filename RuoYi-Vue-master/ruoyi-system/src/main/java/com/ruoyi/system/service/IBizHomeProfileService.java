package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.BizHomeProfileUpdate;

public interface IBizHomeProfileService
{
    Map<String, Object> getHomeProfile(Long userId, List<SysRole> roles, SysUser user);

    int updateHomeProfile(Long userId, List<SysRole> roles, SysUser user, BizHomeProfileUpdate update);
}
