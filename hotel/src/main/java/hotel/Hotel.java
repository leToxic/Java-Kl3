package hotel;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created: 17.02.2023 at 11:44
 *
 * @author Plasek Sebastian
 */

public class Hotel implements Comparable<Hotel> {
    private String name;
    private String location;
    private int size;
    private boolean smoking;
    private int rate;
    private LocalDate date;
    private String owner;

    public Hotel(String name, String location, int size, boolean smoking, double rate, LocalDate date, String owner) {
        setName(name);
        setLocation(location);
        setSize(size);
        setSmoking(smoking);
        setRate(rate);
        setDate(date);
        setOwner(owner);
    }

    public Hotel(byte[] data, Map<String, Short> columns) {

    }

    public Hotel() {}

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Error: Name cannot be null or empty!");
        }
        this.name = name;
    }

    public void setLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Error: Location cannot be null or empty!");
        }
        this.location = location;
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Error: Size must be greater than zero!");
        }
        this.size = size;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public void setRate(double rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException("Error: Rate must be greater than zero!");
        }
        this.rate = (int) rate * 100;
    }

    public void setDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Error: Date is invalid!");
        }
        this.date = date;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isSmoking() {
        return this.smoking;
    }

    public double getRate() {
        return this.rate / 100d;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getOwner() {
        return this.owner;
    }

    public static int getStartingOffset(String filename) {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            raf.seek(4);                // Ersten 4 Bytes von ID belegt
            int offset = raf.readInt();

            raf.close();
            return offset;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static Map<String, Short> readColumns(String filename) {
        Map<String, Short> map = new LinkedHashMap<>();

        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {

            raf.seek(8);                     // Nach Offset anfangen zu lesen
            short columnCount = raf.readShort();

            for (int i = 0; i < columnCount; i++) {
                short columnNameLength = raf.readShort();
                byte[] columnName = new byte[columnNameLength];

                for (int j = 0; j < columnNameLength; j++) {
                    columnName[j] = raf.readByte();
                }
                short columnValueLength = raf.readShort();
                map.put(new String(columnName), columnValueLength);
            }
            raf.close();
            return map;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static Set<Hotel> readHotels(String filename) {
        Set<Hotel> hotelSet = new TreeSet<>();

        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            Map<String, Short> map = readColumns(filename);

            Hotel hotel = new Hotel();

            StringBuilder sb = new StringBuilder();
            int hotelEntryLength = 0;

            for (String columnName : map.keySet()) {
                hotelEntryLength += map.get(columnName);
            }

            int offset = getStartingOffset(filename);
            raf.seek(offset);       // gelesen wird ab dem Offset

            short delete;

            while (raf.getFilePointer() != raf.length()) {
                delete = raf.readShort();

                if (!(delete == 0 || delete == -32768)) {
                    throw new IllegalArgumentException("Error: " + filename + " is invalid!");
                }
                if (delete == -32768) {
                    // Hotel gelöscht, wird übersprungen
                    raf.seek(raf.getFilePointer() + hotelEntryLength);
                } else {
                    int length = 0;
                    for (String columnName : map.keySet()) {

                        length = map.get(columnName);
                        sb.delete(0, sb.length());
                        byte[] bytes= new byte[length];

                        for (int i = 0; i < length; i++) {
                            bytes[i] = raf.readByte();
                            sb.append((char) bytes[i]);
                        }

                        switch (columnName) {
                            case "name" -> {hotel.setName(sb.toString().trim());}

                            case "location" -> {hotel.setLocation(sb.toString().trim());}

                            case "size" -> {hotel.setSize(Integer.parseInt(sb.toString().trim()));}

                            case "smoking" -> {hotel.setSmoking(sb.toString().equals("Y"));}

                            case "rate" -> {hotel.setRate(Double.parseDouble(sb.toString().replace("$", "").trim()) * 100);}

                            case "date" -> {
                                String[] splitted = sb.toString().trim().split("/");
                                hotel.setDate(LocalDate.of(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])));
                            }

                            case "owner" -> {hotel.setOwner(sb.toString().trim());}
                        }
                    }
                    hotelSet.add(hotel);
                    hotel = new Hotel();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return hotelSet;
    }

    @Override
    public int compareTo(Hotel o) {
        int ret = getLocation().compareTo(o.getLocation());

        if (ret == 0) {
            return getName().compareTo(o.getName());
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return size == hotel.size && smoking == hotel.smoking && Double.compare(hotel.rate, rate) == 0 && Objects.equals(hotel.name, name) && Objects.equals(hotel.location, location) &&
                Objects.equals(hotel.date, date) && Objects.equals(hotel.owner, owner);
    }

    @Override
    public String toString() {
        return String.format("Hotel: name: %s; location: %s; size: %d; smoking: %b; rate: %.2f; date: %s; owner: %s"
                , getName(), getLocation(), getSize(), isSmoking(), getRate(), getDate().toString(), getOwner());
    }
}