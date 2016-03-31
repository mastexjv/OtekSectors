package pl.otekplay.sectors.netty.web;

public interface ArgHandler {

    int neededArgs();

    void handleMessage(String[] args, StringBuilder out);

}
