package com.example.tims.service.serviceImpl;

import com.example.tims.dao.TeacherDao;
import com.example.tims.dto.QueryDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Teacher;
import com.example.tims.service.EntityService;
import com.example.tims.util.LogUtils;
import com.example.tims.util.SqlUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.tims.dto.Enum.StatusEnum;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements EntityService<Teacher> {
    @Resource
    TeacherDao teacherDao;


    public RestBean<List<Teacher>> getAllTeacher() {
        try {
            return RestBean.success(teacherDao.getAllTeacher());
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> add(Teacher teacher) {

        try {
            LogUtils.info("Adding teacher " + teacher);

            if (teacherDao.add(teacher) == 1) {
                return RestBean.success();
            }

            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public RestBean<String> delete(String id) {
        try {
            if (teacherDao.delete(id) == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> update(Teacher teacher) {

        try {
            if (teacherDao.update(teacher) == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<List<Teacher>> query(String fieldName, String value, boolean isAccurate) {

        SqlUtil.isTableExists(Teacher.class);

        try {
            QueryDto queryDto = new QueryDto(fieldName,value);
            if (isAccurate) {
                return RestBean.success(teacherDao.accurateQuire(queryDto));
            } else {
                return RestBean.success(teacherDao.quire(queryDto));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public RestBean<String> getTableConfig(String tableTarget){
        try{
            return RestBean.success(teacherDao.getTableConfig(tableTarget));
        }catch (Exception e){
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
