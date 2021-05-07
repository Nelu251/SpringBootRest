package com.example.demo.restController;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.service.StudentService;
import com.example.demo.util.CustomException;
import com.example.demo.util.ErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Data
@RequiredArgsConstructor
@RestController
public class ControllerStudent {

    @Autowired
    private StudentService studentService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(Exception exception) {
        return new ErrorMessage("400", exception.getMessage());
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Object> addStudent(@RequestBody StudentDTO studentDTO) {
        if (studentDTO.getName() != null && studentDTO.getSurname() != null && studentDTO.getEmail() != null) {
            studentService.addStudent(studentDTO);
            return new ResponseEntity<>("Student added", HttpStatus.ACCEPTED);
        } else throw new CustomException("All fields required");
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<Object> allStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable("id") Long id) {
        if (studentService.getStudentByID(id) == null) {
            throw new CustomException("No scuh student");
        }
        return new ResponseEntity<>(studentService.getStudentByID(id), HttpStatus.OK);

    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>("Student Deleted", HttpStatus.OK);
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {
        studentDTO.setId(id);
        if (studentService.getStudentByID(id) != null) {
            if (studentDTO.getName() == null || studentDTO.getSurname() == null || studentDTO.getEmail() == null) {
                throw new CustomException("All fields required");
            }
            studentService.updateStudent(studentDTO);
            return new ResponseEntity<>("Student Updated", HttpStatus.OK);
        } else throw new NoSuchElementException("Student with this id doesn't exist");
    }

}
