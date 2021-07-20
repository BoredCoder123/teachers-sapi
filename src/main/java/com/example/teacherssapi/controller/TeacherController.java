package com.example.teacherssapi.controller;

import com.example.teacherssapi.entity.Marks;
import com.example.teacherssapi.entity.Teacher;
import com.example.teacherssapi.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @GetMapping("/findTeacherByName/{name}")
    public List<Teacher> findTeacherByName(@PathVariable String name){
        return service.findTeacherByName(name);
    }

    @GetMapping("/findTeacherById/{id}")
    public Teacher findTeacherById(@PathVariable int id) throws Exception {
        return service.findTeacherById(id);
    }

    @GetMapping("/findTeacher")
    public List<Teacher> findTeachers(){
        return service.findTeachers();
    }

    @GetMapping("/viewAllMarks")
    public List<Marks> viewAllMarks(){
        return service.viewAllMarks();
    }

    @GetMapping("/viewMarksByTeacherId/{teacherId}")
    public List<Marks> viewMarksByTeacherId(@PathVariable int teacherId){
        return service.viewMarksByTeacherId(teacherId);
    }

    @PostMapping("/addMarks/{teacherId}")
    public String addMarks(@RequestBody Marks marks, @PathVariable Integer teacherId){
        return service.addMarks(marks, teacherId);
    }

    @PutMapping("/updateMarks/{teacherId}")
    public String updateMarks(@RequestBody Marks marks, @PathVariable Integer teacherId){
        return service.updateMarks(marks, teacherId);
    }
}
