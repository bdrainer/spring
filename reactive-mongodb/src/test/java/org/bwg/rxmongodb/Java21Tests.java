package org.bwg.rxmongodb;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Java21Tests {

    @Test
    void testVirtualThreads() {
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                System.out.println("Thread " + i + " sleeping for 1 second");
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
            // Wait for all tasks to complete.
        }
        System.out.println("Time to complete: " + (System.currentTimeMillis() - start) / 1000.0D);
    }
}
