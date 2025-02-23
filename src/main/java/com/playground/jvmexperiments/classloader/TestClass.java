package com.playground.jvmexperiments.classloader;

/***
 * Normally, the application class loader would load this class from target/classes folder.
 * Instead, I’ll let my custom loader do it.
 */
public class TestClass {
    public void greet(){
        System.out.println("Hello from test class !");
    }
}
