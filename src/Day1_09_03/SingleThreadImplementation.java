package Day1_09_03;

import java.io.*;
import java.util.Arrays;

public class SingleThreadImplementation {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        int totalLines = 0;
        int totalWords = 0;
        int totalFiles = 0;

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
            File[] batch = Arrays.stream(files).skip(i).limit(100).toArray(File[]::new);

            for (File file : batch) {

                System.out.println( "File : " + file.getName());

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

                    totalLines += lineCount;
                    totalWords += wordCount;
                    totalFiles++;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("----------------\n    Summary\n----------------");
        System.out.println("Total Lines : " + totalLines
                + "\n" + "Total Words : " + totalWords
                + "\n" + "Total Files : " + totalFiles
        );

        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
