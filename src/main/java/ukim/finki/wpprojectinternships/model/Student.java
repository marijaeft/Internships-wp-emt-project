package ukim.finki.wpprojectinternships.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    //private String password;
    private String index;
    //private byte[] CV;
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Student() {
    }

    public Student(String name, String surname, String email, String phone, String index, byte[] image) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.index = index;
        this.image = image;
    }
    public Long getId() {
        return id;
    }
}
