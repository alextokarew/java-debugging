package com.github.alextokarew.javadebugging.multithreaded;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExample {
    private static CompletableFuture<User> getUserByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("UserService: fetching user by name " + name);
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            return new User(1, name);
        });
    }

    private static CompletableFuture<List<Order>> getOrders(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("OrderService: fetching orders for user " + userId);
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            if (userId == 1) throw new RuntimeException("BOOM");
            return Arrays.asList(
                    new Order(101, userId, "Book"),
                    new Order(102, userId, "Laptop")
            );
        });
    }

    static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<User> userFuture = getUserByName(args[0]);
        CompletableFuture<List<Order>> ordersFuture = userFuture.thenCompose(user -> getOrders(user.id));
        IO.println("Orders for user: " + ordersFuture.get());
    }

    record User(int id, String name) {}
    record Order(int id, int userId, String item) {}
}
