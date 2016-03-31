package pl.otekplay.sectors.utils;

import java.lang.reflect.Field;

/**
 * Created by Oskar on 24.02.2016.
 */
public class AccessUtil {
    public static Field setAccessible(Field f) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        f.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
        return f;
    }
}
