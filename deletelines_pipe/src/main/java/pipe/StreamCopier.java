package pipe;

import java.io.*;

public class StreamCopier {

    public static void copyByte(InputStream source, OutputStream output) throws IOException {
        int data;
        while ((data = source.read()) != -1)
            output.write(data);
    }

    public static void copyBuffered(InputStream source, OutputStream output, int size) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[size];
        while ((bytesRead = source.read(buffer)) != -1)
            output.write(buffer, 0, bytesRead);
    }

}


