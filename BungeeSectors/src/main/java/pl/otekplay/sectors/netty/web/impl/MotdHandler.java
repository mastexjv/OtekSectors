package pl.otekplay.sectors.netty.web.impl;

import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

public class MotdHandler extends AbstractHandler {
    public MotdHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        out.append("Ustawiles MOTD: "+args[1]);
        Settings.MOTD = ChatUtil.fixColors(args[1].replace("[NEWLINE]","\n"));
    }
}
