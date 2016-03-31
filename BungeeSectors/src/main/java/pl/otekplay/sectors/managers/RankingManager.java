package pl.otekplay.sectors.managers;

import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import pl.otekplay.sectors.comparators.GuildComparator;
import pl.otekplay.sectors.comparators.RankingComparator;
import pl.otekplay.sectors.data.*;

import java.util.LinkedList;
import java.util.List;

public class RankingManager {
    @Getter
    private static List<Ranking> rankings = new LinkedList<>();
    @Getter
    private static List<GuildRanking> guildRankings = new LinkedList<>();


    public static void addRanking(Ranking ranking) {
        rankings.add(ranking);
        sortUserRankings();
    }

    public static void addRanking(GuildRanking ranking) {
        guildRankings.add(ranking);
        sortGuildRankings();
    }

    public static void removeRanking(Ranking ranking) {
        rankings.remove(ranking);
        sortUserRankings();
    }

    public static void removeRanking(GuildRanking ranking) {
        guildRankings.remove(ranking);
        sortGuildRankings();
    }

    public static void sortUserRankings() {
        rankings.sort(new RankingComparator());
    }

    public static void sortGuildRankings() {
        guildRankings.sort(new GuildComparator());
    }

    public static int getPlaceUser(User user) {
        for (int num = 0; num < rankings.size(); num++) {
            if (rankings.get(num).equals(user.getRanking())) {
                return num + 1;
            }
        }
        return 0;
    }

    public static int getPlaceGuild(Guild guild) {
        for (int num = 0; num < rankings.size(); num++) {
            if (guildRankings.get(num).equals(guild.getRanking())) {
                return num + 1;
            }
        }
        return 0;
    }

    public static void calculateRanking(User user) {
        double deadpoints = user.getRanking().getPoints();
        double killpoints = user.getLastKiller().getRanking().getPoints();
        double plus, minus, procent = deadpoints * 0.05D;
        if (killpoints <= deadpoints) {
            double wartosc = (deadpoints - killpoints) / killpoints + 1.0D;
            plus = Math.round(procent * wartosc);
            minus = Math.round(procent);
        } else {
            double wartosc = (killpoints - deadpoints) / deadpoints + 1.0D;
            plus = Math.round(procent / wartosc);
            minus = Math.round(procent / (wartosc * wartosc));
        }
        int pp = (int) plus;
        int mm = (int) minus;
        user.getRanking().removePoints(mm);
        user.getLastKiller().getRanking().addPoints(pp);
        user.getRanking().addDeads(1);
        user.getLastKiller().getRanking().addKills(1);
        sortUserRankings();
        sortGuildRankings();
        Fight fight = new Fight(user.getLastKiller(),user,pp,mm,System.currentTimeMillis());
        user.getLastKiller().addFight(fight);
        user.addFight(fight);
        BungeeCord.getInstance().broadcast("Gracz " + user.getLastKiller().getName() + "[+" + plus + "] zabil gracza " + user.getName() + "[-" + minus + "]");
    }
}
