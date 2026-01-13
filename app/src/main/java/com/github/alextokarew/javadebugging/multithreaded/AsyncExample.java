package com.github.alextokarew.javadebugging.multithreaded;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class AsyncExample {
    static void main() throws Exception {
//        var pool = ForkJoinPool.commonPool();

        var f = CompletableFuture.supplyAsync(() -> {
            IO.println("1. Current thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Done";
        }).thenApplyAsync((s) -> {
            IO.println("2. Current thread: " + Thread.currentThread().getName());
            return "Well " + s;
        });

        System.out.println(f.get());
    }
}
