package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginElement = $("[data-test-id=login] input");
    private SelenideElement passwordElement = $("[data-test-id=password] input");
    private SelenideElement buttonElement = $("[data-test-id=action-login]");


    public VerificationPage validLogin(@NotNull DataHelper.Auth.Info info) {
        String login = info.getLogin();
        if (login != null) loginElement.shouldBe(Condition.visible).setValue(login);

        String pass = info.getPassword();
        if (pass != null) passwordElement.shouldBe(Condition.visible).setValue(pass);

        buttonElement.shouldBe(Condition.visible).click();
        return new VerificationPage();
    }
}
