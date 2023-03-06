package deleteLines;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created: 02.03.2023 at 11:24
 *
 * @author Plasek Sebastian
 */
public class AsciiInputStream extends FileInputStream {
    public AsciiInputStream(String name) throws FileNotFoundException {
        super(name);
    }

    public String readLine() throws IOException {
        int c;
        StringBuilder ret = new StringBuilder();
        while ((c = this.read()) > 0) {
            if (c == '\r' && this.read() == '\n') {
                break;
            }
            ret.append((char) c);
        }
        if (ret.length() == 0) {
            return null;
        }
        return ret.toString();
    }


}
