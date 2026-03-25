package com.linkdatabase.tdinitiationspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private String Reference;
    private String FirstName ;
    private String LastName ;
    private Integer Age ;
}
