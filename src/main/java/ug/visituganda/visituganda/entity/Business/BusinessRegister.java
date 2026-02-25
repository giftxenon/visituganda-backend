package ug.visituganda.visituganda.entity.Business;


import jakarta.persistence.*;
import lombok.*;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

@Entity
@Table(name = "registered_businesses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRegister {

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

    // Store image as bytea in PostgreSQL
    @Column(name = "logo", columnDefinition = "bytea")
    private byte[] logo;

    @Column(name = "logo_file_name")
    private String logoFileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessCategory category;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}