package com.jt.common.cache;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LocalCacheTests {

    private static List<String> cache = new CopyOnWriteArrayList<>();

    public static List<String> getData() {

        if (cache.isEmpty()) {
            synchronized (LocalCacheTests.class) {
                if (cache.isEmpty()) {
                    System.out.println("(!) get data from database ");
                    List<String> data = Arrays.asList("A", "B", "C");
                    cache.addAll(data);
                }

            }
        }
        return cache;

    }

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(getData());
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(getData());
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(getData());
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }


}
