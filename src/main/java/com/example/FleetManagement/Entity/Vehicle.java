package com.example.FleetManagement.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleNumber;
    private String model;
    private String type;     // Car, Bike, Truck, etc.
    private String status;   // Active, Inactive

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

