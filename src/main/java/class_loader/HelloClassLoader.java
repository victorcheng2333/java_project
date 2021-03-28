package class_loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

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
