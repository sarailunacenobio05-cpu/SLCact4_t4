package com.luna.slcact4_t4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luna.slcact4_t4.dto.PageResponse;
import com.luna.slcact4_t4.dto.StudentRequest;
import com.luna.slcact4_t4.dto.StudentResponse;
import com.luna.slcact4_t4.entity.Student;
import com.luna.slcact4_t4.exception.ConflictException;
import com.luna.slcact4_t4.exception.ResourceNotFoundException;
import com.luna.slcact4_t4.repository.StudentRepository;

@Service
public class StudentService {

    private static final int MAX_PAGE_SIZE = 50;

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> list(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Page<Student> result = repository.findAll(PageRequest.of(safePage, safeSize, Sort.by("studentId")));
        return PageResponse.from(result, StudentResponse::from);
    }

    @Transactional(readOnly = true)
    public StudentResponse get(Long id) {
        return StudentResponse.from(find(id));
    }

    @Transactional
    public StudentResponse create(StudentRequest req) {
        String email = req.email().trim().toLowerCase();
        if (repository.existsByEmail(email)) {
            throw new ConflictException("Ya existe un estudiante con ese email");
        }
        Student s = new Student();
        apply(s, req, email);
        return StudentResponse.from(repository.save(s));
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest req) {
        Student s = find(id);
        String email = req.email().trim().toLowerCase();
        if (repository.existsByEmailAndStudentIdNot(email, id)) {
            throw new ConflictException("Ya existe otro estudiante con ese email");
        }
        apply(s, req, email);
        return StudentResponse.from(repository.save(s));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Student find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un estudiante con id " + id));
    }

    private void apply(Student s, StudentRequest req, String email) {
        s.setFirstName(req.firstName().trim());
        s.setLastName(req.lastName().trim());
        s.setEmail(email);
    }
}
