package com.playground.jvmexperiments.classloader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/***
 Purpose:
 This class extends ClassLoader and overrides findClass(String name) to define how we read the bytes for our class.
 */
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //read raw bytes from class file
            byte[] classData = loadClassData(name);
            //bytes to class object
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }

    }

    /*Loads the .class file bytes from the given path
     exp: "com.playground.jvmexperiments.classloader.TestClass" will be mapped to path
     "target/classes/com/playground/jvmexperiments/classloader/TestClass.class".*/
    private byte[] loadClassData(String className) throws IOException {
        String path = "target/classes/" + className.replace('.', '/') + ".class";
        return Files.readAllBytes(Paths.get(path));
    }

    public static void main(String[] args) throws Exception{
        MyClassLoader loader = new MyClassLoader();

        Class<?> testClass = loader.loadClass("com.playground.jvmexperiments.classloader.TestClass");

        Object instance = testClass.getDeclaredConstructor().newInstance();

        Method greetMethod = testClass.getMethod("greet");
        greetMethod.invoke(instance);
    }
}
