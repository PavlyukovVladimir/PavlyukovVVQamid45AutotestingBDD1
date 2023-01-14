package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.Balance;
import ru.netology.DataHelper;

import static com.codeborne.selenide.Selenide.$;


public class PersonalAccountPage {
    private final String firstId = DataHelper.Cards.getFirstCardId();
    private final String secondId = DataHelper.Cards.getSecondCardId();

    private SelenideElement firstCardElement = $("[data-test-id=\"" + firstId + "\"]");
    private SelenideElement firstCardButtonElement = $("[data-test-id=\"" + firstId + "\"] button");
    private SelenideElement secondCardElement = $("[data-test-id=\"" + secondId + "\"]");
    private SelenideElement secondCardButtonElement = $("[data-test-id=\"" + secondId + "\"] button");


    public TopUpFromOwnCardPage firstTopUpClick() {
        firstCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(firstId);
    }

    public TopUpFromOwnCardPage secondTopUpClick() {
        secondCardButtonElement.shouldBe(Condition.visible).click();
        return new TopUpFromOwnCardPage(secondId);
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

}
