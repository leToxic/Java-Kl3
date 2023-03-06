package deleteLines;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created: 02.03.2023 at 11:21
 *
 * @author Plasek Sebastian
 */
public class DeleteLines {

    public static void deleteLines(String input, String output, List<Integer> lines) {
        String pathToRes = "deletelines_pipe/src/main/resources/";
        try (AsciiInputStream ais = new AsciiInputStream(pathToRes + input);
             PrintWriter pw = new PrintWriter(pathToRes + output)) {
            int counter = 0;
            String s;
            while ((s = ais.readLine()) != null) {
                if (!lines.contains(counter)) {
                    pw.println(s);
                }
                counter++;
            }
        } catch (IOException fnf) {
            System.out.println("Fehler: " + fnf);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> toDelete = new ArrayList<>(List.of(Arrays.copyOfRange(args, 2, args.length)));
        ArrayList<Integer> toDeleteLines = new ArrayList<>();

        for (String s : toDelete) {
            if (s.contains("-")) {
                String[] app = s.split("-");
                for (int i = Integer.parseInt(app[0]); i <= Integer.parseInt(app[1]); i++) {
                    toDeleteLines.add(i);
                }
            } else {
                toDeleteLines.add(Integer.parseInt(s));
            }
        }
        deleteLines(args[0], args[1], toDeleteLines);
    }
}
