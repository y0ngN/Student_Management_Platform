package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizStudentAchievement;

public interface BizStudentAchievementMapper
{
    BizStudentAchievement selectStudentAchievementById(Long achievementId);

    List<BizStudentAchievement> selectStudentAchievementList(BizStudentAchievement query);

    int insertStudentAchievement(BizStudentAchievement achievement);

    int updateStudentAchievement(BizStudentAchievement achievement);

    int deleteStudentAchievementByIds(Long[] achievementIds);

    int auditStudentAchievement(BizStudentAchievement achievement);

    long countStudentAchievement(BizStudentAchievement query);

    long countStudentAchievementApproved(BizStudentAchievement query);

    List<Map<String, Object>> countStudentAchievementByDepartment(BizStudentAchievement query);

    List<Map<String, Object>> countStudentAchievementByMonth(BizStudentAchievement query);
}
