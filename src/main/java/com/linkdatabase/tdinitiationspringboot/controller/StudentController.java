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
    public ResponseEntity<String> welcome(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Le paramètre 'name' est requis");
        }
        return ResponseEntity.ok("Welcome " + name);
    }

    @PostMapping("/students")
    public ResponseEntity<List<Student>> createStudent(@RequestBody List<Student> students) {
        try {
            listStudent.addAll(students);
            return ResponseEntity.status(HttpStatus.CREATED).body(listStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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