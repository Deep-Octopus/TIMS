package com.example.tims.service.serviceImpl;

import com.example.tims.dao.ClazzDao;
import com.example.tims.dao.StudentDao;
import com.example.tims.dto.QueryDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
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
public class ClazzServiceImpl implements EntityService<Clazz> {
    @Resource
    ClazzDao clazzDao;

    @Resource
    StudentDao studentDao;

//    public int getClazzStudentCount(String clazzId){
//        return clazzDao.getClazzStudentCount(clazzId);
//    }
//    public int setClazzStudentCount(String clazzId, int count){
//        return clazzDao.setClazzStudentCount(clazzId, count);
//    }


    public RestBean<List<Clazz>> getAllClazz() {
        try {
            return RestBean.success(clazzDao.getAllClazz());
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
    public RestBean<List<Clazz>> getClazzByTeacher(String teacherId){
        try{
            return RestBean.success(clazzDao.getClazzByTeacher(teacherId));
        }catch (Exception e){
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> add(Clazz clazz) {

        try {
            LogUtils.info("Adding clazz " + clazz);

            if (clazzDao.add(clazz) == 1) {
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
            if (clazzDao.delete(id) == 1) {
//                删除对应班级的学生
                studentDao.deleteByClazz(id);

                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> update(Clazz clazz) {

        try {
            if (clazzDao.update(clazz) == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<List<Clazz>> query(String fieldName, String value, boolean isAccurate) {

        SqlUtil.isTableExists(Clazz.class);

        try {
            QueryDto queryDto = new QueryDto(fieldName,value);
            if (isAccurate) {
                return RestBean.success(clazzDao.accurateQuire(queryDto));
            } else {
                return RestBean.success(clazzDao.quire(queryDto));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
