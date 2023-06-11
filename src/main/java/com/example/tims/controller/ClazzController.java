package com.example.tims.controller;

import com.example.tims.dto.MultipleDeleteDto;
import com.example.tims.dto.QueryDto;
import com.example.tims.dto.RestBean;
import com.example.tims.entity.Clazz;
import com.example.tims.entity.Student;
import com.example.tims.service.serviceImpl.ClazzServiceImpl;
import com.example.tims.service.serviceImpl.StudentServiceImpl;
import com.example.tims.util.LogUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/clazz")
public class ClazzController {

    @Resource
    ClazzServiceImpl clazzService;

    @Resource
    StudentServiceImpl studentService;

    @GetMapping("/init")
    public RestBean<List<Clazz>> initClazz(){
        return clazzService.getAllClazz();
    }
    @PostMapping(value = "/add")
    public RestBean<String> addClazz(@RequestBody Clazz clazz) {
        return clazzService.add(clazz);
    }

    @PostMapping("/delete")
    public RestBean<String> deleteClazz(@RequestParam("id") String id) {
        return clazzService.delete(id);
    }

    @PostMapping("/multipleDelete")
    public RestBean<String> multipleDeleteStudent(@RequestBody MultipleDeleteDto multipleDeleteDto){
        return clazzService.multipleDelete(multipleDeleteDto.getIdList());
    }
    @PostMapping("/update")
    public RestBean<String> updateClazz(@RequestBody Clazz clazz) {
        return clazzService.update(clazz);
    }

    @GetMapping("/getStudents/{clazzId}")
    public RestBean<List<Student>> getStudentsByClazz(@PathVariable("clazzId") String clazzId) {
        return studentService.getStudentsByClazz(clazzId);
    }

    @GetMapping("/getClazzs/{teacherId}")
    public RestBean<List<Clazz>> getClazzByTeacher(@PathVariable("teacherId") String teacherId) {
        return clazzService.getClazzByTeacher(teacherId);
    }
    @PostMapping("/query")
    public RestBean<List<Clazz>> queryClazz(@RequestBody QueryDto queryDto) {
        return clazzService.query(queryDto,false);
    }
}
