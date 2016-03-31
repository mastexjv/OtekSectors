package pl.otekplay.sectors.netty.web.impl;

import net.md_5.bungee.BungeeCord;

/**
 * Created by Oskar on 18.02.2016.
 */
public class SendCommandHandler extends AbstractHandler {
    public SendCommandHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        String arg  = args[1];
        BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(),arg);

    }
}
