//package ukim.finki.wpprojectinternships.service.impl;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import ukim.finki.wpprojectinternships.model.Company;
//import ukim.finki.wpprojectinternships.model.DTO.LoginDto;
//import ukim.finki.wpprojectinternships.model.Student;
//import ukim.finki.wpprojectinternships.model.UserRole;
//import ukim.finki.wpprojectinternships.model.exceptions.InvalidStudentIdException;
//import ukim.finki.wpprojectinternships.repository.CompanyRepository;
//import ukim.finki.wpprojectinternships.repository.StudentRepository;
//import ukim.finki.wpprojectinternships.service.AuthenticationService;
//
//@Service
//public class AuthenticationServiceImpl implements AuthenticationService {
//    private final StudentRepository studentRepository;
//    private final CompanyRepository companyRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthenticationServiceImpl(StudentRepository studentRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
//        this.studentRepository = studentRepository;
//        this.companyRepository = companyRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Student loginStudent(LoginDto loginDto) {
//        Student student = studentRepository.findByEmail(loginDto.getEmail()).orElseThrow(InvalidStudentIdException::new);
//        if (student != null && passwordEncoder.matches(loginDto.getPassword(), student.getPassword())) {
//            student.setRole(UserRole.STUDENT);
//            return student;
//        }
//        throw new RuntimeException("Invalid email or password");
//    }
//
//    @Override
//    public Company loginCompany(LoginDto loginDto) {
//        Company company = companyRepository.findCompanyByEmail(loginDto.getEmail());
//        if (company != null && passwordEncoder.matches(loginDto.getPassword(), company.getPassword())) {
//            company.setRole(UserRole.COMPANY);
//            return company;
//        }
//        throw new RuntimeException("Invalid email or password");
//    }
//}
