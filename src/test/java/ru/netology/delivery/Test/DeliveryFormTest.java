package ru.netology.delivery.Test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.Data.DataGenerator;
import ru.netology.delivery.Data.User;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryFormTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        User user = DataGenerator.Registration.generateUser("ru",3);

        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(user.getDate());
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Забронировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на "
                + user.getDate()), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
