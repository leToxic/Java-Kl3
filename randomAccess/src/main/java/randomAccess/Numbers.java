package randomAccess;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created: 26.01.2023 at 12:06
 *
 * @author Plasek Sebastian
 */
public class Numbers {

    public static List<Number> getContents(String filename) throws IOException {
        List<Number> ret = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            while (file.length() != file.getFilePointer()) {
                try {
                    if (file.readByte() == 0) {
                        ret.add(file.readInt());
                    } else {
                        ret.add(file.readDouble());
                    }
                } catch (EOFException eof) {
                    throw new IllegalArgumentException(filename);
                }
            }
        }
        return ret;
    }

    public static Map<String, Set<Number>> groupByType(List<? extends Number> numbers) {
        Map<String, Set<Number>> ret = new TreeMap<>();
        numbers.forEach(number -> {
            String name = number.getClass().getSimpleName();
            Set<Number> app = ret.getOrDefault(name, new HashSet<>());
            app.add(number);
            ret.put(name, app);
        });

        //for (Number number : numbers) {
        //    Set<Number> app = ret.getOrDefault(number.getClass().getSimpleName(), new HashSet<>());
        //    app.add(number);
        //    ret.put(number.getClass().getSimpleName(), app);
        //}
        return ret;
    }

    public static void createFile(String filename, List<? extends Number> numbers) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            for (Number number : numbers) {
                if (number instanceof Double) {
                    file.writeByte(1);
                    file.writeDouble((Double) number);
                } else if (number instanceof Integer) {
                    file.writeByte(0);
                    file.writeInt((Integer) number);
                } else {
                    throw new IllegalArgumentException("Nicht verfügbarer Datentyp: " + number.getClass());
                }
            }
        }
    }
}

