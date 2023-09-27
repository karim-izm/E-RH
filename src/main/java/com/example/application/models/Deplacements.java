package com.example.application.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deplacements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String destination;

    LocalDate dateDebut;

    LocalDate dateFin;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
