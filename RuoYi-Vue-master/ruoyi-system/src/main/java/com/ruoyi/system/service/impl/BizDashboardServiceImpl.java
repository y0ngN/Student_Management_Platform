package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizDashboardQuery;
import com.ruoyi.system.domain.BizStudentAchievement;
import com.ruoyi.system.domain.BizTeacherMaterial;
import com.ruoyi.system.service.IBizDashboardService;
import com.ruoyi.system.service.IBizStudentAchievementService;
import com.ruoyi.system.service.IBizTeacherMaterialService;

@Service
public class BizDashboardServiceImpl implements IBizDashboardService
{
    @Autowired
    private IBizTeacherMaterialService teacherMaterialService;

    @Autowired
    private IBizStudentAchievementService studentAchievementService;

    @Override
    public Map<String, Object> selectOverview(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = buildTeacherQuery(query);
        BizStudentAchievement studentQuery = buildStudentQuery(query);
        long teacherTotal = teacherMaterialService.countTeacherMaterial(teacherQuery);
        long teacherApproved = teacherMaterialService.countTeacherMaterialApproved(teacherQuery);
        long studentTotal = studentAchievementService.countStudentAchievement(studentQuery);
        long studentApproved = studentAchievementService.countStudentAchievementApproved(studentQuery);
        BigDecimal approvedProjectAmount = teacherMaterialService.sumApprovedProjectAmount(teacherQuery);

        Map<String, Object> overview = new LinkedHashMap<>();
        overview.put("teacherTotal", teacherTotal);
        overview.put("teacherApproved", teacherApproved);
        overview.put("studentTotal", studentTotal);
        overview.put("studentApproved", studentApproved);
        overview.put("allTotal", teacherTotal + studentTotal);
        overview.put("approvedTotal", teacherApproved + studentApproved);
        overview.put("approvedProjectAmount", approvedProjectAmount == null ? BigDecimal.ZERO : approvedProjectAmount);
        return overview;
    }

    @Override
    public List<Map<String, Object>> selectRoleStat(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = buildTeacherQuery(query);
        BizStudentAchievement studentQuery = buildStudentQuery(query);
        long teacherTotal = teacherMaterialService.countTeacherMaterial(teacherQuery);
        long studentTotal = studentAchievementService.countStudentAchievement(studentQuery);

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> teacher = new LinkedHashMap<>();
        teacher.put("name", "教师科研材料");
        teacher.put("value", teacherTotal);
        list.add(teacher);

        Map<String, Object> student = new LinkedHashMap<>();
        student.put("name", "学生成果");
        student.put("value", studentTotal);
        list.add(student);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectDepartmentStat(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = buildTeacherQuery(query);
        BizStudentAchievement studentQuery = buildStudentQuery(query);
        List<Map<String, Object>> teacherList = teacherMaterialService.countTeacherMaterialByDepartment(teacherQuery);
        List<Map<String, Object>> studentList = studentAchievementService.countStudentAchievementByDepartment(studentQuery);
        Map<String, Long> counter = new TreeMap<>();

        mergeDepartmentCount(counter, teacherList);
        mergeDepartmentCount(counter, studentList);

        List<Map<String, Object>> merged = new ArrayList<>();
        for (Map.Entry<String, Long> entry : counter.entrySet())
        {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("name", entry.getKey());
            row.put("value", entry.getValue());
            merged.add(row);
        }
        return merged;
    }

    @Override
    public List<Map<String, Object>> selectMonthlyTrend(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = buildTeacherQuery(query);
        BizStudentAchievement studentQuery = buildStudentQuery(query);
        List<Map<String, Object>> teacherList = teacherMaterialService.countTeacherMaterialByMonth(teacherQuery);
        List<Map<String, Object>> studentList = studentAchievementService.countStudentAchievementByMonth(studentQuery);

        Map<String, Map<String, Object>> monthlyMap = new TreeMap<>();
        for (Map<String, Object> item : teacherList)
        {
            String month = String.valueOf(item.get("month"));
            long value = toLong(item.get("value"));
            Map<String, Object> row = monthlyMap.computeIfAbsent(month, m -> createMonthRow(m));
            row.put("teacherCount", value);
            row.put("totalCount", toLong(row.get("totalCount")) + value);
        }
        for (Map<String, Object> item : studentList)
        {
            String month = String.valueOf(item.get("month"));
            long value = toLong(item.get("value"));
            Map<String, Object> row = monthlyMap.computeIfAbsent(month, m -> createMonthRow(m));
            row.put("studentCount", value);
            row.put("totalCount", toLong(row.get("totalCount")) + value);
        }
        return new ArrayList<>(monthlyMap.values());
    }

    @Override
    public Map<String, Object> selectRawSubmissions(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = buildTeacherQuery(query);
        BizStudentAchievement studentQuery = buildStudentQuery(query);
        List<BizTeacherMaterial> teacherRows = teacherMaterialService.selectTeacherMaterialList(teacherQuery).stream()
            .limit(8)
            .collect(Collectors.toList());
        List<BizStudentAchievement> studentRows = studentAchievementService.selectStudentAchievementList(studentQuery).stream()
            .limit(8)
            .collect(Collectors.toList());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("teacherRows", teacherRows);
        result.put("studentRows", studentRows);
        return result;
    }

    private Map<String, Object> createMonthRow(String month)
    {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("month", month);
        row.put("teacherCount", 0L);
        row.put("studentCount", 0L);
        row.put("totalCount", 0L);
        return row;
    }

    private void mergeDepartmentCount(Map<String, Long> counter, List<Map<String, Object>> list)
    {
        for (Map<String, Object> item : list)
        {
            String name = String.valueOf(item.get("name"));
            long value = toLong(item.get("value"));
            counter.put(name, counter.getOrDefault(name, 0L) + value);
        }
    }

    private long toLong(Object value)
    {
        if (value == null)
        {
            return 0L;
        }
        if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }

    private BizTeacherMaterial buildTeacherQuery(BizDashboardQuery query)
    {
        BizTeacherMaterial teacherQuery = new BizTeacherMaterial();
        teacherQuery.setDepartmentName(query.getDepartmentName());
        teacherQuery.setBeginDate(query.getBeginDate());
        teacherQuery.setEndDate(query.getEndDate());
        return teacherQuery;
    }

    private BizStudentAchievement buildStudentQuery(BizDashboardQuery query)
    {
        BizStudentAchievement studentQuery = new BizStudentAchievement();
        studentQuery.setDepartmentName(query.getDepartmentName());
        studentQuery.setBeginDate(query.getBeginDate());
        studentQuery.setEndDate(query.getEndDate());
        return studentQuery;
    }
}
