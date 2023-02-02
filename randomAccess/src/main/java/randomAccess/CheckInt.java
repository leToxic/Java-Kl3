package randomAccess;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created: 26.01.2023 at 11:27
 *
 * @author Plasek Sebastian
 */
public class CheckInt {
    public static void createFile(String filename, double... values) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            file.setLength(0);
            file.writeInt(values.length);
            for (double value : values) {
                file.writeDouble(value);
            }
        }
    }

    public static boolean isValidFile(String filename) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            return (long) file.readInt() * Double.BYTES == file.length() - Integer.BYTES;
        }
    }

    public static String getFileInfo(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            if (!isValidFile(filename)) {
                sb.append("invalid");
                return sb.toString();
            }

            sb.append("Saved values: ").append(file.readInt()).append("\n");
            while (file.length() != file.getFilePointer()) {
                sb.append(String.format("%.2f", file.readDouble())).append(" ");
            }

            sb.replace(sb.length() - 1, sb.length(), "");
        }
        return sb.toString();
    }

    public static void append(String filename, double toAppend) throws IOException {
        if (!isValidFile(filename)) {
            throw new IllegalArgumentException(filename);
        }

        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            int app = file.readInt() + 1;
            file.seek(0);
            file.writeInt(app);

            file.seek(file.length());
            file.writeDouble(toAppend);
        }
    }
}
