package ukim.finki.wpprojectinternships.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ukim.finki.wpprojectinternships.model.Company;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.model.User;

import java.util.Collection;
import java.util.List;

public class FacultyUserDetails implements UserDetails {

    private User user;

    private Student student;

    private Company company;

    private String password;

    public FacultyUserDetails(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public FacultyUserDetails(User user, Company company, String password) {
        this.user = user;
        this.company = company;
        this.password = password;
    }

    public FacultyUserDetails(User user, Student student, String password) {
        this.user = user;
        this.student = student;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Company getCompany() {
        return this.company;
    }

    public Student getStudent() {
        return this.student;
    }
}
