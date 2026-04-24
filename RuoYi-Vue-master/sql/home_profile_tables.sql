-- 首页个人信息功能相关表
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS Dic_Department (
    DeptID BIGINT PRIMARY KEY COMMENT '部门ID',
    DeptName VARCHAR(100) NOT NULL COMMENT '部门名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门字典表';

CREATE TABLE IF NOT EXISTS Base_Staff (
    StaffID VARCHAR(50) PRIMARY KEY COMMENT '教职工号',
    UserID BIGINT COMMENT '关联sys_user.user_id',
    RealName VARCHAR(50) NOT NULL COMMENT '姓名',
    Gender VARCHAR(10) COMMENT '性别',
    IDCard VARCHAR(18) UNIQUE COMMENT '身份证号',
    BirthDate DATE COMMENT '出生年月',
    DeptID BIGINT COMMENT '所属部门ID',
    Phone VARCHAR(20) COMMENT '手机号',
    Email VARCHAR(100) COMMENT '邮箱',
    EntryDate DATE COMMENT '来校时间/入职时间',
    StaffStatus VARCHAR(20) DEFAULT '在职' COMMENT '人员状态 (在职、离职、退休)',
    CONSTRAINT FK_Base_Staff_Dept FOREIGN KEY (DeptID) REFERENCES Dic_Department(DeptID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教职工基础信息表';

CREATE TABLE IF NOT EXISTS Staff_University_Detail (
    EmployeeID VARCHAR(50) PRIMARY KEY COMMENT '关联教职工号',
    AdminPostLevel VARCHAR(50) COMMENT '党政职务级别',
    MainAdminPost VARCHAR(100) COMMENT '党政主职',
    TeacherCertNumber VARCHAR(100) COMMENT '教师资格证号',
    SubjectCategory VARCHAR(50) COMMENT '学科类别',
    ResearchDirection VARCHAR(100) COMMENT '研究方向',
    IsDoubleQualified TINYINT(1) COMMENT '是否双师型教师',
    IsTeachingUndergrad TINYINT(1) COMMENT '是否为本科生上课',
    CounselorCategory VARCHAR(50) COMMENT '辅导员类别',
    CONSTRAINT FK_Staff_Detail_Staff FOREIGN KEY (EmployeeID) REFERENCES Base_Staff(StaffID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校人事记录明细表';

CREATE TABLE IF NOT EXISTS Base_Student (
    StudentID VARCHAR(50) PRIMARY KEY COMMENT '学号',
    UserID BIGINT COMMENT '关联sys_user.user_id',
    StudentName VARCHAR(50) NOT NULL COMMENT '姓名',
    Major VARCHAR(100) COMMENT '专业',
    CurrentGrade VARCHAR(20) COMMENT '年级',
    DeptID BIGINT COMMENT '所属学院ID',
    CONSTRAINT FK_Base_Student_Dept FOREIGN KEY (DeptID) REFERENCES Dic_Department(DeptID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生基础信息表';
