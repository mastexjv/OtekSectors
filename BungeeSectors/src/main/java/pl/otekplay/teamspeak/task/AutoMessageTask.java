package pl.otekplay.teamspeak.task;

import pl.otekplay.teamspeak.TS3Test;
import pl.otekplay.teamspeak.storage.Settings;

/**
 * Created by Oskar on 27.02.2016.
 */
public class AutoMessageTask implements Runnable {
    private String[] messages = new String[]{
            "Witaj na teamspeaku xHC.PL",
            "Potepiony ma malego.",
            "oLDIS to chUJ",
            "Otek krol"
    };
    private int lastID = 0;
    @Override
    public void run() {
        if(messages.length == 0){
            return;
        }
        if(lastID == messages.length){
            lastID = 0;
        }
        msg(messages[lastID]);
        lastID++;
        try {
            Thread.sleep(Settings.TIME_BEETWEN_MESSAGES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        run();
    }

    private void msg(String msg){
        TS3Test.getBot().getApi().sendServerMessage("[color=red] [MSG] [/color]".replace("[MSG]",msg));
    }
}
