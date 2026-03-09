package Day1_09_03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Creating100files {

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(5);

        Map<Integer,String> map = new HashMap<Integer,String>();

        AtomicInteger atomicInteger = new AtomicInteger(1);

        map.put(1, "Java is a powerful programming language.\nIt is widely used for backend development.");
        map.put(2, "Multithreading allows multiple parts of a program to run concurrently.\nThis improves CPU utilization and performance.\nHowever, proper synchronization is necessary.");
        map.put(3,  "Streams in Java provide a modern way to process collections.\n" +
                "They support operations like map, filter, and reduce.\n" +
                "Streams encourage functional programming.\n" +
                "They help write cleaner and more readable code.\n" +
                "Parallel streams can improve performance in some cases.");
        map.put(4, "A comparator is used to define custom sorting logic.\n" +
                "It is part of the java.util package.\n" +
                "Comparators allow sorting objects based on different attributes.");

        map.put(5, "Synchronization in Java prevents multiple threads from accessing shared resources simultaneously.\n" +
                "The synchronized keyword provides intrinsic locking.\n" +
                "Locks ensure thread safety but may reduce performance if overused.\n" +
                "Careful design is required when building concurrent systems.");


        String directoryPath = "C:\\Users\\MyakamAnirudh\\Desktop\\DailyAssignments\\src\\Day1_09_03\\myDir";

        for (int i = 1; i <= 131; i++) {

            File file = new File(directoryPath + "/file" + i + ".txt");

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(file)
                    );
            int idx = ThreadLocalRandom.current().nextInt(1, 6);
            writer.write(map.get(idx));

            writer.close();
            System.out.println("file"+i+" created successfully");

        }

        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + " ms");

    }
}
