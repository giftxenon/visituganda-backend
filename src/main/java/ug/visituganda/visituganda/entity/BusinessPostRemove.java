package ug.visituganda.visituganda.entity;

import jakarta.persistence.*;
import lombok.*;
import ug.visituganda.visituganda.entity.company.CompanyPost;

@Entity
@Table(name = "business_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessPostRemove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // e.g. "Toyota Land Cruiser Prado"

    @Column(length = 1000)
    private String description;

    private Double pricePerDay;

    private String imageUrl;

    private String location;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private CompanyPost business;

    @Column(nullable = false)
    private boolean active = true;
}