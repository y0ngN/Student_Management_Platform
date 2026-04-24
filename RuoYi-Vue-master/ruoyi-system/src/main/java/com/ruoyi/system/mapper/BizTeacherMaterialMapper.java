package com.ruoyi.system.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizTeacherMaterial;

public interface BizTeacherMaterialMapper
{
    BizTeacherMaterial selectTeacherMaterialById(Long materialId);

    List<BizTeacherMaterial> selectTeacherMaterialList(BizTeacherMaterial query);

    int insertTeacherMaterial(BizTeacherMaterial material);

    int updateTeacherMaterial(BizTeacherMaterial material);

    int deleteTeacherMaterialByIds(Long[] materialIds);

    int auditTeacherMaterial(BizTeacherMaterial material);

    long countTeacherMaterial(BizTeacherMaterial query);

    long countTeacherMaterialApproved(BizTeacherMaterial query);

    BigDecimal sumApprovedProjectAmount(BizTeacherMaterial query);

    List<Map<String, Object>> countTeacherMaterialByDepartment(BizTeacherMaterial query);

    List<Map<String, Object>> countTeacherMaterialByMonth(BizTeacherMaterial query);
}
