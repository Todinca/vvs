package com.example.springbootvvs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class DBTest {

    @Autowired
    private ProductsRepository productsRepository;

    @BeforeEach
    public void initDataBase(){
        productsRepository.deleteAll();

    }
    @Test
    public void testFindAllProductsWhenDBIsEmpty(){
        List<MakeupProducts> products = productsRepository.findAll();
        assertEquals(0,products.size());

    }
    @Test
    public void testFindAllProductsWhenDBIsNotEmpty(){
        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        List<MakeupProducts> makeupProducts = productsRepository.findAll();
        assertTrue((products.containsAll(makeupProducts) && makeupProducts.containsAll(products)));

    }
    @Test
    public void testSaveProductWhenDBIsEmpty(){
        MakeupProducts product = new MakeupProducts("Fard de pleoape",30.00,4);
        MakeupProducts addedProduct = null;
        addedProduct = productsRepository.save(product);
        List<MakeupProducts> products = productsRepository.findAll();
        assertNotNull(addedProduct);
        assertEquals(1,products.size());

    }
    @Test
    public void testSaveProductWhenDBIsNotEmpty() {
        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        MakeupProducts product = new MakeupProducts("Iluminator", 150.00, 3);
        products.add(product);
        productsRepository.save(product);
        List<MakeupProducts> makeupProducts = productsRepository.findAll();
        assertTrue((products.containsAll(makeupProducts) && makeupProducts.containsAll(products)));

    }
    @Test
    public void testFindByPriceLessThanWhenPriceIsLessThanAll(){
        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        double price = 15.00;
        List<MakeupProducts> makeupProducts = null;
        makeupProducts = productsRepository.findByPriceProductLessThan(price);

        assertEquals(0,makeupProducts.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsBigThanAll(){

        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        double price = 200.00;
        List<MakeupProducts> makeupProducts = null;
        makeupProducts = productsRepository.findByPriceProductLessThan(price);

        assertTrue((products.containsAll(makeupProducts) && makeupProducts.containsAll(products)));

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsInDataBase(){
        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);


        double price = 70;
        List<MakeupProducts> makeupProducts = null;
        makeupProducts = productsRepository.findByPriceProductLessThan(price);

        products.remove(0);
        products.remove(0);
        assertTrue((products.containsAll(makeupProducts) && makeupProducts.containsAll(products)));

    }
    @Test
    public void testDeleteProductByIdWhenIdExist(){

        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);



        Long id = products.get(0).getId();
        productsRepository.deleteById(id);
        products.remove(0);
        List<MakeupProducts> makeupProducts = productsRepository.findAll();


        assertTrue((products.containsAll(makeupProducts) && makeupProducts.containsAll(products)));

    }
    @Test
    public void testDeleteProductByIdWhenIdNotExist(){

        List<MakeupProducts> products = new ArrayList<>();

        products.add(new MakeupProducts("Fard de pleoape",30.00,4));
        products.add(new MakeupProducts("Crion de buze",20.00,6));
        products.add(new MakeupProducts("Fond de ten",130.00,5));
        products.add(new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        Long id = new Long(20);
        assertThrows(EmptyResultDataAccessException.class, () -> {productsRepository.deleteById(id);});

    }

}
