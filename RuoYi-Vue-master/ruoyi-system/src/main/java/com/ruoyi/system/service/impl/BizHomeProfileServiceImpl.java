package com.ruoyi.system.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.BaseStaff;
import com.ruoyi.system.domain.BaseStudent;
import com.ruoyi.system.domain.BizHomeProfileUpdate;
import com.ruoyi.system.domain.StaffUniversityDetail;
import com.ruoyi.system.mapper.BaseStaffMapper;
import com.ruoyi.system.mapper.BaseStudentMapper;
import com.ruoyi.system.mapper.StaffUniversityDetailMapper;
import com.ruoyi.system.service.IBizHomeProfileService;
import com.ruoyi.system.service.ISysUserService;

@Service
public class BizHomeProfileServiceImpl implements IBizHomeProfileService
{
    @Autowired
    private BaseStaffMapper baseStaffMapper;

    @Autowired
    private StaffUniversityDetailMapper staffUniversityDetailMapper;

    @Autowired
    private BaseStudentMapper baseStudentMapper;

    @Autowired
    private ISysUserService userService;

    @Override
    public Map<String, Object> getHomeProfile(Long userId, List<SysRole> roles, SysUser user)
    {
        String roleType = resolveRoleType(roles);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("roleType", roleType);
        result.put("userId", userId);
        result.put("userName", user.getUserName());
        result.put("nickName", user.getNickName());

        if ("teacher".equals(roleType))
        {
            BaseStaff staff = baseStaffMapper.selectByUserId(userId);
            if (staff == null)
            {
                staff = defaultStaff(user);
            }
            StaffUniversityDetail detail = staffUniversityDetailMapper.selectByEmployeeId(staff.getStaffId());
            if (detail == null)
            {
                detail = new StaffUniversityDetail();
                detail.setEmployeeId(staff.getStaffId());
            }
            result.put("staff", staff);
            result.put("staffDetail", detail);
        }
        else if ("student".equals(roleType))
        {
            BaseStudent student = baseStudentMapper.selectByUserId(userId);
            if (student == null)
            {
                student = defaultStudent(user);
            }
            result.put("student", student);
        }
        return result;
    }

    @Override
    public int updateHomeProfile(Long userId, List<SysRole> roles, SysUser user, BizHomeProfileUpdate update)
    {
        String roleType = resolveRoleType(roles);
        int rows = 0;
        if ("teacher".equals(roleType))
        {
            BaseStaff staff = update.getStaff() == null ? defaultStaff(user) : update.getStaff();
            BaseStaff old = baseStaffMapper.selectByUserId(userId);
            if (old != null)
            {
                staff.setStaffId(old.getStaffId());
            }
            if (staff.getStaffId() == null || "".equals(staff.getStaffId().trim()))
            {
                staff.setStaffId(user.getUserName());
            }
            if (staff.getRealName() == null || "".equals(staff.getRealName().trim()))
            {
                staff.setRealName(user.getNickName());
            }
            staff.setUserId(userId);
            if (old == null)
            {
                rows += baseStaffMapper.insertBaseStaff(staff);
            }
            else
            {
                rows += baseStaffMapper.updateBaseStaff(staff);
            }

            StaffUniversityDetail detail = update.getStaffDetail() == null ? new StaffUniversityDetail() : update.getStaffDetail();
            detail.setEmployeeId(staff.getStaffId());
            StaffUniversityDetail oldDetail = staffUniversityDetailMapper.selectByEmployeeId(staff.getStaffId());
            if (oldDetail == null)
            {
                rows += staffUniversityDetailMapper.insertStaffUniversityDetail(detail);
            }
            else
            {
                rows += staffUniversityDetailMapper.updateStaffUniversityDetail(detail);
            }
            syncSysUser(userId, staff.getRealName(), staff.getPhone(), staff.getEmail(), staff.getDeptId());
        }
        else if ("student".equals(roleType))
        {
            BaseStudent student = update.getStudent() == null ? defaultStudent(user) : update.getStudent();
            BaseStudent old = baseStudentMapper.selectByUserId(userId);
            if (old != null)
            {
                student.setStudentId(old.getStudentId());
            }
            if (student.getStudentId() == null || "".equals(student.getStudentId().trim()))
            {
                student.setStudentId(user.getUserName());
            }
            if (student.getStudentName() == null || "".equals(student.getStudentName().trim()))
            {
                student.setStudentName(user.getNickName());
            }
            student.setUserId(userId);
            if (old == null)
            {
                rows += baseStudentMapper.insertBaseStudent(student);
            }
            else
            {
                rows += baseStudentMapper.updateBaseStudent(student);
            }
            syncSysUser(userId, student.getStudentName(), null, null, student.getDeptId());
        }
        return rows;
    }

    private void syncSysUser(Long userId, String nickName, String phone, String email, Long deptId)
    {
        SysUser saveUser = new SysUser();
        saveUser.setUserId(userId);
        saveUser.setNickName(nickName);
        saveUser.setPhonenumber(phone);
        saveUser.setEmail(email);
        saveUser.setDeptId(deptId);
        userService.updateUserProfile(saveUser);
    }

    private BaseStaff defaultStaff(SysUser user)
    {
        BaseStaff staff = new BaseStaff();
        staff.setStaffId(user.getUserName());
        staff.setUserId(user.getUserId());
        staff.setRealName(user.getNickName());
        staff.setPhone(user.getPhonenumber());
        staff.setEmail(user.getEmail());
        staff.setDeptId(user.getDeptId());
        staff.setStaffStatus("在职");
        return staff;
    }

    private BaseStudent defaultStudent(SysUser user)
    {
        BaseStudent student = new BaseStudent();
        student.setStudentId(user.getUserName());
        student.setUserId(user.getUserId());
        student.setStudentName(user.getNickName());
        student.setDeptId(user.getDeptId());
        return student;
    }

    private String resolveRoleType(List<SysRole> roles)
    {
        if (roles != null)
        {
            for (SysRole role : roles)
            {
                if (role != null && "teacher".equals(role.getRoleKey()))
                {
                    return "teacher";
                }
            }
            for (SysRole role : roles)
            {
                if (role != null && "student".equals(role.getRoleKey()))
                {
                    return "student";
                }
            }
        }
        return "other";
    }
}
