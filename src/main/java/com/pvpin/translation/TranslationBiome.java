package com.pvpin.translation;

import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.block.Biome;

import static com.pvpin.translation.TranslationManager.*;

/**
 * @author William_Shi
 */
public class TranslationBiome {
    protected static String getMojangKey(Biome biome) {
        String str = biome.name().toLowerCase();
        return "biome.minecraft." + str;
    }

    /**
     * @param locale locale type
     * @param biome  biome to be translated
     * @return localized name
     */
    public static String getLocalizedName(String locale, Biome biome) {
        String str = null;
        switch (locale) {
            case "zh_cn": {
                str = getZH_CNName(getMojangKey(biome));
                break;
            }
            case "zh_tw": {
                str = getZH_TWName(getMojangKey(biome));
                break;
            }
            case "en_us": {
                str = getEN_USName(getMojangKey(biome));
                break;
            }
        }
        ;
        return str;
    }

    public static TranslatableComponent getTextComponent(Biome biome) {
        return new TranslatableComponent(getMojangKey(biome));
    }

}
