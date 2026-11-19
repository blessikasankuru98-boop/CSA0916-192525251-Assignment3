
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.*;

public class ResultProcessor {

    static String processFile(Path file) {
        try {
            String data = Files.readString(file).trim();

            if (data.isEmpty() || !data.matches("\\d+")) {
                throw new IllegalArgumentException("Invalid score");
            }

            int score = Integer.parseInt(data);
            return "SUCCESS: " + file.getFileName()
                   + " Score: " + score;

        } catch (Exception e) {
            return "FAILED: " + file.getFileName()
                   + " Reason: " + e.getMessage();
        }
    }

    public static void main(String[] args)
            throws Exception {

        Path folder = Paths.get("scores");
        Files.createDirectories(folder);

        for (int i = 1; i <= 10000; i++) {
            Path file = folder.resolve("student" + i + ".txt");

            if (i % 2000 == 0) {
                Files.deleteIfExists(file);
            } else if (i % 2000 == 1) {
                Files.writeString(file, "INVALID DATA");
            } else {
                Files.writeString(file, "85");
            }
        }

        int[] poolSizes = {1, 2, 4, 8};

        for (int size : poolSizes) {
            ExecutorService pool =
                Executors.newFixedThreadPool(size);

            long start = System.currentTimeMillis();

            List<Future<String>> results = new ArrayList<>();

            try (var files = Files.list(folder)) {
                files.forEach(file ->
                    results.add(pool.submit(
                        () -> processFile(file))));
            }

            int success = 0;
            int failed = 0;

            for (Future<String> result : results) {
                String message = result.get();

                if (message.startsWith("SUCCESS"))
                    success++;
                else
                    failed++;
            }

            pool.shutdown();

            long end = System.currentTimeMillis();

            System.out.println("Threads: " + size);
            System.out.println("Success: " + success);
            System.out.println("Failed: " + failed);
            System.out.println("Time: " + (end - start) + " ms");
            System.out.println("--------------------");
        }
    }
}
