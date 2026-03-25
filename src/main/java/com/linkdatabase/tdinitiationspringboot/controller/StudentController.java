package com.linkdatabase.tdinitiationspringboot.controller;

import com.linkdatabase.tdinitiationspringboot.exception.BadRequestException;
import com.linkdatabase.tdinitiationspringboot.model.Student;
import com.linkdatabase.tdinitiationspringboot.service.StudentService;
import com.linkdatabase.tdinitiationspringboot.validator.StudentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentValidator studentValidator;

    public StudentController(StudentService studentService, StudentValidator studentValidator) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
    }

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
        studentValidator.validate(students);
        studentService.addAll(students);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(studentService.getAll());
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(name = "Accept", required = false) String acceptHeader) {
        if (acceptHeader == null || acceptHeader.isBlank() || "*/*".equals(acceptHeader)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body("Accept header is required");
        }

        List<Student> allStudents = studentService.getAll();

        if ("text/plain".equals(acceptHeader)) {
            String names = allStudents.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "text/plain")
                    .body(names);
        } else if ("application/json".equals(acceptHeader)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(allStudents);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .header("Content-Type", "text/plain")
                    .body("Format non supporté");
        }
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "text/plain")
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }
}