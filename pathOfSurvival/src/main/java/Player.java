import java.io.*;

/**
 * Created: 09.03.2023 at 11:29
 *
 * @author Plasek Sebastian
 */
public class Player implements Comparable<Player>, Serializable {
    private String alias;
    private Integer year_lastMatch;
    private Integer month_lastMatch;
    private Integer day_LastMatch;
    private Integer spm;
    private Integer wins;
    private Integer loses;

    public Player(byte[] arr) throws IOException {
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(arr))) {
            setAlias(dis.readUTF());
            setYear_lastMatch(dis.readInt());
            setMonth_lastMatch(dis.readInt());
            setDay_LastMatch(dis.readInt());
            setSpm(dis.readInt());
            setWins(dis.readInt());
            setLoses(dis.readInt());
        }
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(alias);
        dos.writeInt(year_lastMatch);
        dos.writeInt(month_lastMatch);
        dos.writeInt(day_LastMatch);
        dos.writeInt(spm);
        dos.writeInt(wins);
        dos.writeInt(loses);
        return baos.toByteArray();


    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getYear_lastMatch() {
        return year_lastMatch;
    }

    public void setYear_lastMatch(int year_lastMatch) {
        this.year_lastMatch = year_lastMatch;
    }

    public int getMonth_lastMatch() {
        return month_lastMatch;
    }

    public void setMonth_lastMatch(int month_lastMatch) {
        this.month_lastMatch = month_lastMatch;
    }

    public int getDay_LastMatch() {
        return day_LastMatch;
    }

    public void setDay_LastMatch(int day_LastMatch) {
        this.day_LastMatch = day_LastMatch;
    }

    public int getSpm() {
        return spm;
    }

    public void setSpm(int spm) {
        this.spm = spm;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    @Override
    public int compareTo(Player o) {
        return o.spm - this.spm;
    }

    @Override
    public String toString() {
        return "Player{" +
                "alias='" + alias + '\'' +
                ", year_lastMatch=" + year_lastMatch +
                ", month_lastMatch=" + month_lastMatch +
                ", day_LastMatch=" + day_LastMatch +
                ", spm=" + spm +
                ", wins=" + wins +
                ", loses=" + loses +
                '}';
    }
}
