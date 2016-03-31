package pl.otekplay.sectors.utils;

public class DoubleUtil {
    public static double round(final double valueToRound, final int numberOfDecimalPlaces) {
        final double multipicationFactor = Math.pow(10.0, numberOfDecimalPlaces);
        final double interestedInZeroDPs = valueToRound * multipicationFactor;
        return Math.round(interestedInZeroDPs) / multipicationFactor;
    }
}
