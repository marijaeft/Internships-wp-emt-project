package ukim.finki.wpprojectinternships.config;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ukim.finki.wpprojectinternships.model.Company;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.model.User;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidStudentIdException;
import ukim.finki.wpprojectinternships.repository.CompanyRepository;
import ukim.finki.wpprojectinternships.repository.StudentRepository;
import ukim.finki.wpprojectinternships.repository.UserRepository;

import java.util.Optional;

@Service
public class FacultyUserDetailsService implements UserDetailsService {

    @Value("${system.authentication.password}")
    String systemAuthenticationPassword;

    final UserRepository userRepository;

    final CompanyRepository companyRepository;
    final StudentRepository studentRepository;

    final PasswordEncoder passwordEncoder;

    public FacultyUserDetailsService(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder,StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.studentRepository=studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
               .orElseThrow(() -> new UsernameNotFoundException("User not found with username/email: " + username));

        if (user.getRole().isStudent()) {
            Optional<Student> student = studentRepository.findByName(username);
           // ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            //HttpSession session = attr.getRequest().getSession(true);
          //  session.setAttribute("currentStudent", user.getName());
            return new FacultyUserDetails(user, student.orElse(null), passwordEncoder.encode(systemAuthenticationPassword));
        } else if (user.getRole().isCompany()) {
            Company company = companyRepository.findCompanyByEmail(user.getEmail());
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("companyId", company.getId());
            return new FacultyUserDetails(user, company, passwordEncoder.encode(systemAuthenticationPassword));
        }

        throw new UsernameNotFoundException("User role not recognized for username/email: " + username);
    }
}
