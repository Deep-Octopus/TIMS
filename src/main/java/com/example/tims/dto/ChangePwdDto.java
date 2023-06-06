package com.example.tims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePwdDto {
    private String username;
    private String auth;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
