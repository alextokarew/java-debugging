package com.github.alextokarew.javadebugging.multithreaded;

import java.util.concurrent.Executors;

public class Races {
    private int counter = 0;

    private void run() {
        try(var pool = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 2; i++) {
                pool.submit(() -> {
                    counter++;
                });
            }
        }
    }

    static void main() {
        var races = new Races();
        races.run();
        IO.println("Counter: Expected 2, actual is: " +  races.counter);
    }
}
