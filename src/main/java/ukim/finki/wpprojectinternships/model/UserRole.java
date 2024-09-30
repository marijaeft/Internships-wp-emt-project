package ukim.finki.wpprojectinternships.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    STUDENT(false, true),
    // professors
    COMPANY(true, false, AppRole.COMPANY),
    ADMIN(false, false);


    @Getter
    public AppRole applicationRole = AppRole.STUDENT;

    private final Boolean company;

    private final Boolean student;

    UserRole(Boolean company, Boolean student,AppRole applicationRole) {
        this.company = company;
        this.student = student;
        this.applicationRole=applicationRole;
    }

    UserRole(Boolean company, Boolean student) {
        this.company = company;
        this.student = student;
    }
    public Boolean isCompany() {
        return company;
    }


    public Boolean isStudent() {
        return student;
    }

    public String roleName() {
        return "ROLE_" + this.name();
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

