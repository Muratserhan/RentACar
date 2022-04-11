package com.etiya.rentACar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cities")
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name")
    private String name;

@OneToMany(mappedBy = "rentCity")
private List<Rental> rentRentals;

    @OneToMany(mappedBy = "returnCity")
    private List<Rental> returnRentals;


@OneToMany(mappedBy = "city")
    private List<Car> cars;

}
