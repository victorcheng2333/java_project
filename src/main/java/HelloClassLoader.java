import java.util.Base64;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception{
        new HelloClassLoader().findClass("jvm.Hello").newInstance();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String helloBase64 = "yv66vgAAADsAHAoAAgADBwAEDAAFAAYBABBqYXZhL2xhbmcvT2JqZWN0AQAGPGluaXQ+AQADKClWCQAIAAkHAAoMAAsADAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsIAA4BAAtoZWxsbyB3b3JsZAoAEAARBwASDAATABQBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgcAFgEACWp2bS9IZWxsbwEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhACEAFQACAAAAAAACAAEABQAGAAEAFwAAAB0AAQABAAAABSq3AAGxAAAAAQAYAAAABgABAAAAAgAIABkABgABABcAAAAlAAIAAAAAAAmyAAcSDbYAD7EAAAABABgAAAAKAAIAAAAEAAgABQABABoAAAACABs=";
        byte[] bytes = decode(helloBase64);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
