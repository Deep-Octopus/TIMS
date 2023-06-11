package com.example.tims.controller;

import com.example.tims.dto.MultipleDeleteDto;
import com.example.tims.dto.QueryDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
import com.example.tims.service.serviceImpl.StudentServiceImpl;
import com.example.tims.util.LogUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/student")
public class StudentController {

    @Resource
    StudentServiceImpl studentService;

    @GetMapping("/init")
    public RestBean<List<Student>> initStudent(){
        return studentService.getAllStudent();
    }

    @PostMapping(value = "/add")
    public RestBean<String> addStudent(@RequestBody Student student) {
        return studentService.add(student);
    }

    @GetMapping("/delete")
    public RestBean<String> deleteStudent(@RequestParam String id) {
        return studentService.delete(id);
    }

    @PostMapping("/multipleDelete")
    public RestBean<String> multipleDeleteStudent(@RequestBody MultipleDeleteDto multipleDeleteDto){
        return studentService.multipleDelete(multipleDeleteDto.getIdList());
    }
    @PostMapping("/update")
    public RestBean<String> updateStudent(@RequestBody Student student) {
        return studentService.update(student);
    }

    @PostMapping("/query")
    public RestBean<List<Student>> queryStudent(@RequestBody QueryDto queryDto) {
        LogUtils.info(queryDto.getFieldName());
        return studentService.query(queryDto,false);
    }


}
