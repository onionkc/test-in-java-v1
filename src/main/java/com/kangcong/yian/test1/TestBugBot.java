package com.kangcong.yian.test1;

import java.util.concurrent.CountDownLatch;

/**
 * TestBugBot - 用于测试BugBot自动代码审查功能
 * 该类包含故意设计的bug，用于验证BugBot的检测能力
 *
 * @author kangcong
 * @since 2025-06-25 14:30
 */
public class TestBugBot {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(() -> {
            System.out.println("Task 1 started");
            // BUG: 忘记调用 countDown()，导致 latch 永远不会释放
            // 这会导致主线程永远等待，造成死锁
            System.out.println("Task 1 finished");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Task 2 started");
            // BUG: 错误地多次调用 countDown()
            latch.countDown();
            latch.countDown(); // 多次减少计数器，可能导致提前释放
            // 这会导致CountDownLatch计数变为负数，违反其设计原则
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
            // 由于Task 1没有调用countDown，这里会永远等待
            latch.await();
            System.out.println("All tasks completed, proceeding...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
