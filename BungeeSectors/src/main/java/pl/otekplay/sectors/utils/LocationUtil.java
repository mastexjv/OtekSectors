package pl.otekplay.sectors.utils;

import pl.otekplay.sectors.data.Sector;

public class LocationUtil {

    public static int getDistanceFromBorder(int x,int z,Sector sec) {
        int distWest = Math.abs(sec.getWESTBORDER() - x);
        int distEast = Math.abs(sec.getEASTBORDER() - x);
        int distNorth = Math.abs(sec.getNORTHBORDER() - z);
        int distSouth = Math.abs(sec.getSOUTHBORDER() - z);
        int distX = (distWest < distEast) ? distWest : distEast;
        int distZ = (distNorth < distSouth) ? distNorth : distSouth;
        if(distX > distZ){
            return distZ;
        }
        return distX;
    }
}
