package com.example.tims.service.serviceImpl;

import com.example.tims.dao.StudentDao;
import com.example.tims.dao.TeacherDao;
import com.example.tims.dao.UserDao;
import com.example.tims.dto.ChangePwdDto;
import com.example.tims.dto.Enum.StatusEnum;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Student;
import com.example.tims.entity.Teacher;
import com.example.tims.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserDao userDao;

    @Resource
    StudentDao studentDao;

    @Resource
    TeacherDao teacherDao;

    @Resource
    TokenService tokenService;

    public RestBean<String> getTableConfig(String target) {
        try {
            String config = userDao.getTableConfig(target);
            if (config == null) {
                return RestBean.failure(StatusEnum.NOT_FOUND);
            }
            return RestBean.success(config);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public RestBean<Map<String, String>> getUser(String username, String user_type) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("user_type", user_type);
            map.put("username", username);
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public RestBean<Object> getUserDetailInfo(String username, String user_type) {
        try {
            if (user_type.equals("teacher")) {
                Teacher teacher = teacherDao.getTeacherById(username);
                return RestBean.success(teacher);
            } else if (user_type.equals("student")) {
                Student student = studentDao.getStudentById(username);
                return RestBean.success(student);
            } else {
                return RestBean.failure(StatusEnum.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<Map<String, String>> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        try {
            String user_type = "";
            String user_password = "";

            user_password = studentDao.getPwd(username);

            if (user_password != null) {
                user_type = "student";
            } else {
                user_password = teacherDao.getPwd(username);
                if (user_password == null) return RestBean.failure(StatusEnum.NOT_FOUND);
                user_type = "teacher";
            }

//            比较长度
            if (password.length() != user_password.length()) {
                return RestBean.failure(StatusEnum.UNAUTHORIZED);
            }
            boolean flag = true;
            for (int i = 0; i < password.length(); i++) {
                if (flag && password.charAt(i) != user_password.charAt(i)) {
                    flag = false;
                }
            }
            if (!flag) return RestBean.failure(StatusEnum.UNAUTHORIZED);
//            密码相同，登录成功,生成JWT
            String token = tokenService.generateToken(username, user_type); // 创建一个JWT Token

            map.put("token", token);
            map.put("user_type", user_type);
//            返回token
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public RestBean<String> changePassword(ChangePwdDto changePwdDto) {
        try {
            String password = "";
            if (changePwdDto.getAuth().equals("student")) {
                password = studentDao.getPwd(changePwdDto.getUsername());
            } else if (changePwdDto.getAuth().equals("teacher")) {
                password = teacherDao.getPwd(changePwdDto.getUsername());
            } else {
                return RestBean.failure(StatusEnum.BAD_REQUEST);
            }

//            旧密码输错了
            if (password != null && !password.equals(changePwdDto.getOldPassword())) {
                return RestBean.failure(StatusEnum.FORBIDDEN);
            }
//            旧密码和新密码一样
            if (password.equals(changePwdDto.getNewPassword())) {
                return RestBean.failure(402,"数据没有变化");
            }
            //            设置密码
            int changeRow = 0;

            if (changePwdDto.getAuth().equals("student")) {
                changeRow = studentDao.setPwd(changePwdDto.getUsername(), changePwdDto.getNewPassword());
            } else {
                changeRow = teacherDao.setPwd(changePwdDto.getUsername(), changePwdDto.getNewPassword());
            }
            if (changeRow == 1) {
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestBean<Object> logout(String username, String password) {
        return null;
    }

}
