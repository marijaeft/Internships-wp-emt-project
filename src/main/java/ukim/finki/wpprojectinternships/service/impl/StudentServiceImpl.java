package ukim.finki.wpprojectinternships.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.model.User;
import ukim.finki.wpprojectinternships.model.UserRole;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidStudentIdException;
import ukim.finki.wpprojectinternships.repository.StudentRepository;
import ukim.finki.wpprojectinternships.repository.UserRepository;
import ukim.finki.wpprojectinternships.service.StudentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository,UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student registerStudent(String name, String surname, String email, String phone, String index, MultipartFile image) {
        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setEmail(email);
        student.setPhone(phone);
        student.setIndex(index);
        student.setRole(UserRole.STUDENT);
        if(image!=null) {
            try {
                byte[] imageData = image.getBytes();
                student.setImage(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = new User();
        user.setId(name);
        user.setEmail(email);
        user.setName(name);
        user.setRole(UserRole.STUDENT);
        userRepository.save(user);
        return studentRepository.save(student);
    }

//    @Override
//    public Student loginStudent(LoginDto loginDto) {
//        Student student = studentRepository.findByEmail(loginDto.getEmail());
//        if (student != null && passwordEncoder.matches(loginDto.getPassword(), student.getPassword())) {
//            return student;
//        }
//        return null;
//    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long studentId,String name, String surname, String email, String phone, String index, MultipartFile image) {
        // String encodedPassword = passwordEncoder.encode(password);
        Student student = this.findStudentById(studentId);
        student.setName(name);
        student.setSurname(surname);
        student.setEmail(email);
        student.setPhone(phone);
        student.setIndex(index);
        try {
            byte[] imageData = image.getBytes();
            student.setImage(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentRepository.save(student);
    }
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) studentRepository.findByEmail(s).orElseThrow(InvalidStudentIdException::new);
    }

    public Long authenticateUser(String email, String password){
        // Наоѓање на студентот по email
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student != null) {
            // Ако лозинката е точна, врати го `id` на студентот
            return student.get().getId();
        }
        // Ако не е најден студент или лозинката не е точна, врати null
        return null;
    }


}
