package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Student;
import com.sojoteki.library_management_system.request_dto.StudentRequestDto;
import com.sojoteki.library_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<?> saveStudent(@RequestBody StudentRequestDto studentRequestDto){
        try {
            String response = studentService.saveStudent(studentRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass()+":\nSave operation failed - "+e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllStudents(@RequestParam String sortBy, @RequestParam String sortOrder, @RequestParam int pageNo, @RequestParam int pageSize){
        try {
            Page<Student> studentPage = studentService.getAllStudents(sortBy, sortOrder, pageNo, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(studentPage);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass()+":\nFetch students list failed - "+e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(student);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass()+":\n"+"Get operation failed - "+e.getMessage());
        }
    }
}
