package ukim.finki.wpprojectinternships.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Student;

import java.util.List;

public interface StudentService extends UserDetailsService {
    //registracija createStudent
    //logiranje so email i pass
    //create cv na kraj
    //find by id
    //find all
    //update
    //edit upload cv
    Student registerStudent(String name, String surname, String email, String phone, String index, MultipartFile image);
    //Student loginStudent(LoginDto loginDto);
    Student findStudentById(Long id);
    List<Student> findAllStudents();
    Student updateStudent(Long studentId,String name, String surname, String email, String phone, String index, MultipartFile image);
    Long authenticateUser(String email, String password);
}
