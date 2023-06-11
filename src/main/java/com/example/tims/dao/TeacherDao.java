package com.example.tims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tims.dto.QueryDto;
import com.example.tims.entity.Student;
import com.example.tims.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeacherDao extends BaseMapper<Teacher> {
    int setPwd(@Param("id") String id, @Param("newPwd") String newPwd);

    String getPwd(@Param("id") String id);
    List<Teacher> getAllTeacher();

    int add(Teacher teacher);

    int delete(String id);

    int multipleDelete(@Param("ids") List<String> list);

    int update(Teacher teacher);

    List<Teacher> accurateQuire(QueryDto queryDto);

    List<Teacher> quire(QueryDto queryDto);

    String getTableConfig(@Param("tableTarget") String tableTarget);

    Teacher getTeacherById(@Param("id") String id);
}
