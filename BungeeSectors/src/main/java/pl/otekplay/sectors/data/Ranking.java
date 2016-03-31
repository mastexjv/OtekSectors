package pl.otekplay.sectors.data;

import com.mongodb.BasicDBObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import pl.otekplay.sectors.managers.RankingManager;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;

@Data
@AllArgsConstructor
public class Ranking {
    private final User user;
    @Getter
    private int points, kills, deaths;

    public void setPoints(int points) {
        this.points = points;
        Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_USER_POINTS_NAME, points + "").get(), user);

    }

    public void setKills(int kills) {
        this.kills = kills;
        Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_USER_KILLS_NAME, kills + "").get(), user);

    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_USER_DEATHS_NAME, deaths + "").get(), user);

    }

    public void addPoints(int points) {
        setPoints(this.points + points);
    }

    public void removePoints(int points) {
        setPoints(this.points - points);
    }

    public void addKills(int kills) {
        setKills(this.kills + kills);
    }

    public void removeKills(int kills) {
        setKills(this.kills - kills);
    }

    public void addDeads(int deaths) {
        setDeaths(this.deaths + deaths);
    }

    public void removeDeads(int deaths) {
        setDeaths(this.deaths - deaths);
    }

    public int getPlace() {
        return RankingManager.getPlaceUser(user);
    }

}
