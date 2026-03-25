package com.linkdatabase.tdinitiationspringboot.validator;

import com.linkdatabase.tdinitiationspringboot.exception.BadRequestException;
import com.linkdatabase.tdinitiationspringboot.model.Student;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentValidator {
    public void validate(List<Student> students) {
        for (Student s : students) {
            if (s.getReference() == null || s.getReference().isBlank()) {
                throw new BadRequestException("Reference cannot be null or blank");
            }
            if (s.getFirstName() == null || s.getFirstName().isBlank()) {
                throw new BadRequestException("FirstName cannot be null or blank");
            }
            if (s.getLastName() == null || s.getLastName().isBlank()) {
                throw new BadRequestException("LastName cannot be null or blank");
            }
        }
    }
}