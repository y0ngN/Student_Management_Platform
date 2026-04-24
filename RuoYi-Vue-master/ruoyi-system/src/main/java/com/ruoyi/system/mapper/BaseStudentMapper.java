package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BaseStudent;

public interface BaseStudentMapper
{
    BaseStudent selectByUserId(Long userId);

    BaseStudent selectByStudentId(String studentId);

    int insertBaseStudent(BaseStudent student);

    int updateBaseStudent(BaseStudent student);
}
