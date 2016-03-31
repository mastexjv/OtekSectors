package pl.otekplay.sectors.placeholders;

import codecrafter47.bungeetablistplus.api.bungee.IPlayer;
import codecrafter47.bungeetablistplus.api.bungee.placeholder.PlaceholderProvider;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;

/**
 * Created by Oskar on 11.02.2016.
 */
public class SectorsPlaceholderProvider extends PlaceholderProvider {
    @Override
    public void setup() {
        bind("points").to(tabListContext -> getPoints(tabListContext.getPlayer()));
        bind("kills").to(tabListContext -> getKills(tabListContext.getPlayer()));
        bind("deaths").to(tabListContext -> getDeaths(tabListContext.getPlayer()));
        bind("place").to(tabListContext -> getPlace(tabListContext.getPlayer()));
        bind("group").to(tabListContext -> getGroup(tabListContext.getPlayer()));
        bind("domain").to(tabListContext -> getDomain(tabListContext.getPlayer()));
        bind("ip").to(tabListContext -> getIP(tabListContext.getPlayer()));
        bind("gtag").to(tabListContext -> getGuildTag(tabListContext.getPlayer()));
        bind("gpoints").to(tabListContext -> getGuildPoints(tabListContext.getPlayer()));
        bind("gkills").to(tabListContext -> getGuildKills(tabListContext.getPlayer()));
        bind("gdeaths").to(tabListContext -> getGuildDeaths(tabListContext.getPlayer()));
        bind("gplace").to(tabListContext -> getGuildPlace(tabListContext.getPlayer()));
        bind("gleader").to(tabListContext -> getGuildLeader(tabListContext.getPlayer()));
    }


    private String getPoints(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getRanking().getPoints() + "";
    }

    private String getKills(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getRanking().getKills() + "";
    }

    private String getDeaths(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getRanking().getDeaths() + "";
    }

    private String getPlace(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getRanking().getPlace() + "";
    }

    private String getGroup(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getGroup() + "";
    }

    private String getDomain(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getDomain() + "";
    }

    private String getIP(IPlayer player) {
        User user = UserManager.getUser(player.getUniqueID());
        return user.getIP() + "";
    }

    private String getGuildTag(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getTag();
        }
        return ret;
    }

    private String getGuildPoints(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getRanking().getPoints();
        }
        return ret;
    }

    private String getGuildKills(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getRanking().getKills();
        }
        return ret;
    }

    private String getGuildDeaths(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getRanking().getDeads();
        }
        return ret;
    }

    private String getGuildPlace(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getRanking().getPlace();
        }
        return ret;
    }

    private String getGuildLeader(IPlayer player) {
        String ret = "";
        User user = UserManager.getUser(player.getUniqueID());
        if (user.hasGuild()) {
            ret = ret + user.getGuild().getLeader().getName();
        }
        return ret;
    }

}
