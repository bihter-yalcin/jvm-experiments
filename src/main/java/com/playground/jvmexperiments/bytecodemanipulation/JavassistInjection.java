package com.playground.jvmexperiments.bytecodemanipulation;

import com.playground.jvmexperiments.classloader.MyClassLoader;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class JavassistInjection {
    public static void main(String[] args) throws Exception {
        ClassPool repoOfClasses = ClassPool.getDefault();
        CtClass classWillBeManipulate = repoOfClasses.get("com.playground.jvmexperiments.bytecodemanipulation.ClassWillBeManipulate");
        CtMethod method = classWillBeManipulate.getDeclaredMethod("sayHello");
        method.insertBefore("{ System.out.println(\"Injected!\"); }");

        byte[] modifiedBytes = classWillBeManipulate.toBytecode();

        MyClassLoader loader = new MyClassLoader();
        Class<?> modifiedClass = loader.defineClass(
                "com.playground.jvmexperiments.bytecodemanipulation.ClassWillBeManipulate",
                modifiedBytes
        );
        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
        modifiedClass.getMethod("sayHello").invoke(instance);
    }

}