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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body("Le paramètre 'name' est requis");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "text/plain")
                .body("Welcome " + name);
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
    public ResponseEntity<?> getStudents(@RequestHeader(name = "Accept", required = false) String acceptHeader) {
        try {
            if (acceptHeader == null || acceptHeader.isBlank() || "*/*".equals(acceptHeader)) {
                 return ResponseEntity.badRequest().body("Accept header is required");
            }
            if ("text/plain".equals(acceptHeader)) {
                String names = listStudent.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .collect(Collectors.joining(", "));
                return ResponseEntity.ok(names);
            } else if ("application/json".equals(acceptHeader)) {
                return ResponseEntity.ok(listStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Format non supporté");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}