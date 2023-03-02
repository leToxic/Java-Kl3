package pipe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created: 02.03.2023 at 12:17
 *
 * @author Plasek Sebastian
 */
public class Pipe {
    public static void main(String[] args) throws IOException {
        try (PipedOutputStream pos = new PipedOutputStream();
             PipedInputStream pis = new PipedInputStream(pos)) {

            StreamCopier.copyByte(System.in, pos);
            pos.flush();
            pos.close();

            StreamCopier.copyBuffered(pis, new FileOutputStream("deletelines_pipe/src/main/resources/outputpipe.txt"), 4);
        }
    }
}
