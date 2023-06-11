package com.example.tims.controller;

import com.example.tims.dao.ClazzDao;
import com.example.tims.dto.ChangePwdDto;
import com.example.tims.dto.Enum.StatusEnum;
import com.example.tims.dto.RestBean;
import com.example.tims.dto.TableConfigDto;
import com.example.tims.entity.User;
import com.example.tims.service.serviceImpl.AuthServiceImpl;
import com.example.tims.util.LogUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthController {
    @Resource
    ClazzDao clazzDao;
    @Resource
    AuthServiceImpl authService;

    @PostMapping("/login")
    public RestBean<Map<String, String>> login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
    }

    @GetMapping("/logout")
    public RestBean<String> logout(){
        return RestBean.success();
    }
    @PostMapping("/changePwd")
    public RestBean<String> changePassword(@RequestBody ChangePwdDto changePwdDto){
        return authService.changePassword(changePwdDto);
    }

    @GetMapping("/api/auth/getUser")
    public RestBean<Map<String, String>> getUser(HttpServletRequest request){
        return authService.getUser(request.getAttribute("username").toString(),request.getAttribute("user_type").toString());
    }
    @GetMapping("/api/auth/getUserInfo")
    public RestBean<Object> getUserInfo(HttpServletRequest request){
        return authService.getUserDetailInfo(request.getAttribute("username").toString(),request.getAttribute("user_type").toString());
    }
    @GetMapping("/api/auth/getTableConfig/{target}")
    public RestBean<String> getTableConfig(@PathVariable String target){
        return authService.getTableConfig(target);
    }

    @PostMapping("/api/auth/setTableConfig")
    public RestBean<String> saveTableConfig(@RequestBody TableConfigDto tableConfig) {
        try{
            LogUtils.info(tableConfig.getConfigValue());
            if (clazzDao.saveTableConfig(tableConfig.getTableTarget(), tableConfig.getConfigValue()) == 1){
                return RestBean.success();
            }
            return RestBean.failure(StatusEnum.NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(StatusEnum.INTERNAL_SERVER_ERROR);
        }

    }
}
