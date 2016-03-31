package pl.otekplay.sectors.managers;

import codecrafter47.bungeetablistplus.api.bungee.BungeeTabListPlusAPI;
import codecrafter47.bungeetablistplus.api.bungee.placeholder.PlaceholderProvider;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.placeholders.SectorsPlaceholderProvider;

/**
 * Created by Oskar on 11.02.2016.
 */
public class TablistManager {

    public static void init(){
        BungeeTabListPlusAPI.getPlaceholderManager().registerPlaceholderProvider(new SectorsPlaceholderProvider());
    }


}
