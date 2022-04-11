package com.etiya.rentACar.dataAccess.abstracts;

import com.etiya.rentACar.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao extends JpaRepository<Brand,Integer> {

    List<Brand> getByNameIgnoreCase(String name);
    //findByFirstNameIgnoreCase ?

}
