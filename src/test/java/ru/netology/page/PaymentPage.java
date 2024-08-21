package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement holder = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvccvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement button = $(byText("Продолжить"));

    //private SelenideElement wrongFormatCard = $(byText("Неверный формат")).parent().$(".input__sub");
    private SelenideElement incorrectCardNumber = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement incorrectMonth = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement incorrectYear = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement cardsExpirationError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardsExpirationFinished = $(byText("Истёк срок действия карты"));
    private SelenideElement incorrectHolder = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement incorrectCode = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void completedForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holder.setValue(cardInfo.getHolder());
        cvccvv.setValue(cardInfo.getCVCCVV());
        button.click();
    }

    public void emptyForm() {
        button.click();
        incorrectCardNumber.shouldBe(visible);
        incorrectMonth.shouldBe(visible);
        incorrectYear.shouldBe(visible);
        incorrectHolder.shouldBe(visible);
        incorrectCode.shouldBe(visible);
    }

    public void paymentApproved() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(35));
    }

    public void paymentDeclined() {
        $(byCssSelector("div.notification.notification_status_error.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white")).shouldBe(Condition.hidden, Duration.ofSeconds(25));
    }

    public void incorrectCardNumberVisible() {
        incorrectCardNumber.shouldBe(visible);
    }

    public void incorrectCardExpirationDate() {
        cardsExpirationError.shouldBe(visible);
    }

    public void cardExpirationFinishedVisible() {
        cardsExpirationFinished.shouldBe(visible);
    }

    public void incorrectMonthVisible() {
        incorrectMonth.shouldBe(visible);
    }

    public void incorrectYearVisible() {
        incorrectYear.shouldBe(visible);
    }

    public void incorrectHolderVisible() {
        incorrectHolder.shouldBe(visible);
    }

    public void incorrectCodeVisible() {
        incorrectCode.shouldBe(visible);
    }
}