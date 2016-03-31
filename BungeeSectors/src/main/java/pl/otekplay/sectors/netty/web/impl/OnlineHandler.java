package pl.otekplay.sectors.netty.web.impl;

import net.md_5.bungee.BungeeCord;

public class OnlineHandler extends AbstractHandler {

    public OnlineHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        out.append(BungeeCord.getInstance().getOnlineCount());
    }
}
