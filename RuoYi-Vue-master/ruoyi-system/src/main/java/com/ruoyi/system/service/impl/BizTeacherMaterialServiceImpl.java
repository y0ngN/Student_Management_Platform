package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizTeacherMaterial;
import com.ruoyi.system.mapper.BizTeacherMaterialMapper;
import com.ruoyi.system.service.IBizTeacherMaterialService;

@Service
public class BizTeacherMaterialServiceImpl implements IBizTeacherMaterialService
{
    @Autowired
    private BizTeacherMaterialMapper teacherMaterialMapper;

    @Override
    public BizTeacherMaterial selectTeacherMaterialById(Long materialId)
    {
        return teacherMaterialMapper.selectTeacherMaterialById(materialId);
    }

    @Override
    public List<BizTeacherMaterial> selectTeacherMaterialList(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.selectTeacherMaterialList(query);
    }

    @Override
    public int insertTeacherMaterial(BizTeacherMaterial material)
    {
        return teacherMaterialMapper.insertTeacherMaterial(material);
    }

    @Override
    public int updateTeacherMaterial(BizTeacherMaterial material)
    {
        return teacherMaterialMapper.updateTeacherMaterial(material);
    }

    @Override
    public int deleteTeacherMaterialByIds(Long[] materialIds)
    {
        return teacherMaterialMapper.deleteTeacherMaterialByIds(materialIds);
    }

    @Override
    public int auditTeacherMaterial(BizTeacherMaterial material)
    {
        return teacherMaterialMapper.auditTeacherMaterial(material);
    }

    @Override
    public long countTeacherMaterial(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.countTeacherMaterial(query);
    }

    @Override
    public long countTeacherMaterialApproved(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.countTeacherMaterialApproved(query);
    }

    @Override
    public BigDecimal sumApprovedProjectAmount(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.sumApprovedProjectAmount(query);
    }

    @Override
    public List<Map<String, Object>> countTeacherMaterialByDepartment(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.countTeacherMaterialByDepartment(query);
    }

    @Override
    public List<Map<String, Object>> countTeacherMaterialByMonth(BizTeacherMaterial query)
    {
        return teacherMaterialMapper.countTeacherMaterialByMonth(query);
    }
}
