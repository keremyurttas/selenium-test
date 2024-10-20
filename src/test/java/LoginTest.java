import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.cdimascio.dotenv.Dotenv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LoginTest {
    private WebElement emailInput, passwordInput, sendButton;
    private WebDriver driver;
    private Dotenv dotenv;


    @BeforeEach
    public void init() {
        driver = DriverUtils.getConfiguredDriver();
        dotenv = Dotenv.load();


        WebElement signinButton = driver.findElement(By.className("account-user"));
        signinButton.click();

        emailInput = driver.findElement(By.id("login-email"));
        passwordInput = driver.findElement(By.id("login-password-input"));
        sendButton = driver.findElement(By.className("submit"));
    }

    //    String loginEmail = dotenv.get("LOGIN_EMAIL");
//    String loginPassword = dotenv.get("LOGIN_PASSWORD");
    @ParameterizedTest
    @CsvSource({
            "'dummytext', , 'Lütfen geçerli bir e-posta adresi giriniz.'",
            "'dummytext@gmail.com', , 'Lütfen şifrenizi giriniz.'",
            "'dummytext@gmail.com', '123456', 'E-posta adresiniz ve/veya şifreniz hatalı.'"

    })
    void testByLoginData(String email, String password, String expectedError) {
        // Convert empty strings to null
        if ("".equals(password)) {
            password = null;
        }
        performLogin(email, password);
        assertEquals(expectedError, getErrorMessage());
    }

    @Test
    void testSuccessfulLogin() {
        String loginEmail = dotenv.get("LOGIN_EMAIL");
        String loginPassword = dotenv.get("LOGIN_PASSWORD");
        performLogin(loginEmail, loginPassword);
        WebElement loggedinDropdownMenu = driver.findElement(By.className("new-user-loggedin-container"));
        assertNotNull(loggedinDropdownMenu);
    }

    void performLogin(String email, String password) {
        emailInput.clear();
        emailInput.sendKeys(email);

        if (password != null) {
            passwordInput.clear();
            passwordInput.sendKeys(password);
        }

        sendButton.click();
    }

    public String getErrorMessage() {
        WebElement alertContainer = driver.findElement(By.id("error-box-wrapper"));
        if (!alertContainer.getText().isEmpty()) {
            return alertContainer.getText();
        } else {
            return null;
        }
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
