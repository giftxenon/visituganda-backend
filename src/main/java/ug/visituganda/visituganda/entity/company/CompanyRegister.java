package ug.visituganda.visituganda.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.CreateCar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registered_businesses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phone;

    private String email;

    private String operatingHours;

    @Column(length = 1000)
    private String description;

    private Double rating = 0.0;

    @JsonIgnore
    @Column(name = "logo", columnDefinition = "bytea")
    private byte[] logo;

    @Column(name = "logo_file_name")
    private String logoFileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessCategory category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // One-to-many relation with cars
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreateCar> cars = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addCar(CreateCar car) {
        cars.add(car);
        car.setCompany(this);
    }
}