package Day1_09_03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadImplementation {

    private static final Object LOCK = new Object();

    static int totalLines;
    static int totalWords;
    static int totalFiles;

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

            synchronized (LOCK) {
                totalLines += lineCount;
                totalWords += wordCount;
                totalFiles++;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(4);

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
                .limit(100)
                .toArray(File[]::new);

        for (File file : files) {
            executor.submit(() -> {
                readingSingleFile(file);
            });
        }

        executor.shutdown();
        if(executor.awaitTermination(10, TimeUnit.MINUTES)){
            System.out.println("----------------\n    Summary\n----------------");
            System.out.println("Total Lines : " + totalLines
                    + "\n" + "Total Words : " + totalWords
                    + "\n" + "Total Files : " + totalFiles
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
