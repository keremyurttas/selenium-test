import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class SearchTest {
    private WebElement searchInput, searchIcon;
    private WebDriver driver;
    private String searchKey = "ayakkabı";

    @BeforeEach
    public void init() {
        driver = DriverUtils.getConfiguredDriver();
        searchInput = driver.findElement(By.cssSelector("[data-testid='suggestion']"));
        searchIcon = driver.findElement(By.cssSelector("[data-testid='search-icon']"));
    }

    @Test
    public void testBySearchKey() {
        searchInput.sendKeys(searchKey);
        searchIcon.click();
        List<WebElement> searchResults = driver.findElements(By.className("prdct-desc-cntnr-ttl-w"));
        searchResults.forEach((res) ->
                assertTrue(res.getText().toLowerCase().contains(searchKey), "The search result does not contain " + searchKey  +" "+ res.getText())
        );
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}