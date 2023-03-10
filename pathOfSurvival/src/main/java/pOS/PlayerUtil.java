package pOS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created: 09.03.2023 at 11:40
 *
 * @author Plasek Sebastian
 */
public class PlayerUtil {

    private List<Player> players;

    public PlayerUtil(String filename) throws IOException {
        setPlayers(readFromFile(filename));
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

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

    public void findPlayer(String alias, int newSPM, int newWins, int newLoses) {
        for (Player player : this.players) {
            if (player.getAlias().equals(alias)) {
                player.setSpm(newSPM);
                player.setWins(newWins);
                player.setLoses(newLoses);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void writeToNewData(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeInt(new Random().nextInt(0, 100));
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
        PlayerUtil pu = new PlayerUtil("pathOfSurvival/src/main/resources/playerdata_new.bin");
        List<Player> out = pu.getPlayers();

        for (Player player : out) {
            System.out.println(player);
        }

        for (Player player : out) {
            if (player.getAlias().equals("HunterKiller11111elf") || player.getAlias().equals("CyberBob") || player.getAlias().equals("ShadowDeath42")) {
                System.out.println(player);
            }
        }
        pu.findPlayer("HunterKiller11111elf", 15000, 1337, 0);
        pu.findPlayer("CyberBob", 2, 1, 354);
        pu.findPlayer("ShadowDeath42", 3, 0, 400);

        pu.writeToNewData("pathOfSurvival/src/main/resources/output.bin");
        List<Player> newOut = readFromFile("pathOfSurvival/src/main/resources/output.bin").stream().sorted().toList();

        for (Player player : newOut) {
            if (player.getAlias().equals("HunterKiller11111elf") || player.getAlias().equals("CyberBob") || player.getAlias().equals("ShadowDeath42")) {
                System.out.println(player);
            }
        }
    }
}
