package com.example.application.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cin;

    private String firstName;

    private String lastName;

    private String gender;

    private String birthDate;

    private String email;

    private String tele;


    private String dateEmbauche;

    private String dateDebutCt;

    private String dateFinCt;

    private String departement;

    private String adresse;


    private String situation_familiale;

    private int nb_enfants;

    private int nbr_conje;

    private double salary;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    public String getFullName() {
        return firstName + " " + lastName;
    }
}
