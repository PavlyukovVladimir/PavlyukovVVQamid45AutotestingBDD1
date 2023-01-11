package ru.netology;

import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NotFoundException;


public class DataHelper {
    private DataHelper() {
    }

    public static class Auth {
        private Auth() {
        }

        @Value
        public static class Info {
            String login;
            String password;
        }

        public static Info getValidInfo() {
            Info info = new Info("vasya", "qwerty123");
            return info;
        }

    }

    public static class Verify {
        private Verify() {
        }

        public static String getSmsCode(@NotNull Auth.Info info) {
            return "12345";
        }
    }

    public static class Cards {
        private Cards() {
        }

        public static String getCardNumberFromId(@NotNull String cardId) {
            final String firstId = getFirstCardId();
            final String secondId = getSecondCardId();
            if (cardId.equals(firstId)) {
                return "5559 0000 0000 0001";
            } else if (cardId.equals(secondId)) {
                return "5559 0000 0000 0002";
            }
            throw new NotFoundException("Неизвестный id карты");
        }

        public static String getUnknownCardNumber() {
            return "5559 0000 0000 0003";
        }

        public static String getFirstCardId() {
            return "92df3f1c-a033-48e6-8390-206f6b1f56c0";
        }

        public static String getSecondCardId() {
            return "0f3f5c2a-249e-4c3d-8287-09f7a039391d";
        }

        public static String getOtherCardNumber(@NotNull String cardId) {
            if (cardId.equals(getFirstCardId())) {
                return getCardNumberFromId(getSecondCardId());
            }
            if (cardId.equals(getSecondCardId())) {
                return getCardNumberFromId(getFirstCardId());
            }
            throw new NotFoundException("Неизвестный id карты");
        }
    }

}
