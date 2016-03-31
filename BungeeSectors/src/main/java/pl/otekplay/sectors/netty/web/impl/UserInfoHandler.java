package pl.otekplay.sectors.netty.web.impl;

import pl.otekplay.sectors.managers.UserManager;

/**
 * Created by Oskar on 13.02.2016.
 */
public class UserInfoHandler extends AbstractHandler {
    public UserInfoHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        String name = args[1];
        if(!UserManager.isUser(name)){
            out.append("Gracz: "+name+" nie istnieje w bazie.");
            return;
        }
        out.append(UserManager.getUserByName(name).toString());

    }
}
