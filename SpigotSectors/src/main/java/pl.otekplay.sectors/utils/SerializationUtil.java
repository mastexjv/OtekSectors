package pl.otekplay.sectors.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SerializationUtil {


    public static String itemStackArrayToBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }
            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemStack[]{};
    }


    public static Collection<PotionEffect> deserializePotionEffects(String potionEffectString) {
       Collection<PotionEffect> rtrn = new ArrayList<PotionEffect>();
        potionEffectString = potionEffectString.replace(";", "");
        for (String serializedEffect : potionEffectString.split("e@")) {
            String effectName = serializedEffect.split(":d@")[0];
            String effectDuration = "";
            String effectAmplifier = "";
            for (String serializedEffectSplit : serializedEffect.split(":d@")) {
                if (serializedEffectSplit.equals(effectName) == false) {
                    String[] getDurAndAmp = serializedEffectSplit.split(":a@");
                    for (int i = 0; i < getDurAndAmp.length; i++) {
                        if (i == 0) {
                            if (getDurAndAmp[i].equals("") == false) {
                                effectDuration = getDurAndAmp[i];
                            }
                        }
                        if (i == 1) {
                            if (getDurAndAmp[i].equals("") == false) {
                                effectAmplifier = getDurAndAmp[i];
                            }
                        }
                    }
                }
            }
            if (effectName.equals("") == false && effectDuration.equals("") == false && effectAmplifier.equals("") == false) {
                rtrn.add(new PotionEffect(PotionEffectType.getByName(effectName), Integer.valueOf(effectDuration), Integer.valueOf(effectAmplifier)));
            }
        }
        return rtrn;
    }

    public static String serializePotionEffects(Player player) {
       Collection<PotionEffect> effects = player.getActivePotionEffects();
        String rtrn = ";";
        for (PotionEffect effect : effects) {
            String effectName = effect.getType().getName();
            int duration = effect.getDuration();
            int amplify = effect.getAmplifier();
            rtrn += "e@" + effectName + ":d@" + duration + ":a@" + amplify;
        }
        rtrn += ";";
        return rtrn;
    }
}
