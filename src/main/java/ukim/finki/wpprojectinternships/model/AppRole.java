package ukim.finki.wpprojectinternships.model;

public enum AppRole {
    ADMIN,COMPANY,STUDENT;

    public String roleName() {
        return "ROLE_" + this.name();
    }
}
