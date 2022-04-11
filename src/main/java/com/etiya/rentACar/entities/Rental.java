package com.etiya.rentACar.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rentDate")
    private LocalDate rentDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name="endKilometre")
    private  double endKilometre;

    @ManyToOne
    @JoinColumn(name = "rent_city_id",referencedColumnName = "id")
    private City rentCity;

    @ManyToOne
    @JoinColumn(name = "return_city_id",referencedColumnName = "id")
    private  City returnCity;

    @Column(name="rentDay")
    private int rentDay;

    @Column(name="dailyPrice")
    private double dailyPrice;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name="additionalService_id")
    private AdditionalService additionalService;

    @OneToMany(mappedBy = "rental")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "rental")
    private List<OrderedAdditionalService> orderedAdditionalServices;

}
