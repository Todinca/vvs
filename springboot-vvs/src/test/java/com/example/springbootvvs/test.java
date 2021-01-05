package com.example.springbootvvs;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class test {
    @Test
    public void testS() throws InterruptedException {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\40759\\IdeaProjects\\springboot-vvs\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver,30,1000);

        driver.get("http://localhost:8080/");
        Thread.sleep(5000);  // Let the user actually see something!

        By newProducts_all = By.id("all_button");
        wait.until(elementToBeClickable(newProducts_all));
        driver.findElement(newProducts_all).click();

        By newProducts_delete = By.id("delete_button");
        wait.until(elementToBeClickable(newProducts_delete));
        driver.findElement(newProducts_delete).click();

        Thread.sleep(3000);

        By newProducts_less = By.id("less_button");
        wait.until(elementToBeClickable(newProducts_less ));
        driver.findElement(newProducts_less).click();

        Thread.sleep(3000);

       String title=driver.getTitle();
       assertTrue(title.contains("Makeup Products"));
        driver.quit();
    }

}
