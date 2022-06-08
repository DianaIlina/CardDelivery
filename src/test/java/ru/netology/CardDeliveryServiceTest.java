package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {
    @Test
    public void shouldCompleteDeliveryForm() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        String deliveryDate = format1.format(ldt);


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
