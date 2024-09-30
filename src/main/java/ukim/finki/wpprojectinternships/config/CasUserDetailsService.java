//package ukim.finki.wpprojectinternships.config;
//
//
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import ukim.finki.wpprojectinternships.repository.CompanyRepository;
//import ukim.finki.wpprojectinternships.repository.StudentRepository;
//import ukim.finki.wpprojectinternships.repository.UserRepository;
//
//@Profile("cas")
//@Service
//public class CasUserDetailsService extends FacultyUserDetailsService implements AuthenticationUserDetailsService {
//
//
//    public CasUserDetailsService(UserRepository userRepository,
//                                 CompanyRepository companyRepository,
//                                 PasswordEncoder passwordEncoder,
//                                 StudentRepository studentRepository) {
//        super(userRepository,companyRepository,passwordEncoder,studentRepository);
//    }
//
//    @Override
//    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
//        String username = (String) token.getPrincipal();
//        return super.loadUserByUsername(username);
//    }
//}
