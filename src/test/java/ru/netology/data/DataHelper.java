package ru.netology.data;

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

    @Value
    public static class Balance {
        // Я понимаю, что этот класс - это довольно сильное усложнение.
        // Но я пытался представить реальную ситуацию и
        // попытался написать универсальный класс для представленного в задании примера вывода сумм банками.
        int rub;
        int kopecks;

        /**
         * If both arguments are not equal to 0, then they must have the same sign.
         */
        public Balance(int rub, int kopecks) {
            this.rub = rub;
            if (kopecks < 0 && rub > 0 || kopecks > 0 && rub < 0) {
                throw new IllegalArgumentException("Копейки c рублями не могут быть разного знака.");
            }
            if (Math.abs(kopecks) > 99) {
                throw new IllegalArgumentException("Сумма копеек не может быть больше 99.");
            }
            this.kopecks = kopecks;
        }

        public static Balance parseBalanceFromString(@NotNull String balanceStr) {
            balanceStr = balanceStr.replaceAll(" ", "");
            if (!balanceStr.matches(
                    "-?[1-9]\\d*,\\d{1,2}" + "|" +
                            "-?[1-9]\\d*" + "|" +
                            "-\\d,[1-9]\\d?" + "|" +
                            "-\\d,\\d[1-9]" + "|" +
                            "0" + "|" +
                            "0,\\d{1,2}")) {
                throw new NumberFormatException();
            }
            boolean isNegative = balanceStr.charAt(0) == '-';
            if (isNegative) {
                balanceStr = balanceStr.substring(1);
            }
            String[] amountParts = balanceStr.split(",");
            int rub = Integer.parseInt(amountParts[0]);
            if (amountParts.length == 1) {
                if (isNegative) {
                    return new Balance(-1 * rub, 0);
                } else {
                    return new Balance(rub, 0);
                }
            } else {
                String kopecksStr = amountParts[1];
                if (kopecksStr.equals("0") || kopecksStr.equals("00")) {
                    if (isNegative) {
                        return new Balance(-1 * rub, 0);
                    } else {
                        return new Balance(rub, 0);
                    }
                }
                if (kopecksStr.length() == 1) {
                    kopecksStr += "0";
                } else if (kopecksStr.charAt(0) == '0') {
                    kopecksStr = kopecksStr.substring(1);
                }
                int kopecks = Integer.parseInt(kopecksStr);
                if (isNegative) {
                    return new Balance(-1 * rub, -1 * kopecks);
                } else {
                    return new Balance(rub, kopecks);
                }
            }
        }

        public Balance(@NotNull String balanceStr) {
            Balance balance = parseBalanceFromString(balanceStr);
            rub = balance.getRub();
            kopecks = balance.getKopecks();
        }

        @Override
        public String toString() {
            if (kopecks == 0) {
                return "" + rub;
            } else if (kopecks < 0) {
                if (rub == 0) {
                    return String.format("-0,%02d", -1 * kopecks);
                } else {
                    return String.format("%d,%02d", rub, -1 * kopecks);
                }
            }
            return String.format("%d,%02d", rub, kopecks);
        }

        public int getBalanceInKopecks() {
            return this.getRub() * 100 + this.getKopecks();
        }

        public static Balance convertKopecksToBalance(int kopecks) {
            boolean isNegative = kopecks < 0;
            if (isNegative) {
                kopecks *= -1;
            }
            int rub = kopecks / 100;
            kopecks %= 100;
            if (isNegative) {
                return new Balance(-1 * rub, -1 * kopecks);
            }
            return new Balance(rub, kopecks);
        }

        public Balance add(@NotNull Balance balance) {
            int thisKopecks = this.getBalanceInKopecks();
            int otherKopecks = balance.getBalanceInKopecks();
            return convertKopecksToBalance(thisKopecks + otherKopecks);
        }

        public Balance add(int rub, int kopecks) {
            return this.add(new Balance(rub, kopecks));
        }
    }

}
