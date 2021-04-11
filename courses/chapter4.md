#### 2.在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程
```java
package conc;

import java.util.concurrent.*;

public class MultiThread {

    public static void main(String[] args) throws Exception {
        func1();
        func2();
        func3();
        func4();
        func5();
    }

    /**
     * func1: thread join 等待
     */
    static void func1() throws InterruptedException {
        var myCal = new MyCal();
        var thread = new Thread(myCal);
        thread.start();
        thread.join();
        System.out.println(myCal.getValue());
    }

    /**
     * func2: 主线程 sleep
     */
    static void func2() throws InterruptedException {
        var myCal = new MyCal();
        var thread = new Thread(myCal);
        thread.start();
        Thread.sleep(100);
        System.out.println(myCal.getValue());
    }

    /**
     * func3: FutureTask get 等待
     */
    static void func3() throws ExecutionException, InterruptedException {
        var futureTask = new FutureTask<>(Util::sum);
        var thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }

    /**
     * func4: ThreadPoolExecutor submit Callable 方法
     */
    static void func4() throws ExecutionException, InterruptedException{
        var service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var future = service.submit(() -> Util.sum());
        System.out.println(future.get());
    }

    /**
     * func5: countDownLatch
     */
    static void func5() throws InterruptedException {
        var count = new CountDownLatch(1);
        var myCal = new MyCalWithCountDownLatch(count);
        var thread = new Thread(myCal);
        thread.start();
        count.await();
        System.out.println(myCal.getValue());
    }

}

class MyCal implements Runnable {
    // 返回值
    private int returnValue;

    @Override
    public void run() {
        returnValue = Util.sum();
    }
    public int getValue() {
        return returnValue;
    }
}

class MyCalWithCountDownLatch implements Runnable {
    // 返回值
    private int returnValue;
    private CountDownLatch countDownLatch;

    MyCalWithCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        returnValue = Util.sum();
        countDownLatch.countDown();
    }
    public int getValue() {
        return returnValue;
    }
}

class Util {
    public static int sum() {
        return fib(36);
    }
    private static int fib(int a) {
        if ( a < 2)
            return 1;
        return fib(a-1) + fib(a-2);
    }
}
```
#### 多线程和并发脑图
![conc.png](https://github.com/victorcheng2333/java_project/blob/master/images/conc.png)
