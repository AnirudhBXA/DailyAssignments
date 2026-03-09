package Day1_09_03;

import java.io.*;
import java.util.Arrays;

public class SingleThreadImplementation {

    public static void main(String[] args) {

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
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .toArray(File[]::new);

        for (File file : files) {

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
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
