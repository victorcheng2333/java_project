## Java 不同 GC 和 堆内存总结

### 1、串行 Serial GC 

特点：单线程Garbage Collector，存在Stop-The-World STW 现象

年轻代： 采用mark - copy 标记复制算法，堆内存区分为 Eden, Survivor区，Survivor 有分为两个同样大小的 S0, S1 区

老年代： mark-sweep-compact 标记-清除-整理算法

适用场景： Client 模式下的默认GC选项，-XX:+UseSerialGC



### 2、并行 Parallel GC

特点： 在jdk7,8 中 server模式下的默认GC选项，吞吐量优先

-XX:+UseParallelGC

XX:MaxGCPauseMillis=value

-XX:GCTimeRatio=N // GC时间和用户时间比例 = 1 / (N+1)



### 3、CMS GC

特点：避免在老年代垃圾收集出现长时间的卡顿，大部分工作和应用线程一起并发执行，其中在初始标记、最终标记两个阶段会出现STW停顿， 其他阶段与业务线程并行执行，缺点是堆内存碎片化



### 4、G1 GC

特点：兼顾吞吐量和停顿时间，Oracle JDK 9 之后的默认GC选项

堆内存划分为一个个的 Region, 默认2048 个。Region 之间是复制算法，整体上可以看做标记-整理算法，可以有效避免内存碎片，当Java堆非常大的时候，G1的优势明显

### 对比图
![gc.png](https://github.com/victorcheng2333/java_project/blob/master/images/gc.png)