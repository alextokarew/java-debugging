package com.github.alextokarew.javadebugging.multithreaded;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlocks2 {
    private final Lock[] locks = new Lock[3];

    private Deadlocks2() {
        Arrays.setAll(locks, _ -> new ReentrantLock());
    }

    private void run() {
        try (var pool = Executors.newFixedThreadPool(locks.length)) {
            for (int i = 0; i < locks.length; i++) {
                final int lockId = i;
                pool.submit(() -> {
                    locks[lockId].lock();
                    try {
                        Thread.sleep(100);
                        var lock = locks[(lockId + 1) % locks.length];
                        lock.lock();
                        try {
                            IO.println("There should be a deadlock");
                        } finally {
                            lock.unlock();
                        }
                    } catch (InterruptedException ignored) {
                    } finally {
                        locks[lockId].unlock();
                    }
                });
            }
        }
    }

    static void main() {
        new Deadlocks2().run();
    }
}
