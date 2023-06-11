package com.example.tims.controller;

import com.example.tims.dto.MultipleDeleteDto;
import com.example.tims.dto.QueryDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
import com.example.tims.entity.Teacher;
import com.example.tims.service.serviceImpl.TeacherServiceImpl;
import com.example.tims.util.LogUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/teacher")
public class TeacherController {

    @Resource
    TeacherServiceImpl teacherService;

    @GetMapping("/init")
    public RestBean<List<Teacher>> initTeacher(){
        return teacherService.getAllTeacher();
    }
    @PostMapping(value = "/add")
    public RestBean<String> addTeacher(@RequestBody Teacher teacher) {
        return teacherService.add(teacher);
    }

    @PostMapping("/delete")
    public RestBean<String> deleteTeacher(@RequestParam("id") String id) {
        return teacherService.delete(id);
    }

    @PostMapping("/multipleDelete")
    public RestBean<String> multipleDeleteStudent(@RequestBody MultipleDeleteDto multipleDeleteDto){
        return teacherService.multipleDelete(multipleDeleteDto.getIdList());
    }

    @PostMapping("/update")
    public RestBean<String> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.update(teacher);
    }

    @PostMapping("/query")
    public RestBean<List<Teacher>> queryTeacher(@RequestBody QueryDto queryDto) {
        LogUtils.info(queryDto.getFieldName());
        return teacherService.query(queryDto,false);
    }

    @GetMapping("/getTableConfig/{tableTarget}")
    public RestBean<String> getTableConfig(@PathVariable String tableTarget){
        return teacherService.getTableConfig(tableTarget);
    }
}
