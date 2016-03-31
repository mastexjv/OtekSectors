package pl.otekplay.sectors.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.otekplay.sectors.managers.UserManager;

import java.util.UUID;

/**
 * Created by Oskar on 13.02.2016.
 */
@AllArgsConstructor
@Getter
@Setter
public class Fight {

    private User winner,loser;
    private int plus, lose;
    private long time;


    public String toString() {
        return winner.getName() + ":" + loser.getName() + ":" + plus + ":" + lose + ":" + time;
    }
    public static Fight fromString(String string){
        String[] args = string.split(":");
        User win = UserManager.getUserByName(args[0]);
        User lose = UserManager.getUserByName(args[1]);
        int plus = Integer.parseInt(args[2]);
        int minus = Integer.parseInt(args[3]);
        long time = Long.parseLong(args[4]);
        return new Fight(win,lose,plus,minus,time);
    }
}
