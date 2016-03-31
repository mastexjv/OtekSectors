package pl.otekplay.sectors.netty.web.impl;

import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.managers.SectorManager;

/**
 * Created by Oskar on 13.02.2016.
 */
public class SectorsHandler extends AbstractHandler {
    public SectorsHandler() {
        super(0);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        out.append("# Sektory #\n");
        for (Sector sector : SectorManager.getSectors()) {
            out.append("\nSector: "+sector.getName()+" Online: "+sector.getServer().getPlayers().size()+" TPS: "+sector.getTPS()+" N: "+sector.getNORTHBORDER()+" S:"+sector.getSOUTHBORDER()+" E: "+sector.getEASTBORDER()+" W: "+sector.getWESTBORDER());
        }
    }
}
