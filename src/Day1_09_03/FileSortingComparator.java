package Day1_09_03;

import java.io.File;
import java.util.Comparator;

public class FileSortingComparator implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        int dateCompare = Long.compare(o2.lastModified(), o1.lastModified());

        if (dateCompare != 0) {
            return dateCompare;
        }

        return o1.getName().compareTo(o2.getName());
    }
}
