package com.project.clicker.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigNumber {
    private BigDecimal value;

    // Sufiksy dla dużych liczb
    private static final String[] SUFFIXES = {
        "", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc",
        "UnD", "DoD", "TrD", "QaD", "QiD", "SxD", "SpD", "OcD", "NoD", "Vg",
        "UnV", "DoV", "TrV", "QaV", "QiV", "SxV", "SpV", "OcV", "NoV", "Tg",
        "UnT", "DoT", "TrT", "QaT", "QiT", "SxT", "SpT", "OcT", "NoT", "Qd"
    };

    // Konstruktory
    public BigNumber() {
        this.value = BigDecimal.ZERO;
    }

    public BigNumber(long number) {
        this.value = new BigDecimal(number);
    }

    public BigNumber(double number) {
        this.value = new BigDecimal(Double.toString(number));
    }

    public BigNumber(String number) {
        this.value = new BigDecimal(number);
    }

    public BigNumber(BigDecimal value) {
        this.value = value;
    }

    public BigNumber(BigNumber other) {
        this.value = new BigDecimal(other.value.toString());
    }

    // Operacje matematyczne
    public BigNumber add(BigNumber other) {
        return new BigNumber(this.value.add(other.value));
    }

    public BigNumber add(long number) {
        return new BigNumber(this.value.add(new BigDecimal(number)));
    }

    public BigNumber add(double number) {
        return new BigNumber(this.value.add(new BigDecimal(Double.toString(number))));
    }

    public BigNumber subtract(BigNumber other) {
        return new BigNumber(this.value.subtract(other.value));
    }

    public BigNumber subtract(long number) {
        return new BigNumber(this.value.subtract(new BigDecimal(number)));
    }

    public BigNumber multiply(BigNumber other) {
        return new BigNumber(this.value.multiply(other.value));
    }

    public BigNumber multiply(double multiplier) {
        return new BigNumber(this.value.multiply(new BigDecimal(Double.toString(multiplier))));
    }

    public BigNumber multiply(long multiplier) {
        return new BigNumber(this.value.multiply(new BigDecimal(multiplier)));
    }

    public BigNumber divide(BigNumber other) {
        if (other.value.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Dzielenie przez zero!");
        }
        return new BigNumber(this.value.divide(other.value, 10, RoundingMode.HALF_UP));
    }

    public BigNumber divide(double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Dzielenie przez zero!");
        }
        return new BigNumber(this.value.divide(new BigDecimal(Double.toString(divisor)), 10, RoundingMode.HALF_UP));
    }

    // Operacje porównania
    public boolean isGreaterThan(BigNumber other) {
        return this.value.compareTo(other.value) > 0;
    }

    public boolean isGreaterThan(long number) {
        return this.value.compareTo(new BigDecimal(number)) > 0;
    }

    public boolean isGreaterOrEqual(BigNumber other) {
        return this.value.compareTo(other.value) >= 0;
    }

    public boolean isGreaterOrEqual(long number) {
        return this.value.compareTo(new BigDecimal(number)) >= 0;
    }

    public boolean isLessThan(BigNumber other) {
        return this.value.compareTo(other.value) < 0;
    }

    public boolean isLessThan(long number) {
        return this.value.compareTo(new BigDecimal(number)) < 0;
    }

    public boolean isLessOrEqual(BigNumber other) {
        return this.value.compareTo(other.value) <= 0;
    }

    public boolean equals(BigNumber other) {
        return this.value.compareTo(other.value) == 0;
    }

    public boolean equals(long number) {
        return this.value.compareTo(new BigDecimal(number)) == 0;
    }

    public boolean isZero() {
        return this.value.equals(BigDecimal.ZERO);
    }

    public boolean isPositive() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }

    // Konwersje
    public long toLong() {
        return this.value.longValue();
    }

    public double toDouble() {
        return this.value.doubleValue();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value.toString());
    }

    // Formatowanie do czytelnego stringa
    public String toReadableString() {
        if (value.compareTo(new BigDecimal("1000")) < 0) {
            // Dla liczb poniżej 1000 pokazuj bez sufiksu
            if (value.scale() > 0 && value.stripTrailingZeros().scale() > 0) {
                return value.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            } else {
                return value.toBigInteger().toString();
            }
        }

        int suffixIndex = 0;
        BigDecimal temp = new BigDecimal(value.toString());

        // Znajdź odpowiedni sufiks
        while (temp.compareTo(new BigDecimal("1000")) >= 0 && suffixIndex < SUFFIXES.length - 1) {
            temp = temp.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);
            suffixIndex++;
        }

        // Formatuj liczbę
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(temp) + SUFFIXES[suffixIndex];
    }

    // Formatowanie z konkretną liczbą miejsc po przecinku
    public String toReadableString(int decimalPlaces) {
        if (value.compareTo(new BigDecimal("1000")) < 0) {
            return value.setScale(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
        }

        int suffixIndex = 0;
        BigDecimal temp = new BigDecimal(value.toString());

        while (temp.compareTo(new BigDecimal("1000")) >= 0 && suffixIndex < SUFFIXES.length - 1) {
            temp = temp.divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP);
            suffixIndex++;
        }

        StringBuilder pattern = new StringBuilder("#.");
        for (int i = 0; i < decimalPlaces; i++) {
            pattern.append("#");
        }

        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(temp) + SUFFIXES[suffixIndex];
    }

    // Metody pomocnicze
    public BigNumber abs() {
        return new BigNumber(this.value.abs());
    }

    public BigNumber negate() {
        return new BigNumber(this.value.negate());
    }

    public BigNumber pow(int exponent) {
        return new BigNumber(this.value.pow(exponent));
    }

    public BigNumber sqrt() {
        if (this.value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Nie można obliczyć pierwiastka z liczby ujemnej");
        }

        // Implementacja algorytmu Newtona dla pierwiastka kwadratowego
        BigDecimal x = this.value;
        BigDecimal sqrt = new BigDecimal("1");

        for (int i = 0; i < 50; i++) {
            sqrt = sqrt.add(x.divide(sqrt, 10, RoundingMode.HALF_UP))
                .divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP);
        }

        return new BigNumber(sqrt);
    }

    // Metody statyczne dla często używanych wartości
    public static BigNumber ZERO() {
        return new BigNumber(0);
    }

    public static BigNumber ONE() {
        return new BigNumber(1);
    }

    public static BigNumber TEN() {
        return new BigNumber(10);
    }

    public static BigNumber HUNDRED() {
        return new BigNumber(100);
    }

    public static BigNumber THOUSAND() {
        return new BigNumber(1000);
    }

    public static BigNumber MILLION() {
        return new BigNumber(1000000);
    }

    // Metody statyczne dla operacji
    public static BigNumber max(BigNumber a, BigNumber b) {
        return a.isGreaterThan(b) ? a : b;
    }

    public static BigNumber min(BigNumber a, BigNumber b) {
        return a.isLessThan(b) ? a : b;
    }

    public static BigNumber valueOf(long value) {
        return new BigNumber(value);
    }

    public static BigNumber valueOf(double value) {
        return new BigNumber(value);
    }

    public static BigNumber valueOf(String value) {
        return new BigNumber(value);
    }

    // Override metod Object
    @Override
    public String toString() {
        return toReadableString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BigNumber bigNumber = (BigNumber) obj;
        return value.equals(bigNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // Metoda do debugowania - pokazuje pełną wartość
    public String toFullString() {
        return value.toPlainString();
    }

    // Parsowanie z stringa z sufiksem (np. "1.5M" -> BigNumber)
    public static BigNumber parse(String str) {
        str = str.trim().toUpperCase();

        if (str.isEmpty()) {
            return new BigNumber(0);
        }

        // Sprawdź czy ma sufiks
        String numberPart = str;
        String suffixPart = "";

        for (int i = 1; i < SUFFIXES.length; i++) {
            if (str.endsWith(SUFFIXES[i])) {
                numberPart = str.substring(0, str.length() - SUFFIXES[i].length());
                suffixPart = SUFFIXES[i];
                break;
            }
        }

        try {
            BigDecimal number = new BigDecimal(numberPart);

            if (!suffixPart.isEmpty()) {
                // Znajdź indeks sufiksu i pomnóż przez odpowiednią potęgę 1000
                for (int i = 1; i < SUFFIXES.length; i++) {
                    if (SUFFIXES[i].equals(suffixPart)) {
                        BigDecimal multiplier = new BigDecimal("1000").pow(i);
                        number = number.multiply(multiplier);
                        break;
                    }
                }
            }

            return new BigNumber(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nieprawidłowy format liczby: " + str);
        }
    }
}
