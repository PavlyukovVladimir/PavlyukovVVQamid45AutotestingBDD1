package ru.netology;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

@Value
public class Balance {
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
