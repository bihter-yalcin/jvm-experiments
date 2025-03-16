
# 1- Hello JVM
1) Compile code with `mvn clean package`
2) Run the compiled class with 
`mvn exec:java -Dexec.mainClass="com.playground.jvmexperiments.hellojvm.HelloJVM"` command
3) See the bytecode instructions which JIT interpreted with using 
`javap -c HelloJVM.class > HelloJVM_bytecode.txt` command 
4) Lastly I relocate the txt file under the hellojvm to push the repo

# 2- Custom Loader Class

1) I wrote a loop that reads the raw bytes from a .class file and uses defineClass(...) to transform those bytes into a Class<?>. 
2) In the main method, I create an instance of MyClassLoader, then call:
loader.loadClass("com.playground.jvmexperiments.classloader.TestClass");
3) Next, I instantiate the newly loaded class and invoke its method via reflection.
This demonstrates how you can override the JVM’s usual class-loading process to load code from different sources—whether from a local file, remote server, or even an in-memory byte array.


# 3- Memory Stress Test

A simple Java experiment that continuously allocates large arrays to observe when the JVM runs out of heap space.

---

## Purpose

- **Explore** how the JVM allocates memory and what happens when the heap is exhausted.
- **Observe** the effect of different heap sizes on how many allocations can occur before failure.
- **Learn** how to pass custom `-Xmx` flags to tune the heap and prevent (or intentionally cause) `OutOfMemoryError`.

---

## Steps
- I wrote a while loop that continuously allocates large chunks of memory by creating int[] arrays and adding them to a list.
- Every 10 iterations, I printed a status message.
- Then, I ran commands with different heap size settings until I got an OutOfMemoryError.
- This experiment helped me see how far I could push each maximum heap size before running out of memory.

## Results

| **Heap Size** | **Command**                                                                                                            | **Allocated Blocks** | **Error Message**  |
|---------------|------------------------------------------------------------------------------------------------------------------------|----------------------|--------------------|
| Default       | `java -cp <br/>target/jvm-experiments-1.0-SNAPSHOT.jar com.playground.jvmexperiments.memorystress.MemoryTest`          | ~4600                | `OutOfMemoryError` |
| 512 MB        | `java -Xmx512m -cp <br/>target/jvm-experiments-1.0-SNAPSHOT.jar com.playground.jvmexperiments.memorystress.MemoryTest` | ~500                 | `OutOfMemoryError` |
| 200 MB        | `java -Xmx200m -cp <br/>target/jvm-experiments-1.0-SNAPSHOT.jar com.playground.jvmexperiments.memorystress.MemoryTest` | ~190                 | `OutOfMemoryError` |

# 4- GC Logs

A simple experiment to observe GC logs' frequencies and behaviours. You can see the whole logs under the gcLogs file.

---

## Results
| **GC Type** | **-Xmx** | **GC Trigger Points (Blocks)** | **Notes**                                                                                       
|-------------|----------|--------------------------------|------------------------------------------------------------------------------------------------|
| G1          | 512m     | 120, 500                       | Tends to perform larger collections at fewer intervals. <br/>Manages humongous objects (large arrays) with region-based cleanup. 
| G1          | 200m     | 80, 90, 190                    | With smaller heap, it triggers GC sooner and more frequently. <br/>Still handles large objects in “big chunks.”                  
| Parallel    | 512m     | 70                             | Multi-threaded collector, generally “quicker” GC events. <br/>Tends to collect earlier when it detects growing memory usage.      
| Serial      | 512m     | 70, 150, 220, 350              | Single-threaded, multiple stop-the-world phases. <br/>Performs smaller but more frequent GC cycles to keep up with allocations.  

# 5- JIT Demo

An experiment to observe JIT's interpretation performance on the code.

---
## Results
| **Mode**   | **Sum**               | **Time (ns)**    | **Interpretation**                                                                                      |
|------------|-----------------------|------------------|---------------------------------------------------------------------------------------------------------|
| `-Xint`    | 9999999900000000     | 1454390208       | **Fully interpreted.** <br/>Slowest execution due to no JIT optimizations.                              |
| `-Xmixed`  | 9999999900000000     | 54002291         | **Default (mixed) mode.** <br/>Fastest overall once JIT optimizations kick in.                          |
| `-Xcomp`   | 9999999900000000     | 108551750        | **Fully compiled mode.** <br/>Slower startup than mixed but still significantly faster than interpreted. |

# 6- Bytecode Manipulation

1) I explore how to modify existing classes at the bytecode level—injecting extra logic into methods without editing the original source code. 
2) Tools like Javassist, ASM, or Byte Buddy let you alter method bodies, add logging, or change return values at runtime. For example, I used Javassist to insert:
   System.out.println("Injected!");
