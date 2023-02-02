package dhondt;

/**
 * Created: 19.01.2023 at 11:44
 *
 * @author Plasek Sebastian
 */
public class Party implements Comparable<Party> {
    private String name;
    private Integer seats;
    private Long votes;

    public Double changingVotes;

    public Party(String name, Long votes, Integer seats) {
        setSeats(seats);
        setVotes(votes);
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeats(Integer seats) {
        if (seats < 0) {
            throw new IllegalArgumentException("Seats negativ");
        }
        this.seats = seats;
    }

    public void setVotes(Long votes) {
        if (votes < 0) {
            throw new IllegalArgumentException("Votes negativ");
        }
        this.votes = votes;
        this.changingVotes = this.votes.doubleValue();
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public Long getVotes() {
        return votes;
    }


    @Override
    public int compareTo(Party o) {
        if (this == o) {
            return 0;
        }
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Party party = (Party) o;

        return name.equals(party.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
