package pl.otekplay.teamspeak;

import lombok.Getter;
import net.md_5.bungee.api.score.Team;
import pl.otekplay.teamspeak.task.AutoMessageTask;

/**
 * Created by Oskar on 27.02.2016.
 */
public class TS3Test {

    @Getter
    public static TeamSpeakBot bot;



    public static void main(String[] args){
      bot =  new TeamSpeakBot("151.80.120.127","serveradmin","otek");
        bot.start();
        new Thread(new AutoMessageTask()).start();
    }
}
