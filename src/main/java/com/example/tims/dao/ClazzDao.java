package com.example.tims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tims.dto.QueryDto;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClazzDao extends BaseMapper<Clazz> {
//    int getClazzStudentCount(@Param("clazzId") String clazzId);
//
//    int setClazzStudentCount(@Param("clazzId") String clazzId, @Param("count")int count);

    List<Clazz> getAllClazz();

    int add(Clazz clazz);

    int delete(String id);

    int multipleDelete(@Param("ids") List<String> list);

    int update(Clazz clazz);
    List<Clazz> getClazzByTeacher(@Param("teacherId")String teacherId);
    List<Clazz> accurateQuire(QueryDto queryDto);

    List<Clazz> quire(QueryDto queryDto);

    int saveTableConfig(@Param("tableTarget") String tableTarget, @Param("configValue") String configValue);

    String getTableConfig(@Param("tableTarget") String tableTarget);
}