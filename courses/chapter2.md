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


## HttpClient 示例程序
```java
package http_client_demo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
public class HttpClientDemo {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("http://127.0.0.1:8081");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
}

```