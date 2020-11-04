package com.example.springbootvvs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

    @Autowired
    private ProductsRepository productsRepository;

    @LocalServerPort
    private int port;

    private String serverUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void initServerURL() {
        productsRepository.deleteAll();
        this.serverUrl = "http://localhost:" + port;
    }
    private ResponseEntity<List<MakeupProducts>> executeProductRequest(String url, HttpMethod method) {
        return restTemplate.exchange(serverUrl + url, method, null, new ParameterizedTypeReference<List<MakeupProducts>>() {
        });
    }
    @Test
    public void whenGetAllProductsWithEmptyDB_thenReturn200AndCorrectResponse() {
        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/allProd", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(0, response.getBody().size());

    }
    @Test
    public void whenGetAllProducts_thenReturn200AndCorrectResponse() {
        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
       new MakeupProducts("Crion de buze",20.00,6),
        new MakeupProducts("Fond de ten",130.00,5),
        new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/allProd", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<MakeupProducts> responseProductsList = response.getBody();
        assertTrue((responseProductsList.containsAll(products) && products.containsAll(responseProductsList)));

    }
    @Test
    public void whenGetFindProductsWithPriceLessThanAll_thenReturn200And0Elements() {

        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
                new MakeupProducts("Crion de buze",20.00,6),
                new MakeupProducts("Fond de ten",130.00,5),
               new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/affordable/100", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<MakeupProducts> responseProductsList = response.getBody();
        assertEquals(0,responseProductsList.size());

    }
    @Test
    public void whenGetFindProductsWithPriceInDataBase_thenReturn200AndCorrectResponseBody() {
        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30,4),
       new MakeupProducts("Crion de buze",20,6),
                new MakeupProducts("Fond de ten",130,5),
                new MakeupProducts("Baza ten",70,3));
        productsRepository.saveAll(products);

        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/affordable/30", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<MakeupProducts> responseProductsList = response.getBody();

        List<MakeupProducts> expectedList = new ArrayList<>();
        expectedList.add(products.get(2));
        assertTrue((responseProductsList.containsAll(expectedList) && expectedList.containsAll(responseProductsList)));


    }
    @Test
    public void whenGetFindProductsWithPriceBigThanAll_thenReturn200AndCorrectResponseBody() {
        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30,4),
        new MakeupProducts("Crion de buze",20,6),
                new MakeupProducts("Fond de ten",130,5),
                new MakeupProducts("Baza ten",70,3));
        productsRepository.saveAll(products);

        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/affordable/700", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

        List<MakeupProducts> responseProductsList = response.getBody();
        assertTrue((responseProductsList.containsAll(products) && products.containsAll(responseProductsList)));


    }

    @Test
    public void whenGetFindProductsWithoutPathVariable_thenReturn404() {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeProductRequest("/affordable", HttpMethod.GET));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void whenDeleteProductWithIdInDB_thenReturn200AndCorrectResponseBody() {

        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
       new MakeupProducts("Crion de buze",20.00,6),
                new MakeupProducts("Fond de ten",130.00,5),
               new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        ResponseEntity<List<MakeupProducts>> response = executeProductRequest("/delete/" + products.get(0).getId(), HttpMethod.GET);
        List<MakeupProducts> expectedList = new ArrayList<>();
        expectedList.add(products.get(1));
        expectedList.add(products.get(2));

        List<MakeupProducts> responseProductsList = response.getBody();
        assertTrue((responseProductsList.containsAll(expectedList) && expectedList.containsAll(responseProductsList)));

    }

    @Test
    public void whenRemoveProductWithoutPathVariable_thenReturn404() throws Exception {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeProductRequest("/delete", HttpMethod.GET));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    public void whenRemoveProductWithIdNotInDB_then100() throws Exception {
        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
        new MakeupProducts("Crion de buze",20.00,6),
                new MakeupProducts("Fond de ten",130.00,5),
                new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        HttpServerErrorException response = assertThrows(HttpServerErrorException.class, () -> executeProductRequest("/delete/100", HttpMethod.GET));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
    @Test
    public void whenRemoveProductWithIdInDBGetMethod_then405(){
        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
        new MakeupProducts("Crion de buze",20.00,6),
                new MakeupProducts("Fond de ten",130.00,5),
                new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);

        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeProductRequest("/delete/"+products.get(0).getId(), HttpMethod.GET));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }
    @Test
    public void whenRemoveProductWithNotInDBGetMethod_then404(){

        List<MakeupProducts> products = Arrays.asList(new MakeupProducts("Fard de pleoape",30.00,4),
        new MakeupProducts("Crion de buze",20.00,6),
                new MakeupProducts("Fond de ten",130.00,5),
               new MakeupProducts("Baza ten",70.00,3));
        productsRepository.saveAll(products);
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeProductRequest("/delete/500", HttpMethod.GET));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }


}
