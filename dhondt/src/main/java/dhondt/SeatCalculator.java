package dhondt;


import java.util.*;

public class SeatCalculator {

    private final NavigableSet<Party> setOfParties;

    public SeatCalculator(Set<String> parties) {
        if (parties == null || parties.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.setOfParties = new TreeSet<>();
        parties.forEach(partyName -> this.setOfParties.add(new Party(partyName, 0L, 0)));
    }

    public void changeSeatsOfParties(Map<String, Long> votesPerParty, Map<String, Integer> mapToChange, int availableSeats) {
        Party highestScored = this.setOfParties.first();

        for (int seatsUsed = 0; !(seatsUsed == availableSeats); seatsUsed++) {
            this.setOfParties.forEach(p -> {
                if (!(votesPerParty.containsKey(p.getName()))) {
                    throw new IllegalArgumentException();
                }
                p.changingVotes = p.getVotes() / (p.getSeats() + 1.0);
            });

            for (Party party : this.setOfParties) {
                if (party.changingVotes >= highestScored.changingVotes) {
                    highestScored = party;
                }
            }

            highestScored.setSeats(highestScored.getSeats() + 1);
            mapToChange.put(highestScored.getName(), highestScored.getSeats());
        }
    }

    /**
     * Berechnet die Sitz-/Mandatsverteilung nach D'Hondt
     *
     * @param votesPerParty für jede Partei die Anzahl an abgegeben Stimmen für diese Partei, darf nicht leer sein
     * @param seats         Anzahl zu vergebender Sitze/Mandate, muss positiv sein
     * @return für jede Partei die Anzahl ihr zugeteilter Sitze/Mandate
     */
    public Map<String, Integer> calculate(Map<String, Long> votesPerParty, int seats) {
        if (seats <= 0 || votesPerParty.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (String partyName : votesPerParty.keySet()) {
            this.setOfParties.stream().filter(p -> p.getName().equals(partyName)).forEach(p -> p.setVotes(votesPerParty.get(partyName)));
        }

        Map<String, Integer> ret = new TreeMap<>();

        this.changeSeatsOfParties(votesPerParty, ret, seats);
        return ret;
    }
}
