package com.example.tims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String id;

    private String name;

    private String gender;

    private String educationalBackground;

    private String phoneNumber;
}
