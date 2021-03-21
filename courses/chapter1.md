### 1、自定义 ClassLoader:
https://github.com/victorcheng2333/java_project/blob/master/src/main/java/HelloClassLoader.java
```java
public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception{
        Class<?> helloClass = new HelloClassLoader().findClass("Hello");
        Object helloObj = helloClass.newInstance();
        Method helloMethod = helloClass.getMethod("hello");
        helloMethod.invoke(helloObj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = "Hello.xlass";
        StringBuilder build = new StringBuilder();
        byte[] decodedBytes = new byte[1];
        try {
            ClassLoader classLoader = HelloClassLoader.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            byte[] bytes = Files.readAllBytes(file.toPath());
            decodedBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                byte decodedByte = (byte)(255 - b);
                decodedBytes[i] = decodedByte;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, decodedBytes, 0, decodedBytes.length);
    }
}
```


### 2、内存关系图
![java_memory.png](https://github.com/victorcheng2333/java_project/blob/master/images/java_memory.png)

