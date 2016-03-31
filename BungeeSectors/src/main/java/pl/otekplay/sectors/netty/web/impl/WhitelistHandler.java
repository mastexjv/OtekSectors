package pl.otekplay.sectors.netty.web.impl;

import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

public class WhitelistHandler extends AbstractHandler {
    public WhitelistHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        if(Settings.whitelist.contains(args[1])){
            out.append("Usuneles: "+args[1]);
            Settings.whitelist.remove(args[1]);
        }else {
            out.append("Dodales: " + args[1]);
            Settings.whitelist.add(args[1]);
        }
        out.append("\nLista Whitelist: "+Settings.whitelist.toString());
    }
}
