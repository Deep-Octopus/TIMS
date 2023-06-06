package com.example.tims.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.tims.dto.RestBean;
import com.example.tims.service.serviceImpl.TokenService;
import com.example.tims.util.HttpContextUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {

            // * 这里因为拦截了OPTION请求，返回false的话是不会有响应头的，需要我们自己写返回头

            //设置允许的访问源
            response.setHeader("Access-Control-Allow-Origin", "*");
            //设置验证
            response.setHeader("Access-Control-Allow-Credentials","false");
            //设置允许的请求类型
            response.setHeader("Access-Control-Allow-Methods", "*");
            //设置超时时间
            response.setHeader("Access-Control-Max-Age", "1800");
            //设置允许的请求头参数
            response.setHeader("Access-Control-Allow-Headers", "*");

            //返回203
            response.setStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());

            //拦截掉，不让继续往下走
            return false;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String token = authorizationHeader.substring(7);
        if (!tokenService.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Map<String,String> infoMap = tokenService.getUsernameAndUserTypeFromToken(token);

        request.setAttribute("username", infoMap.get("username"));
        request.setAttribute("user_type", infoMap.get("user_type"));
        return true;
    }

    //返回错误信息
    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
//        return Result.buildErr(status,msg);

        String json = JSON.toJSONString(RestBean.failure(status,msg));
        httpResponse.getWriter().print(json);
    }
}
