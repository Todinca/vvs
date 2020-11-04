package com.example.springbootvvs;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<MakeupProducts,Long> {
    List<MakeupProducts> findByPriceProductLessThan(double price);
}
