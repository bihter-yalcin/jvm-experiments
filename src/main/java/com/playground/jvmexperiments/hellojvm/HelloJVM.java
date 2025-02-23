package com.playground.jvmexperiments.hellojvm;

//To understand how Java code is compiled into .class files and how to inspect the resulting bytecode.
/***
 * - Compile code with mvn clean package
 * - Run the compiled class with mvn exec:java -Dexec.mainClass="com.playground.jvmexperiments.hellojvm.HelloJVM" command
 * - See the bytecode instructions which JIT interpreted with using javap -c HelloJVM.class > HelloJVM_bytecode.txt command
 * - I relocate the txt file under the hellojvm to push the repo
***/
public class HelloJVM {
    public static void main(String[] args){
        System.out.println("Hello JVM !");
    }
}
