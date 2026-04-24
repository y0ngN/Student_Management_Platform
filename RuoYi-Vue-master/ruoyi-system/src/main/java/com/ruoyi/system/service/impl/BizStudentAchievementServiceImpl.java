package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizStudentAchievement;
import com.ruoyi.system.mapper.BizStudentAchievementMapper;
import com.ruoyi.system.service.IBizStudentAchievementService;

@Service
public class BizStudentAchievementServiceImpl implements IBizStudentAchievementService
{
    @Autowired
    private BizStudentAchievementMapper studentAchievementMapper;

    @Override
    public BizStudentAchievement selectStudentAchievementById(Long achievementId)
    {
        return studentAchievementMapper.selectStudentAchievementById(achievementId);
    }

    @Override
    public List<BizStudentAchievement> selectStudentAchievementList(BizStudentAchievement query)
    {
        return studentAchievementMapper.selectStudentAchievementList(query);
    }

    @Override
    public int insertStudentAchievement(BizStudentAchievement achievement)
    {
        return studentAchievementMapper.insertStudentAchievement(achievement);
    }

    @Override
    public int updateStudentAchievement(BizStudentAchievement achievement)
    {
        return studentAchievementMapper.updateStudentAchievement(achievement);
    }

    @Override
    public int deleteStudentAchievementByIds(Long[] achievementIds)
    {
        return studentAchievementMapper.deleteStudentAchievementByIds(achievementIds);
    }

    @Override
    public int auditStudentAchievement(BizStudentAchievement achievement)
    {
        return studentAchievementMapper.auditStudentAchievement(achievement);
    }

    @Override
    public long countStudentAchievement(BizStudentAchievement query)
    {
        return studentAchievementMapper.countStudentAchievement(query);
    }

    @Override
    public long countStudentAchievementApproved(BizStudentAchievement query)
    {
        return studentAchievementMapper.countStudentAchievementApproved(query);
    }

    @Override
    public List<Map<String, Object>> countStudentAchievementByDepartment(BizStudentAchievement query)
    {
        return studentAchievementMapper.countStudentAchievementByDepartment(query);
    }

    @Override
    public List<Map<String, Object>> countStudentAchievementByMonth(BizStudentAchievement query)
    {
        return studentAchievementMapper.countStudentAchievementByMonth(query);
    }
}
