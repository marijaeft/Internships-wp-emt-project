package ukim.finki.wpprojectinternships.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private String email;
    private String phone;
    @Column(length = 10_000)
    private String description;
    private String address;
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Company() {
    }

    public Company(String name, String address, String email, String phone, String description) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }

    public Company(String name, String email, String phone, String description, String address, byte[] image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.address = address;
        this.image = image;
    }
}
