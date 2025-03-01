package com.playground.jvmexperiments.jitDemo;

public class JitDemo {
    public static void main(String[] args) {
        long start = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < 100_000_000; i++) {
            sum += compute(i);
        }
        long end = System.nanoTime();
        System.out.println("Sum: " + sum);
        System.out.println("Time (ns): " + (end - start));
    }

    private static int compute(int x) {
        return x * 2;
    }
}
