package ug.visituganda.visituganda.entity.company;

import jakarta.persistence.*;
import lombok.*;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

@Entity
@Table(name = "business_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double rating = 0.0;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessCategory category;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}