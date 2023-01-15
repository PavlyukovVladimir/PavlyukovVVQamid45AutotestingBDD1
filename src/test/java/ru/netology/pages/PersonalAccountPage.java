package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.data.DataHelper.Balance;
import ru.netology.data.DataHelper.Cards;

import static com.codeborne.selenide.Selenide.$;


public class PersonalAccountPage {
    private final String firstId = Cards.getFirstCardId();
    private final String secondId = Cards.getSecondCardId();

    private final SelenideElement title = $("[data-test-id=dashboard]");
    private final SelenideElement firstCardElement = $("[data-test-id=\"" + firstId + "\"]");
    private final SelenideElement firstCardButtonElement = $("[data-test-id=\"" + firstId + "\"] button");
    private final SelenideElement secondCardElement = $("[data-test-id=\"" + secondId + "\"]");
    private final SelenideElement secondCardButtonElement = $("[data-test-id=\"" + secondId + "\"] button");


    public TopUpFromOwnCardPage firstTopUpClick() {
        firstCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(firstId);
    }

    public TopUpFromOwnCardPage secondTopUpClick() {
        secondCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(secondId);
    }

    public PersonalAccountPage checkFirstCardBalance(@NotNull Balance balance) {
        firstCardElement.shouldHave(Condition.ownText("баланс: " + balance + " р."));
        return this;
    }

    public PersonalAccountPage checkSecondCardBalance(@NotNull Balance balance) {
        secondCardElement.shouldHave(Condition.ownText("баланс: " + balance + " р."));
        return this;
    }

    private Balance extractBalance(@NotNull String text) {
        String balanceStart = "баланс: ";
        int start = text.indexOf(balanceStart) + balanceStart.length();
        String balanceFinish = " р.";
        int end = text.indexOf(balanceFinish);
        String balanceStr = text.substring(start, end);

        return new Balance(balanceStr);
    }

    public Balance getFirstCardBalance() {
        String text = firstCardElement.text();
        return extractBalance(text);
    }

    public Balance getSecondCardBalance() {
        String text = secondCardElement.text();
        return extractBalance(text);
    }

    public PersonalAccountPage verifyIsPersonalAccountPage() {
        title.shouldHave(Condition.text("  Личный кабинет"));
        return this;
    }
}
