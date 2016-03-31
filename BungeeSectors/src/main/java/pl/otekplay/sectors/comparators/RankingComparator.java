package pl.otekplay.sectors.comparators;

import pl.otekplay.sectors.data.Ranking;

import java.util.Comparator;

public class RankingComparator implements Comparator<Ranking> {

    public int compare(Ranking g0, Ranking g1) {
        Integer p0 = g0.getPoints();
        Integer p1 = g1.getPoints();
        return p1.compareTo(p0);
    }
}