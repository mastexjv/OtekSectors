package pl.otekplay.sectors.enums;

import lombok.Getter;

/**
 * Created by Oskar on 16.02.2016.
 */
public enum KitType {
    STARTER(0,1000*60*60),
    VIP(1,1000*60*60*3),
    CUSTOM(2,1000*60*60*7);
    @Getter
    final int ID;
    @Getter
    final long cooldown;

    KitType(int ID,long cooldown) {
        this.ID = ID;
        this.cooldown = cooldown;
    }
}
