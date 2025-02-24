package com.playground.jvmexperiments.memorystress;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest {

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        int i = 0;

        try {
            while (true) {
                // ~1MB allocated each iteration
                int[] memoryBlock = new int[256_000];
                list.add(memoryBlock);
                i++;

                if (i % 10 == 0) {
                    System.out.println("Allocated blocks: " + i);
                }

                Thread.sleep(50);
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Ran out of memory after " + i + " allocations !");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

//First experiment
/*Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at com.playground.jvmexperiments.memorystress.MemoryTest.main(MemoryTest.java:26)
        Allocated blocks: 4600

*/

//I made the heap size 512Mb
/*
Allocated blocks: 500
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at com.playground.jvmexperiments.memorystress.MemoryTest.main(MemoryTest.java:26)

 */

//I made the heap size: 200
/*
Allocated blocks: 190
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at com.playground.jvmexperiments.memorystress.MemoryTest.main(MemoryTest.java:26)

 */
