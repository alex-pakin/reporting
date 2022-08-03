package ru.netology.delivery.Test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.Data.DataGenerator;
import ru.netology.delivery.Data.User;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryFormTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSetInitialDateAndPlanNewDate() {
        User user = DataGenerator.Registration.generateUser("ru");
        String initialDate = DataGenerator.generateDate(3);
        String newDate = DataGenerator.generateDate(7);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(initialDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча "
                +"успешно запланирована на " + initialDate), Duration.ofSeconds(10)).shouldBe(visible);
        refresh();
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(newDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=replan-notification] .notification__content button").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча "
                +"успешно запланирована на " + newDate), Duration.ofSeconds(10)).shouldBe(visible);
    }

    @Test
    void shouldSetNewDateWhenInitialDateIsLater() {
        User user = DataGenerator.Registration.generateUser("ru");
        String initialDate = DataGenerator.generateDate(13);
        String newDate = DataGenerator.generateDate(6);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(initialDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        refresh();
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(newDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=replan-notification] .notification__content button").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча "
                +"успешно запланирована на " + newDate), Duration.ofSeconds(10)).shouldBe(visible);
    }

    @Test
    void shouldNotSetNewDate() {
        User user = DataGenerator.Registration.generateUser("ru");
        String initialDate = DataGenerator.generateDate(7);
        String newDate = DataGenerator.generateDate(1);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(initialDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        refresh();
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(newDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='date']//*[@class='input__sub']").shouldHave(exactText("Заказ на "
                + "выбранную дату невозможен"));
    }

    @Test
    void shouldSendForm() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(10)).shouldBe(visible);
    }

    @Test
    void shouldSendFormCityWithDash() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String cityWithDash = "Санкт-Петербург";
        $x("//*[@data-test-id='city']//input").val(cityWithDash);
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(10)).shouldBe(visible);
    }

    @Test
    void shouldSendFormCityWithE() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String cityWithE = "Орёл";
        $x("//*[@data-test-id='city']//input").val(cityWithE);
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldSendFormCityWithSpace() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String cityWithSpace = "Нижний Новгород";
        $x("//*[@data-test-id='city']//input").val(cityWithSpace);
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldSendFormNameWithDash() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String nameWithDash = "Ана-Мария Огбезян";
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(nameWithDash);
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldSendFormSurnameWithDash() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String surnameWithDash = "Андрей Понкратов-Белый";
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(surnameWithDash);
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldNotSendFormCityWrongSpelling() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String cityWrongSpelling = "Санкт Петербург";
        $x("//*[@data-test-id='city']//input").val(cityWrongSpelling);
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='city']//*[@class='input__sub']").shouldHave(exactText("Доставка в "
                + "выбранный город недоступна"));
    }

    @Test
    void shouldNotSendFormCityNotInList() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String cityNotInList = "Белозерск";
        $x("//*[@data-test-id='city']//input").val(cityNotInList);
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='city']//*[@class='input__sub']").shouldHave(exactText("Доставка в "
                + "выбранный город недоступна"));
    }
    @Test
    void shouldNotSendFormDateLessThanThreeDays() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(1);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='date']//*[@class='input__sub']").shouldHave(exactText("Заказ на "
                + "выбранную дату невозможен"));
    }

    @Test
    void shouldNotSendFormNameInLatin() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        String name = "James Bond";
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(name);
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='name']//*[@class='input__sub']").shouldHave(exactText("Имя и Фамилия "
                + "указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendFormNoAgreement() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='agreement']//*[@class='checkbox__text']").shouldHave(exactText("Я "
                + "соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldNotSendFormIfEmptyCity() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        $x("//*[@data-test-id='city']//input").val("");
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='city']//*[@class='input__sub']").shouldHave(exactText("Поле обязательно "
                + "для заполнения"));
    }

    @Test
    void shouldNotSendFormIfEmptyName() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val("");
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='name']//*[@class='input__sub']").shouldHave(exactText("Поле обязательно "
                + "для заполнения"));
    }

    @Test
    void shouldNotSendFormIfEmptyDate() {
        User user = DataGenerator.Registration.generateUser("ru");
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val("");
        $x("//*[@data-test-id='name']//input").val("Джеймс Бонд");
        $x("//*[@data-test-id='phone']//input").val("+79110070707");
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='date']//*[@class='input__sub']").shouldHave(exactText("Неверно "
                + "введена дата"));
    }

    @Test
    void shouldNotSendFormIfEmptyPhone() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generateDate(3);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val("");
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='phone']//*[@class='input__sub']").shouldHave(exactText("Поле обязательно "
                + "для заполнения"));
    }

    @Test
    void shouldNotSendFormIfWholeFormEmpty() {
        $x("//*[@data-test-id='date']//input").doubleClick().sendKeys("BackSpace");
        $x("//*[text()='Запланировать']").click();
        $x("//*[@data-test-id='city']//*[@class='input__sub']").shouldHave(exactText("Поле обязательно "
                + "для заполнения"));
    }
}
