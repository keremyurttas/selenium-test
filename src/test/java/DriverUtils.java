import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverUtils {
    public static WebDriver getConfiguredDriver() {
        WebDriver driver = new ChromeDriver();
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get("https://www.trendyol.com/");

        WebElement cookiesButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
        cookiesButton.click();

        return driver;
    }
}
