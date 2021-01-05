package com.example.springbootvvs;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestS {
    @Test
    public void testGoogleSearch() throws InterruptedException {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\40759\\IdeaProjects\\springboot-vvs\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver,30,1000);

        driver.get("http://localhost:8080/");
        Thread.sleep(5000);  // Let the user actually see something!

        By newBooking_all = By.id("all_button");
        wait.until(elementToBeClickable(newBooking_all));
        driver.findElement(newBooking_all).click();

        By newBooking_delete = By.id("delete_button");
        wait.until(elementToBeClickable(newBooking_delete));
        driver.findElement(newBooking_delete).click();

        Thread.sleep(3000);

        By newBooking_aff = By.id("aff_button");
        wait.until(elementToBeClickable(newBooking_aff ));
        driver.findElement(newBooking_aff).click();

        Thread.sleep(3000);



        wait.until(elementToBeClickable(newBooking_aff ));
        driver.findElement(newBooking_aff).click();

        Thread.sleep(3000);  // Let the user actually see something!
        driver.quit();
    }

    }
