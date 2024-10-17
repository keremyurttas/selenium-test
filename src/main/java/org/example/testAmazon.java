package org.example;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;


public class testAmazon {
    private WebElement emailInput, passwordInput, sendButton;
    private WebDriver driver;

    @BeforeEach
    public void init() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // Set a global implicit wait once
    }


    @Test
    public void AmazonSignUpTest() {

            driver.get("https://www.trendyol.com/");
            String title = driver.getTitle();
            assertEquals(title, "En Trend Ürünler Türkiye'nin Online Alışveriş Sitesi Trendyol'da");

            WebElement cookiesButton = driver.findElement(By.id("onetrust-accept-btn-handler"));

            cookiesButton.click();
            
            WebElement signinButton = driver.findElement(By.className("account-user"));
            signinButton.click();
//            title = driver.getTitle();
//            System.out.println(title);
//            assertEquals(title, "Amazon Sign-In");
            emailInput = driver.findElement(By.id("login-email"));
            passwordInput = driver.findElement(By.id("login-password-input"));
            sendButton = driver.findElement(By.className("submit"));

            ArrayList<Object[]> loginData = new ArrayList<>();
            String password = "123456";
            loginData.add(new Object[]{"dummytext", null, "Lütfen geçerli bir e-posta adresi giriniz.", false});
            loginData.add(new Object[]{"dummytext@gmail.com", null, "Lütfen şifrenizi giriniz.", false});
            loginData.add(new Object[]{"dummytext@gmail.com", password, "E-posta adresiniz ve/veya şifreniz hatalı.", false});

            for (int i = 0; i < loginData.size(); i++) {
                Object[] data = loginData.get(i);
                testByLoginData((String) data[0], (String) data[1], (String) data[2]);
            }




    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    public void testByLoginData(String email, String password, String expectedError) {
        emailInput.clear();
        emailInput.sendKeys(email);

        // If the password field is not null, enter the password
        if (password != null) {
            passwordInput.clear();
            passwordInput.sendKeys(password);
        }

        // Click the submit button
        sendButton.click();

        // Locate the error message element and assert its content
        WebElement alertContainer = driver.findElement(By.id("error-box-wrapper"));
        assertEquals(alertContainer.getText(), expectedError);
    }

}
