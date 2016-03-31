package pl.otekplay.sectors.netty.web.impl;

import lombok.AllArgsConstructor;
import pl.otekplay.sectors.netty.web.ArgHandler;

@AllArgsConstructor
public abstract class AbstractHandler implements ArgHandler {

    private final int args;

    @Override
    public int neededArgs() {
        return args;
    }
}
