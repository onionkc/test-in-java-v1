package com.kangcong.yian.test1;

import java.util.concurrent.CountDownLatch;

/**
 *
 *
 * @author kangcong
 * @since 2025-06-25 14:30
 */
public class TestBugBot {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(() -> {
            System.out.println("Task 1 started");
            // 忘记调用 countDown()，导致 latch 永远不会释放
            System.out.println("Task 1 finished");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Task 2 started");
            // 错误地多次调用 countDown()
            latch.countDown();
            latch.countDown(); // 多次减少计数器，可能导致提前释放
            System.out.println("Task 2 finished");
        });

        Thread t3 = new Thread(() -> {
            System.out.println("Task 3 started");
            // 正确调用一次 countDown()
            latch.countDown();
            System.out.println("Task 3 finished");
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            // 主线程等待 latch 计数归零
            latch.await();
            System.out.println("All tasks completed, proceeding...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
