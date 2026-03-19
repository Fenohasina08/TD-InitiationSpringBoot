package com.linkdatabase.tdinitiationspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.linkdatabase.tdinitiationspringboot.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class StudentController {
    private List<Student> listStudent = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return  "Welcome " + name;
    }

    @PostMapping("/students")
    public String  createStudent(@RequestBody List<Student> students) {
        listStudent.addAll(students);
        return listStudent.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));
    }
    @GetMapping("/students")
    public ResponseEntity<String> getStudents(@RequestHeader("Accept") String acceptHeader) {
        if ("text/plain".equals(acceptHeader)) {
            String names = listStudent.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.ok(names); // 200 OK avec la chaîne
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Format non supporté");
        }
    }

}