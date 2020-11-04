package com.example.springbootvvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
/*@RequestMapping(value="/products")*/
public class ProductsController {
    private List<MakeupProducts> products;
    private ProductsRepository productsRepository;
   @Autowired
   public ProductsController(ProductsRepository productsRepository){
       this.productsRepository=productsRepository;
   }


    @RequestMapping(value="/allProd",method = RequestMethod.GET)
    public List<MakeupProducts> getAll()
    {
        return productsRepository.findAll();
    }


    @RequestMapping(value= "/affordable/{price}",method = RequestMethod.GET)
    public List<MakeupProducts> getAffordable(@PathVariable double price) {
       return productsRepository.findByPriceProductLessThan(price);
    }
    /*@RequestMapping(value="/create",method = RequestMethod.POST)
    public List<MakeupProducts> create(@RequestBody MakeupProducts makeupProducts){
        productsRepository.save(makeupProducts);
   return productsRepository.findAll();}*/


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public List<MakeupProducts> deleteProducts(@PathVariable Long id){
        productsRepository.deleteById(id);
        return productsRepository.findAll();}



}
