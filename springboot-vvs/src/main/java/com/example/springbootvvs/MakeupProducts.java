package com.example.springbootvvs;

import net.bytebuddy.implementation.bytecode.constant.NullConstant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class MakeupProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private double priceProduct;
    private int nrOfProd;
    public MakeupProducts(){

    }
    public MakeupProducts(String productName,double priceProduct,int nrOfProd){


        this.productName = productName;
        this.priceProduct = priceProduct;
        this.nrOfProd = nrOfProd;
    }
    public MakeupProducts(Long id,String productName,double priceProduct,int nrOfProd){

        this.id=id;
        this.productName = productName;
        this.priceProduct = priceProduct;
        this.nrOfProd = nrOfProd;
    }

    public Long getId() {
        return  id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getNrOfProd() {
        return nrOfProd;
    }

    public void setNrOfProd(int nrOfProd) {
        this.nrOfProd = nrOfProd;
    }

    public double totalPrice(){

        return priceProduct*nrOfProd;
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MakeupProducts product = (MakeupProducts) o;

       return   id!=null &&  Double.compare( product.priceProduct,priceProduct) == 0
               && Objects.equals(id, product.id) && Objects.equals(productName, product.productName)
               && Objects.equals(nrOfProd, product.nrOfProd);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, productName, nrOfProd, priceProduct);
    }

}
