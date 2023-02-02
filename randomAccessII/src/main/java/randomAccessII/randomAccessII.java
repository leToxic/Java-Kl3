package randomAccessII;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created: 02.02.2023 at 11:23
 *
 * @author Plasek Sebastian
 */
public class randomAccessII {
    public static void createFile(String from, String to) throws IOException {
        try (RandomAccessFile fileFrom = new RandomAccessFile(from, "r")) {
            try (RandomAccessFile fileTo = new RandomAccessFile(to, "rw")) {
                while (fileFrom.getFilePointer() != fileFrom.length()) {
                    String read = fileFrom.readLine();
                    if (read == null) {
                        break;
                    }

                    try {
                        int i = Integer.parseInt(read);
                        fileTo.writeChar('i');
                        fileTo.writeInt(i);
                        continue;
                    } catch (NumberFormatException ignored) {
                    }

                    try {
                        double d = Double.parseDouble(read);
                        fileTo.writeChar('d');
                        fileTo.writeDouble(d);
                        continue;
                    } catch (NumberFormatException ignored) {
                    }

                    if (read.trim().equals("true") || read.trim().equals("false")) {
                        boolean b = read.trim().equals("true");
                        fileTo.writeChar(b ? 't' : 'f');
                    }
                }
            }
        }
    }

    public static Map<String, Queue<Object>> getMap(String filename, List<Character> order) throws IOException {
        Map<String, Queue<Object>> ret = new HashMap<>();
        ret.put("Integer", new LinkedList<>());
        ret.put("Double", new LinkedList<>());
        ret.put("Boolean", new LinkedList<>());

        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            while (true) {
                try {
                    char ch = raf.readChar();
                    switch (ch) {
                        case 'i' -> {
                            ret.get("Integer").offer(raf.readInt());
                            order.add('i');
                        }
                        case 'd' -> {
                            ret.get("Double").offer(raf.readDouble());
                            order.add('d');
                        }

                        case 't', 'f' -> {
                            if (ch == 't') {
                                ret.get("Boolean").offer(true);
                                order.add('t');
                            } else {
                                ret.get("Boolean").offer(false);
                                order.add('f');
                            }
                        }

                    }

                } catch (EOFException eof) {
                    break;
                }
            }
        }
        return ret;
    }

    public static void makeUnsinnloseDatei(String filename, Map<String, Queue<Object>> map, List<Character> order) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            for (Character ch : order) {
                try {
                    switch (ch) {
                        case 'i' -> raf.writeBytes(map.get("Integer").remove().toString() + "\n");
                        case 'd' -> raf.writeBytes(map.get("Double").remove().toString() + "\n");
                        case 't', 'f' -> raf.writeBytes(map.get("Boolean").remove().toString() + "\n");
                    }

                } catch (Exception ignored) {
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        createFile("randomAccessII/src/main/resources/read.txt", "randomAccessII/src/main/resources/write.txt");
        List<Character> uebergabe = new ArrayList<>();
        Map<String, Queue<Object>> leseAus = getMap("randomAccessII/src/main/resources/write.txt", uebergabe);

        for (String s : leseAus.keySet()) {
            System.out.println(leseAus.get(s));
        }

        System.out.println(uebergabe);

        makeUnsinnloseDatei("randomAccessII/src/main/resources/writeSinnvoll.txt", leseAus, uebergabe);
    }
}

