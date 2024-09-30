package ukim.finki.wpprojectinternships.service;

import ukim.finki.wpprojectinternships.model.Company;
import ukim.finki.wpprojectinternships.model.DTO.LoginDto;
import ukim.finki.wpprojectinternships.model.Student;

public interface AuthenticationService {
    Student loginStudent(LoginDto loginDto);
    Company loginCompany(LoginDto loginDto);
}
