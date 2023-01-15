package ru.netology;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.data.DataHelper.Auth;
import ru.netology.data.DataHelper.Balance;
import ru.netology.data.DataHelper.Verify;
import ru.netology.pages.LoginPage;
import ru.netology.pages.PersonalAccountPage;
import ru.netology.pages.TopUpFromOwnCardPage;


@ExtendWith({ScreenShooterExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransferBetweenOwnCardsTest {
    private PersonalAccountPage page;
    private Balance firstCardBalance;
    private Balance secondCardBalance;

    @BeforeEach
    void setUp() {
//        Configuration.browser = "chrome";
//        Configuration.baseUrl = "http://localhost:9999";
//        Configuration.holdBrowserOpen = true;  // false не оставляет браузер открытым по завершению теста
//        Configuration.reportsFolder = "build/reports/tests/test/screenshots";
        Selenide.open("");
        Auth.Info info = Auth.getValidInfo();
        page = new LoginPage().validLogin(info).validVerify(Verify.getSmsCode(info));
        firstCardBalance = page.getFirstCardBalance();
        secondCardBalance = page.getSecondCardBalance();
    }

    @Order(1)
    @DisplayName("С первой на вторую 100")
    @Test
    void firstToSecond100Test() {
        page
                .secondTopUpClick()
                .topUp(100, 0);

        Balance expectedFirstCardBalance = firstCardBalance.add(-100, 0);
        Balance expectedSecondCardBalance = secondCardBalance.add(100, 0);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }


    @Order(1)
    @DisplayName("С первой на вторую -100, - проигнорируется")
    @Test
    void firstToSecond_100Test() {
        page
                .secondTopUpClick()
                .topUp(-100, 0);

        Balance expectedFirstCardBalance = firstCardBalance.add(-100, 0);
        Balance expectedSecondCardBalance = secondCardBalance.add(100, 0);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }

    @Order(1)
    @DisplayName("Со второй на первую 100")
    @Test
    void secondToFirst100Test() {
        page
                .firstTopUpClick()
                .topUp(100, 0);

        Balance expectedFirstCardBalance = firstCardBalance.add(100, 0);
        Balance expectedSecondCardBalance = secondCardBalance.add(-100, 0);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }

    @Order(1)
    @DisplayName("Со второй на первую -100, - проигнорируется")
    @Test
    void secondToFirst_100Test() {
        page
                .firstTopUpClick()
                .topUp(-100, 0);

        Balance expectedFirstCardBalance = firstCardBalance.add(100, 0);
        Balance expectedSecondCardBalance = secondCardBalance.add(-100, 0);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }

    @Order(1)
    @DisplayName("С первой на первую 100")
    @Test
    void firstToFirst100Test() {
        page
                .firstTopUpClick()
                .selfTopUp(100, 0);
        page
                .checkFirstCardBalance(firstCardBalance)
                .checkSecondCardBalance(secondCardBalance);
    }

    @Order(1)
    @DisplayName("Со второй на вторую 100")
    @Test
    void secondToSecond100Test() {
        page
                .secondTopUpClick()
                .selfTopUp(100, 0);
        page
                .checkFirstCardBalance(firstCardBalance)
                .checkSecondCardBalance(secondCardBalance);
    }

    @Order(1)
    @DisplayName("С неизвестной на вторую 100")
    @Test
    void unknownToSecond100Test() {
        page
                .secondTopUpClick()
                .unknownTopUp(100, 0);
    }

    @Order(1)
    @DisplayName("С неизвестной на первую 100")
    @Test
    void unknownToFirst100Test() {
        page
                .firstTopUpClick()
                .unknownTopUp(100, 0);
    }

    @Order(1)
    @DisplayName("Со второй на первую 0.01")
    @Test
    void secondToFirstS001Test() {
        page
                .firstTopUpClick()
                .topUp(0, 1);

        Balance expectedFirstCardBalance = firstCardBalance.add(0, 1);
        Balance expectedSecondCardBalance = secondCardBalance.add(0, -1);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }

    @Order(1)
    @DisplayName("С первой на вторую 0.01")
    @Test
    void firstToSecond001Test() {
        page
                .secondTopUpClick()
                .topUp(0, 1);

        Balance expectedFirstCardBalance = firstCardBalance.add(0, -1);
        Balance expectedSecondCardBalance = secondCardBalance.add(0, 1);
        page
                .checkSecondCardBalance(expectedSecondCardBalance)
                .checkFirstCardBalance(expectedFirstCardBalance);
    }

    @Order(1)
    @DisplayName("С первой на вторую 1.01")
    @Test
    void firstToSecond1_001Test() {
        page
                .secondTopUpClick()
                .topUp(1, 1);

        Balance expectedFirstCardBalance = firstCardBalance.add(0, -1);
        Balance expectedSecondCardBalance = secondCardBalance.add(0, 1);
        page
                .checkSecondCardBalance(expectedSecondCardBalance)
                .checkFirstCardBalance(expectedFirstCardBalance);
    }

    @Order(1)
    @DisplayName("Со второй на первую 1.01")
    @Test
    void secondToFirst1_001Test() {
        page
                .firstTopUpClick()
                .topUp(1, 1);

        Balance expectedFirstCardBalance = firstCardBalance.add(1, 1);
        Balance expectedSecondCardBalance = secondCardBalance.add(-1, -1);
        page
                .checkFirstCardBalance(expectedFirstCardBalance)
                .checkSecondCardBalance(expectedSecondCardBalance);
    }

    @Order(2)
    @DisplayName("Со второй на первую больше чем есть")
    @Test
    void secondToFirstMoreThanThereIsTest() {
        TopUpFromOwnCardPage topUpPage = page.firstTopUpClick();
        topUpPage.topUp(secondCardBalance.getRub() + 1, 0);
        topUpPage.checkErrorMessage();
    }

}
