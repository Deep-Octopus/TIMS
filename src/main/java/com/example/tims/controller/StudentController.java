package com.example.tims.controller;

import com.example.tims.dto.MultipleDeleteDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Student;
import com.example.tims.service.serviceImpl.StudentServiceImpl;
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

//    @PostMapping("/checkClazz")
//    public RestBean<String> checkClazz(@RequestBody Student student) {
//        return studentService.checkClazz(student);
//    }
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
    public RestBean<List<Student>> queryStudent(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("value") String value,
            @RequestParam("isAccurate") boolean isAccurate) {
        return studentService.query(fieldName, value, isAccurate);
    }


}
