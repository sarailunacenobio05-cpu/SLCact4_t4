package com.luna.slcact4_t4.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luna.slcact4_t4.dto.PageResponse;
import com.luna.slcact4_t4.dto.StudentRequest;
import com.luna.slcact4_t4.dto.StudentResponse;
import com.luna.slcact4_t4.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    
    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.list(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.get(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse created = studentService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/students/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable("id") Long id,
            @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
