package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class PersonalAccountPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final String firstId = DataHelper.Cards.getFirstCardId();
    private final String secondId = DataHelper.Cards.getSecondCardId();

    private SelenideElement firstCardElement = $("[data-test-id=\"" + firstId + "\"]");
    private SelenideElement firstCardButtonElement = $("[data-test-id=\"" + firstId + "\"] button");
    private SelenideElement secondCardElement = $("[data-test-id=\"" + secondId + "\"]");
    private SelenideElement secondCardButtonElement = $("[data-test-id=\"" + secondId + "\"] button");
    private SelenideElement reloadButtonElement = $("[data-test-id=\"action-reload\"]");

    public PersonalAccountPage clickSubmit() {
        reloadButtonElement.shouldBe(Condition.visible).click();
        return this;
    }

    public TopUpFromOwnCardPage firstTopUpClick() {
        firstCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(firstId);
    }

    public TopUpFromOwnCardPage secondTopUpClick() {
        secondCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(secondId);
    }

    public int getFirstCardBalance() {
        String text = firstCardElement.text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        String text = secondCardElement.text();
        return extractBalance(text);
    }

    private int extractBalance(@NotNull String text) {
        int start = text.indexOf(balanceStart) + balanceStart.length();
        int end = text.indexOf(balanceFinish);
        String balanceStr = text.substring(start, end);
        int balance = Integer.parseInt(balanceStr);
        return balance;
    }
}
