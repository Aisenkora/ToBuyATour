package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PurchasePage {
    private final SelenideElement buy = $(byText("Купить"));
    private final SelenideElement buyOnCredit = $(byText("Купить в кредит"));
    private final SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private final SelenideElement creditByCard = $(byText("Кредит по данным карты"));

    public DashboardPage buyByCard() {
        buy.click();
        paymentByCard.shouldBe(visible);
        return new DashboardPage();
    }

    public DashboardPage buyByCreditCard() {
        buyOnCredit.click();
        creditByCard.shouldBe(visible);
        return new DashboardPage();
    }
}
