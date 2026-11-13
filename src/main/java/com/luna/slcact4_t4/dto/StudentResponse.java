package com.luna.slcact4_t4.dto;

import com.luna.slcact4_t4.entity.Student;

public record StudentResponse(Long id, String firstName, String lastName, String email) {

    public static StudentResponse from(Student s) {
        return new StudentResponse(s.getStudentId(), s.getFirstName(), s.getLastName(), s.getEmail());
    }
}
