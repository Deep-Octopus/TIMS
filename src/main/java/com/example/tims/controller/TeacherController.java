package com.example.tims.controller;

import com.example.tims.dto.RestBean;
import com.example.tims.entity.Teacher;
import com.example.tims.service.serviceImpl.TeacherServiceImpl;
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

//    @PostMapping("/multipleDelete")
//    public RestBean<String> multipleDelete(@)

    @PostMapping("/update")
    public RestBean<String> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.update(teacher);
    }

    @PostMapping("/query")
    public RestBean<List<Teacher>> queryTeacher(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("value") String value,
            @RequestParam("isAccurate") boolean isAccurate) {
        return teacherService.query(fieldName, value, isAccurate);
    }

    @GetMapping("/getTableConfig/{tableTarget}")
    public RestBean<String> getTableConfig(@PathVariable String tableTarget){
        return teacherService.getTableConfig(tableTarget);
    }
}
