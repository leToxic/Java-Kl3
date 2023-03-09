import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created: 09.03.2023 at 11:40
 *
 * @author Plasek Sebastian
 */
public class PlayerUtil {

    public static List<Player> readFromFile(String filename) throws IOException {
        List<Player> ret = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            dis.skip(4);
            int offset = dis.readInt();
            dis.skip(1337);
            int playerCount = dis.readShort();
            System.out.println(offset);


            for (int i = 0; i < playerCount; i++) {
                byte[] arr = new byte[dis.readShort()];
                dis.read(arr);
                Player p = new Player(arr);
                ret.add(p);
            }
        }
        return ret;
    }

    public static void changePlayerInfos(List<Player> players) {
        for (Player player : players) {
            switch (player.getAlias()) {
                case "HunterKiller11111elf" -> {
                    player.setSpm(15000);
                    player.setWins(1337);
                    player.setLoses(0);
                }
                case "CyberBob" -> {
                    player.setSpm(2);
                    player.setWins(1);
                    player.setLoses(354);
                }
                case "ShadowDeath42" -> {
                    player.setSpm(3);
                    player.setWins(0);
                    player.setLoses(400);
                }
            }
        }
    }

    public static void writeToNewData(String filename, List<Player> players) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeInt(17);
            dos.writeInt(1347);
            dos.write(new byte[1337]);
            dos.writeShort(players.size());

            for (Player player : players) {
                byte[] app = player.getBytes();
                dos.writeShort(app.length);
                fos.write(app);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Player> out = readFromFile("pathOfSurvival/src/main/resources/playerdata_new.bin").stream().sorted().collect(Collectors.toList());

        for (Player player : out) {
            if (player.getAlias().equals("HunterKiller11111elf") || player.getAlias().equals("CyberBob") || player.getAlias().equals("ShadowDeath42")) {
                System.out.println(player);
            }
        }
        changePlayerInfos(out);

        writeToNewData("pathOfSurvival/src/main/resources/output.bin", out);
        List<Player> newOut = readFromFile("pathOfSurvival/src/main/resources/output.bin");

        for (Player player : newOut) {
            if (player.getAlias().equals("HunterKiller11111elf") || player.getAlias().equals("CyberBob") || player.getAlias().equals("ShadowDeath42")) {
                System.out.println(player);
            }
        }
    }
}
