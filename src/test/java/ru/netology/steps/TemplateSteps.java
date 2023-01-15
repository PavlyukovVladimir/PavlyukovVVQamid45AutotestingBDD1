package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.DataHelper.Auth;
import ru.netology.data.DataHelper.Balance;
import ru.netology.data.DataHelper.Verify;
import ru.netology.pages.LoginPage;
import ru.netology.pages.PersonalAccountPage;


public class TemplateSteps {
    private PersonalAccountPage page;
    private Balance firstCardBalance;
    private Balance secondCardBalance;


    @Пусть("пользователь залогинен и находится в личном кабинете")
    public void loginToPersonalAccount() {
        Auth.Info info = Auth.getValidInfo();
        page = Selenide.open("http://localhost:9999", LoginPage.class)
                .validLogin(info)
                .validVerify(Verify.getSmsCode(info))
                .verifyIsPersonalAccountPage();
        firstCardBalance = page.getFirstCardBalance();
        secondCardBalance = page.getSecondCardBalance();
    }

    @Когда("пользователь переводит {string} рублей с первой на вторую карту")
    public void fistToSecond(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        page
                .secondTopUpClick()
                .topUp(balance.getRub(), balance.getKopecks());
    }

    @Когда("пользователь переводит {string} рублей с первой на первую карту")
    public void fistToFirst(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        page
                .firstTopUpClick()
                .selfTopUp(balance.getRub(), balance.getKopecks());
    }

    @Когда("пользователь переводит {string} рублей со второй на вторую карту")
    public void secondToSecond(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        page
                .secondTopUpClick()
                .selfTopUp(balance.getRub(), balance.getKopecks());
    }

    @Когда("пользователь переводит {string} рублей со второй на первую карту")
    public void secondToFirst(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        page
                .firstTopUpClick()
                .topUp(balance.getRub(), balance.getKopecks());
    }

    @Тогда("баланс его первой карты должен уменьшиться на {string} рублей")
    @И("должен уменьшиться баланс его первой карты на {string} рублей")
    public void firstBalanceDecreased(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        Balance expectedBalance = firstCardBalance.add(-1 * balance.getRub(), -1 * balance.getKopecks());
        page.checkFirstCardBalance(expectedBalance);
    }

    @Тогда("баланс его первой карты должен увеличиться на {string} рублей")
    @И("должен увеличиться баланс его первой карты на {string} рублей")
    public void firstBalanceIncreased(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        Balance expectedBalance = firstCardBalance.add(balance.getRub(), balance.getKopecks());
        page.checkFirstCardBalance(expectedBalance);
    }

    @Тогда("баланс его второй карты должен уменьшиться на {string} рублей")
    @И("должен уменьшиться баланс его второй карты на {string} рублей")
    public void secondBalanceDecreased(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        Balance expectedBalance = secondCardBalance.add(-1 * balance.getRub(), -1 * balance.getKopecks());
        page.checkSecondCardBalance(expectedBalance);
    }

    @Тогда("баланс его второй карты должен увеличиться на {string} рублей")
    @И("должен увеличиться баланс его второй карты на {string} рублей")
    public void secondBalanceIncreased(String balanceStr) {
        Balance balance = Balance.parseBalanceFromString(balanceStr);
        Balance expectedBalance = secondCardBalance.add(balance.getRub(), balance.getKopecks());
        page.checkSecondCardBalance(expectedBalance);
    }

    @Тогда("баланс его первой карты не изменится")
    @И("баланс его первой карты не должен измениться")
    public void firstBalanceConst() {
        page.checkFirstCardBalance(firstCardBalance);
    }

    @Тогда("баланс его второй карты не изменится")
    @И("баланс его второй карты не должен измениться")
    public void secondBalanceConst() {
        page.checkSecondCardBalance(secondCardBalance);
    }

}
