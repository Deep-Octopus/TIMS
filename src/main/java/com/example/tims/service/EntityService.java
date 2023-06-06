package com.example.tims.service;

import com.example.tims.dto.RestBean;

import java.util.List;

public interface EntityService<T> {
    RestBean<String> add(T t);

    RestBean<String> delete(String id);

    RestBean<String> update(T t);

    RestBean<List<T>> query(String fieldName, String value,boolean isAccurate);
}
