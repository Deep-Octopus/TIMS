package com.example.tims.dto;

import com.example.tims.dto.Enum.StatusEnum;
import lombok.Data;

@Data
public class RestBean <T>{

    private int status;

    private String message;

    private T data;

    private RestBean(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> RestBean<T> success(){
        return new RestBean<T>(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMsg(),null);
    }
    public static <T> RestBean<T> success(T data){
        return new RestBean<T>(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMsg(),data);
    }
    public static <T> RestBean<T> failure(StatusEnum statusEnum){
        return new RestBean<>(statusEnum.getCode(), statusEnum.getMsg(), null);
    }
    public static <T> RestBean<T> failure(int code, String message){
        return new RestBean<>(code, message, null);
    }

    public static <T> RestBean<T> failure(StatusEnum statusEnum,T data){
        return new RestBean<>(statusEnum.getCode(),statusEnum.getMsg(),data);
    }
}
