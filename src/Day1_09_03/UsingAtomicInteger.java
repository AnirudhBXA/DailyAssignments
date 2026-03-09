package Day1_09_03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UsingAtomicInteger {
    private static final Object LOCK = new Object();

    static AtomicInteger totalLines = new AtomicInteger(0);
    static AtomicInteger totalWords = new AtomicInteger(0);
    static AtomicInteger totalFiles = new AtomicInteger(0);

    private static void readingSingleFile(File file){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            int lineCount = 0;
            int wordCount = 0;

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                lineCount++;
                String[] words = line.split(" ");
                wordCount += words.length;
            }

            System.out.println("Lines : " + lineCount + "\n" + "Words : " + wordCount);

            totalLines.addAndGet(lineCount);
            totalWords.addAndGet(wordCount);
            totalFiles.incrementAndGet();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();


        File directory = new File("C:\\Users\\MyakamAnirudh\\Desktop\\DailyAssignments\\src\\Day1_09_03\\myDir");

        File[] files = directory.listFiles();

        if (files == null) {
            System.out.println("The directory is empty");
            return;
        }

        files = Arrays.stream(files)
                .filter( (file) -> {
                    return file.getName().endsWith(".txt");
                })
                .sorted(new FileSortingComparator())
                .toArray(File[]::new);



        for(int i = 0; i < files.length ; i+=100){

            CountDownLatch countDownLatch;
            ExecutorService executor = Executors.newFixedThreadPool(4);

            File[] batch = Arrays.stream(files).skip(i).limit(100).toArray(File[]::new);

            countDownLatch = new CountDownLatch(batch.length);
            for (File file : batch) {
                executor.submit(() -> {
                    readingSingleFile(file);
                    countDownLatch.countDown();
                });
            }

            executor.shutdown();
            countDownLatch.await();
        }

        System.out.println("----------------\n    Summary\n----------------");
        System.out.println("Total Lines : " + totalLines.get()
                + "\n" + "Total Words : " + totalWords.get()
                + "\n" + "Total Files : " + totalFiles.get()
        );

        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
