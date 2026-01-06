package com.github.alextokarew.javadebugging.jdb;

public class Calculator {
    private int multiplier;

    Calculator(int multiplier) {
        this.multiplier = multiplier;
    }

    public int add(int a, int b) {
        int sum = a + b;
        sum = multiply(sum);
        return sum;
    }

    private int multiply(int value) {
        return value * multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
