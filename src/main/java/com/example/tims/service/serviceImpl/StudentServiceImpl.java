package com.example.tims.service.serviceImpl;

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
public class StudentServiceImpl implements EntityService<Student> {
    @Resource
    StudentDao studentDao;

    public Clazz getClazzByStudentId(String studentClazzId) {

        return studentDao.getClazzByStudentId(studentClazzId);
    }

    public RestBean<List<Student>> getStudentsByClazz(String clazzId){
        try {
            return RestBean.success(studentDao.getStudentsByClazz(clazzId));
        }catch (Exception e){
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
    public RestBean<List<Student>> getAllStudent() {
        try {
            return RestBean.success(studentDao.getAllStudent());
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> add(Student student) {

        try {
            LogUtils.info("Adding student " + student);
            if (this.getClazzByStudentId(student.getId().substring(0, 9)) != null){
                if (studentDao.add(student) == 1) {
                    return RestBean.success();
                }
            } else{
                return RestBean.failure(303,"该班级不存在");
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
            if (studentDao.delete(id) == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public RestBean<String> multipleDelete(List<String> list) {
        try {
            if (studentDao.multipleDelete(list) != 0) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<String> update(Student student) {

        try {
            if (studentDao.update(student) == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<List<Student>> query(String fieldName, String value, boolean isAccurate) {

        SqlUtil.isTableExists(Student.class);

        try {
            QueryDto queryDto = new QueryDto(fieldName, value);
            if (isAccurate) {
                return RestBean.success(studentDao.accurateQuire(queryDto));
            } else {
                return RestBean.success(studentDao.quire(queryDto));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
