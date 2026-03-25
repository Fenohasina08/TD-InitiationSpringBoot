package com.linkdatabase.tdinitiationspringboot.service;

import com.linkdatabase.tdinitiationspringboot.model.Student;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addAll(List<Student> newStudents) {
        students.addAll(newStudents);
    }

    public List<Student> getAll() {
        return students;
    }
}