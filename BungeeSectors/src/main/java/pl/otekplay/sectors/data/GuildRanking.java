package pl.otekplay.sectors.data;

import lombok.RequiredArgsConstructor;
import pl.otekplay.sectors.managers.RankingManager;

@RequiredArgsConstructor
public class GuildRanking {
    private final Guild guild;

    public int getPoints() {
        int points = guild.getLeader().getRanking().getPoints();
        for (User user : guild.getMembers()) {
            points = points + user.getRanking().getPoints();
        }
        return (points / guild.getMembers().size());
    }
    public int getKills() {
        int kills = guild.getLeader().getRanking().getKills();
        for (User user : guild.getMembers()) {
            kills = kills + user.getRanking().getKills();
        }
        return kills / guild.getMembers().size();
    }

    public int getDeads() {
        int deads = guild.getLeader().getRanking().getDeaths();
        for (User user : guild.getMembers()) {
            deads = deads + user.getRanking().getDeaths();
        }
        return deads / guild.getMembers().size();
    }
    public int getPlace() {
        return RankingManager.getPlaceGuild(guild);
    }
    public Guild getGuild() {
        return guild;
    }
}

