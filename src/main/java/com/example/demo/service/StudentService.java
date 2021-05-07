package com.example.demo.service;

import com.example.demo.DAO.StudentDAO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.model.Student;
import com.example.demo.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentDAO studentDAO;

    public void addStudent(StudentDTO studentDTO) {
        try {
            Student student = new Student(
                    studentDTO.getName(),
                    studentDTO.getSurname(),
                    studentDTO.getEmail());
            studentDAO.save(student);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public StudentDTO getStudentByID(Long id) {
        StudentDTO studentDTO = new StudentDTO();
        Optional<Student> student = studentDAO.findById(id);
        if (student.isPresent()) {
            studentDTO.setId(id);
            studentDTO.setName(student.get().getName());
            studentDTO.setSurname(student.get().getSurname());
            studentDTO.setEmail(student.get().getEmail());
            return studentDTO;

        } else return null;
    }

    public void deleteStudentById(Long id) {
        if (!studentDAO.findById(id).isPresent())
            throw new CustomException("No such student to delete");
        else
            studentDAO.delete(studentDAO.findById(id).get());
    }

    public List<StudentDTO> getAllStudents() {
        return studentDAO
                .findAll()
                .stream()
                .map(student -> {
                    return new StudentDTO(student.getId(), student.getName(), student.getSurname(), student.getEmail());
                })
                .collect(Collectors.toList());
    }

    public void updateStudent(StudentDTO studentDTO) {
        Optional<Student> student = studentDAO.findById(studentDTO.getId());
        Student student1 = student.get();

        student1.setName(studentDTO.getName());
        student1.setSurname(studentDTO.getSurname());
        student1.setEmail(studentDTO.getEmail());
        studentDAO.save(student1);
    }

}
