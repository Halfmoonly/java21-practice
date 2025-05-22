package com.hm.virtualthreadsimplevs.performance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/5/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class PerformanceTest {

    //模拟1W个请求
    private static final int REQUEST_NUM = 10000;


    public static void main(String[] args) {
        long vir = 0, p1 = 0, p2 = 0, p3 = 0, p4 = 0;
        for (int i = 0; i < 3; i++) {
            vir += testVirtualThread();
            p1 += testPlatformThread(200);
            p2 += testPlatformThread(500);
            p3 += testPlatformThread(800);
            p4 += testPlatformThread(1000);
            System.out.println("--------------");
        }
        System.out.println("虚拟线程平均耗时：" + vir / 3 + "ms");
        System.out.println("平台线程[200]平均耗时：" + p1 / 3 + "ms");
        System.out.println("平台线程[500]平均耗时：" + p2 / 3 + "ms");
        System.out.println("平台线程[800]平均耗时：" + p3 / 3 + "ms");
        System.out.println("平台线程[1000]平均耗时：" + p4 / 3 + "ms");
    }


    private static long testVirtualThread() {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < REQUEST_NUM; i++) {
            executorService.submit(PerformanceTest::handleRequest);
        }
        executorService.close();
        long useTime = System.currentTimeMillis() - startTime;
        System.out.println("虚拟线程耗时：" + useTime + "ms");
        return useTime;
    }


    private static long testPlatformThread(int poolSize) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < REQUEST_NUM; i++) {
            executorService.submit(PerformanceTest::handleRequest);
        }
        executorService.close();
        long useTime = System.currentTimeMillis() - startTime;
        System.out.printf("平台线程[%d]耗时：%dms\n", poolSize, useTime);
        return useTime;
    }


    private static void handleRequest() {
        try {
            // 模拟IO耗时
            // 虚拟线程接收到请求执行到这里的时候，就会释放掉JVM平台线程（内核线程），此举和响应式Reactor模型达到的效果是一样的能够让平台线程优先去处理其他的用户请求，但是虚拟线程显著降低了编码人员的心智负担
            // 普通的线程池到这里的时候，还是会阻塞执行睡300ms直接线程执行结束，期间没有释放掉JVM平台线程
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}