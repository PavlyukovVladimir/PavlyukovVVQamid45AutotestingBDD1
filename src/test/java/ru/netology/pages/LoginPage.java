package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import ru.netology.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private String baseUrl = "http://0.0.0.0:9999/";

    private SelenideElement loginElement = $("[data-test-id=login] input");
    private SelenideElement passwordElement = $("[data-test-id=password] input");
    private SelenideElement buttonElement = $("[data-test-id=action-login]");


    public LoginPage fillLogin(@NotNull String login) {
        loginElement.shouldBe(Condition.visible).setValue(login);
        return this;
    }

    public LoginPage fillPassword(@NotNull String pass) {
        passwordElement.shouldBe(Condition.visible).setValue(pass);
        return this;
    }

    public LoginPage fillForm(@NotNull DataHelper.Auth.Info info) {
        String login = info.getLogin();
        if (login != null) fillLogin(login);

        String pass = info.getPassword();
        if (pass != null) fillPassword(pass);

        return this;
    }

    public VerificationPage clickSubmit() {
        buttonElement.shouldBe(Condition.visible).click();
        return new VerificationPage();
    }

    public VerificationPage validLogin(@NotNull DataHelper.Auth.Info info) {
        return fillForm(info).clickSubmit();
    }
}
