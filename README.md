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


