package pl.otekplay.sectors.utils;

public class MathUtil {



    public static int alwaysPlus(int num){
        if(num < 0){
            return Math.abs(num);
        }
        return  num;
    }
    public static int alwaysMinus(int num){
        if(num > 0){
            return Math.abs(num);
        }
        return  num;
    }
}
