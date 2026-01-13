package com.github.alextokarew.javadebugging.multithreaded;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class Deadlocks {

    private final Object[] locks = new Object[3];

    private Deadlocks() {
        Arrays.setAll(locks, _ -> new Object());
    }

    private void run() {
        try (var pool = Executors.newFixedThreadPool(locks.length)) {
            for (int i = 0; i < locks.length; i++) {
                final int lockId = i;
                pool.submit(() -> {
                    synchronized (locks[lockId]) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignored) {}
                        synchronized (locks[(lockId + 1) % locks.length]) {
                            IO.println("There should be a deadlock");
                        }
                    }
                });
            }
            IO.println("Tasks were submitted");
        }
    }

    static void main() {
        new Deadlocks().run();
    }
}
