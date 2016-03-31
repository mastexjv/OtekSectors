package pl.otekplay.sectors.comparators;

import pl.otekplay.sectors.data.GuildRanking;

import java.util.Comparator;

public class GuildComparator implements Comparator<GuildRanking> {

    public int compare(GuildRanking g0, GuildRanking g1) {
        Integer p0 = g0.getPoints();
        Integer p1 = g1.getPoints();
        return p1.compareTo(p0);
    }
}