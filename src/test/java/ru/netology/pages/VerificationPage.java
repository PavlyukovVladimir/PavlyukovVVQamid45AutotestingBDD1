package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement verificationElement = $("[data-test-id=code] input");
    private final SelenideElement verificationButtonElement = $("[data-test-id=action-verify]");

    public VerificationPage fillVerification(@NotNull String code) {
        verificationElement.shouldBe(Condition.visible).setValue(code);
        return this;
    }

    public PersonalAccountPage clickSubmit() {
        verificationButtonElement.shouldBe(Condition.visible).click();
        return new PersonalAccountPage();
    }

    public PersonalAccountPage validVerify(@NotNull String code) {
        return fillVerification(code).clickSubmit();
    }

}
