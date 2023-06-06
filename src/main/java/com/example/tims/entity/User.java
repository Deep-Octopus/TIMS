package com.example.tims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
@AllArgsConstructor
@Data
public class User{
    private String username;
    private String password;
}
