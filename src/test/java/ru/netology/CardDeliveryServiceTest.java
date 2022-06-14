package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldCompleteDeliveryForm() {
        String deliveryDate = generateDate(3);

        Configuration.headless = true;
        open("http://localhost:9999");

        $x("//*/span[@data-test-id=\"city\"]//input").setValue("Москва");
        $x("//*/span[@data-test-id=\"date\"]//input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*/span[@data-test-id=\"date\"]//input").setValue(deliveryDate);
        $x("//*/span[@data-test-id=\"name\"]//input").setValue("Петров Иван");
        $x("//*/span[@data-test-id=\"phone\"]//input").setValue("+79198885634");
        $x("//*/label[@data-test-id=\"agreement\"]").click();
        $(withText("Забронировать")).click();

        $x("//*/div[@class=\"notification__content\"]").should(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + deliveryDate));
    }
}
