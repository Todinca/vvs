package com.example.springbootvvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseSeeder implements CommandLineRunner {
    private ProductsRepository productsRepository;
    @Autowired
    public DataBaseSeeder(ProductsRepository productsRepository){
        this.productsRepository=productsRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        List<MakeupProducts> products= new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);
    }
}
