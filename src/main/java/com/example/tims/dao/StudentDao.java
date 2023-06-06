package com.example.tims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tims.dto.QueryDto;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
import com.example.tims.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
    String getPwd(@Param("id") String id);
    int setPwd(@Param("id") String id, @Param("newPwd") String newPwd);
    Clazz getClazzByStudentId(@Param("studentClazzId") String studentClazzId);
    List<Student> getAllStudent();

    int add(Student student);

    int delete(String id);

    int deleteByClazz(@Param("clazzId") String clazzId);

    int multipleDelete(@Param("ids") List<String> list);

    int update(Student student);
    List<Student> getStudentsByClazz(@Param("clazzId") String clazzId);

    List<Student> accurateQuire(QueryDto queryDto);

    List<Student> quire(QueryDto queryDto);

    Student getStudentById(@Param("id") String id);
}
