package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TopUpFromOwnCardPage {
    private final String cardId;

    public TopUpFromOwnCardPage(@NotNull String cardId) {
        this.cardId = cardId;
    }

    Condition ready = Condition.and("ready", Condition.visible, Condition.enabled);
    private final SelenideElement title = $("h1");
    private final SelenideElement fldAmount = $("[data-test-id=amount] input");
    private final SelenideElement fldFrom = $("[data-test-id=from] input");
    private final SelenideElement fldTo = $("[data-test-id=to] input");
    private final SelenideElement btnTopUp = $("button[data-test-id=action-transfer]");
    private final SelenideElement errorMessageTitle = $("[data-test-id=error-notification] .notification__title");
    private final SelenideElement errorMessageContent = $("[data-test-id=error-notification] .notification__content");

    public TopUpFromOwnCardPage checkTitle() {
        title.should(Condition.visible).shouldHave(Condition.text("Пополнение карты"));
        return this;
    }

    public TopUpFromOwnCardPage fillAmount(int amount, int fractional) {
        String value = "" + amount;
        if (fractional > 0) {
            value += String.format(",%02d", fractional);
        }
        fldAmount.shouldBe(ready).setValue(value);
        return this;
    }

    public TopUpFromOwnCardPage fillValidFromField() {
        String cardNumberFrom = DataHelper.Cards.getOtherCardNumber(cardId);
        fldFrom.shouldBe(ready).setValue(cardNumberFrom);
        return this;
    }

    public TopUpFromOwnCardPage fillSameCardFromField() {
        String cardNumberFrom = DataHelper.Cards.getCardNumberFromId(cardId);
        fldFrom.shouldBe(ready).setValue(cardNumberFrom);
        return this;
    }

    public TopUpFromOwnCardPage fillFromField(@NotNull String cardNumber) {
        fldFrom.shouldBe(ready).setValue(cardNumber);
        return this;
    }

    public TopUpFromOwnCardPage checkToField() {
        String currentCardNumber = DataHelper.Cards.getCardNumberFromId(cardId);
        String hiddenCardNumber = "**** **** **** ";
        hiddenCardNumber += currentCardNumber.substring(currentCardNumber.length() - 5);
        fldTo
                .shouldBe(Condition.visible)
                .shouldBe(Condition.disabled)
                .shouldHave(Condition.value(hiddenCardNumber));
        return this;
    }

    public PersonalAccountPage topUpButtonClick() {
        btnTopUp.shouldBe(ready).click();
        return new PersonalAccountPage();
    }

    public PersonalAccountPage topUp(int amount, int fractional) {
        return checkTitle()
                .fillAmount(amount, fractional)
                .fillValidFromField()
                .checkToField()
                .topUpButtonClick();
    }

    public PersonalAccountPage selfTopUp(int amount, int fractional) {
        return checkTitle()
                .fillAmount(amount, fractional)
                .fillSameCardFromField()
                .checkToField()
                .topUpButtonClick();
    }

    public void unknownTopUp(int amount, int fractional) {
        checkTitle()
                .fillAmount(amount, fractional)
                .fillFromField(DataHelper.Cards.getUnknownCardNumber())
                .checkToField()
                .topUpButtonClick();
        checkErrorMessage();
    }

    public void checkErrorMessage() {
        errorMessageTitle.shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка"));
        errorMessageContent.shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
    }
}
