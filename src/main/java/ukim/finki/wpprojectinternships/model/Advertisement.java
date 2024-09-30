package ukim.finki.wpprojectinternships.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Advertisement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 10_000)
    private String description;

    @UpdateTimestamp(source = SourceType.DB)
    private Timestamp datePosted;

    private Timestamp dateExpired;

    @Enumerated(EnumType.STRING)
    private AdvertisementType type;

    private int numberOfApplicants;

    @ManyToOne
    private Company company;

    private boolean active;

    public Advertisement() {
    }

    public Advertisement(Timestamp dateExpired, String description, AdvertisementType type, int numberOfApplicants, Company company, boolean active) {
        this.dateExpired = dateExpired;
        this.description = description;
        this.type = type;
        this.numberOfApplicants = numberOfApplicants;
        this.company = company;
        this.active = active;
    }
}
