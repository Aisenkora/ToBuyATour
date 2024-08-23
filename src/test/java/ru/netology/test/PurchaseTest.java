package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.PurchasePage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
    }

    @Test
    @DisplayName("Should approved card payment")
    void shouldCardPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should approved card payment by credit")
    void shouldCreditPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Should declined payment")
    void shouldCardPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should declined payment by credit")
    void shouldCreditPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    //Форма "Оплата по карте":

    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegistered() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatin() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //номер карты арабской вязью
    @Test
    public void shouldCardWithArabicLigature() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithArabicLigature());
        form.incorrectCardNumberVisible();
    }

    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphs() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }

    //не заполнение номера карты
    @Test
    public void shouldCardIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExist() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible();
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundary() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible();
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZero() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible();
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible();
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible();
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible();
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible();
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatin() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible();
    }

    //месяц арабской вязью
    @Test
    public void shouldMonthWithArabicLigature() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible();
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphs() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible();
    }

    //не заполнение месяца
    @Test
    public void shouldMonthIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible();
    }

    //прошедший год
    @Test
    public void shouldLastYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible();
    }

    //год на 25 лет превышающий текущий
    @Test
    public void shouldYear25YearsMore() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible();
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible();
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible();
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZero() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible();
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible();
    }

    //год кириллицей
    @Test
    public void shouldYearWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithCyrillic());
        form.incorrectYearVisible();
    }

    //год латиницей
    @Test
    public void shouldYearWithLatin() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithLatin());
        form.incorrectYearVisible();
    }

    //год арабской вязью
    @Test
    public void shouldYearWithArabicLigature() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithArabicLigature());
        form.incorrectYearVisible();
    }

    //год иероглифами
    @Test
    public void shouldYearWithHieroglyphs() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithHieroglyphs());
        form.incorrectYearVisible();
    }

    //не заполнение поле "Год"
    @Test
    public void shouldYearIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible();
    }

    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetter() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLetters() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //не заполнение поля "Владелец"
    @Test
    public void shouldHolderIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatin() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабской вязью
    @Test
    public void shouldCVCCVVWithArabicLigature() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphs() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }

    //не заполнение кода
    @Test
    public void shouldCVCCVVIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    //пустая форма
    @Test
    void shouldFormIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.emptyForm();
    }

    //Форма "Кредит по данным карты":

    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegisteredByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatinByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //номер карты арабской вязью
    @Test
    public void shouldCardWithArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithArabicLigature());
        form.incorrectCardNumberVisible();
    }

    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }

    //не заполнение номера карты
    @Test
    public void shouldCardIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExistByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible();
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundaryByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible();
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZeroByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible();
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible();
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible();
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible();
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible();
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatinByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible();
    }

    //месяц арабской вязью
    @Test
    public void shouldMonthWithArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible();
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible();
    }

    //не заполнение месяца
    @Test
    public void shouldMonthIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible();
    }

    //прошедший год
    @Test
    public void shouldLastYearByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible();
    }

    //год на 25 лет превышающий текущий
    @Test
    public void shouldYear25YearsMoreByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible();
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible();
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible();
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZeroByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible();
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible();
    }

    //год кириллицей
    @Test
    public void shouldYearWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithCyrillic());
        form.incorrectYearVisible();
    }

    //год латиницей
    @Test
    public void shouldYearWithLatinByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithLatin());
        form.incorrectYearVisible();
    }

    //год арабской вязью
    @Test
    public void shouldYearWithArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithArabicLigature());
        form.incorrectYearVisible();
    }

    //год иероглифами
    @Test
    public void shouldYearWithHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithHieroglyphs());
        form.incorrectYearVisible();
    }

    //не заполнение поле "Год"
    @Test
    public void shouldYearIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible();
    }

    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetterByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLettersByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //не заполнение поля "Владелец"
    @Test
    public void shouldHolderIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatinByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабской вязью
    @Test
    public void shouldCVCCVVWithArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }

    //не заполнение кода
    @Test
    public void shouldCVCCVVIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    //пустая форма
    @Test
    void shouldFormIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.emptyForm();
    }
}