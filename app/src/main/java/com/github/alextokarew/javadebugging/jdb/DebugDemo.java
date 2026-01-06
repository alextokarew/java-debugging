package com.github.alextokarew.javadebugging.jdb;

public class DebugDemo {
    static void main(String[] args) {
        int x = 5;
        int y = 10;
        Calculator calc = new Calculator(2);
        int result = calc.add(x, y);
        calc.setMultiplier(4);
        assert x == 10;
        IO.println("Result: " + result);
    }
}
