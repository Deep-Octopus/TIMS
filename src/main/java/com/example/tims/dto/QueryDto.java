package com.example.tims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
public class QueryDto {

    private String fieldName;

    private String value;
}
