package ru.netology;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp2() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void close() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldCompleteDeliveryForm() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        String deliveryDate = format1.format(ldt);


        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        $x("//*/span[@data-test-id=\"city\"]//input").setValue("Москва");
        $x("//*/span[@data-test-id=\"date\"]//input").setValue(deliveryDate);
        $x("//*/span[@data-test-id=\"name\"]//input").setValue("Петров Иван");
        $x("//*/span[@data-test-id=\"phone\"]//input").setValue("+79198885634");
        $x("//*/label[@data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();

        $(withText("Встреча успешно забронирована ")).should(visible, Duration.ofSeconds(15));
    }


}
