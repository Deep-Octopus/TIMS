package com.example.tims.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tims.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @MapKey("user_type")
    List<Map<String,String>> getUserByUsername(@Param("username") String username);

    String getTableConfig(@Param("target") String target);
}
