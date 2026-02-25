package ug.visituganda.visituganda.entity;


import jakarta.persistence.*;
import jakarta.persistence.Id; // ✅ Use this

import java.time.LocalDate;


@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double totalAmount;

    @ManyToOne
    private User customer;

    @ManyToOne
    private BusinessPostRemove post;
}