package com.pvpin.translation.v1_17;

import com.pvpin.pvpincore.impl.nms.VersionChecker;
import org.bukkit.entity.EntityType;

import static com.pvpin.translation.TranslationManager.*;

/**
 * @author William_Shi
 */
public class Entity_17 {
    protected static String getMojangKey(EntityType type) {
            return "entity.minecraft." + type.name().toLowerCase();
    }

    /**
     * @param locale locale type
     * @param type   entity type
     * @return localized name
     */
    public static String getLocalizedName(String locale, EntityType type) {
        String str = null;
        switch (locale) {
            case "zh_cn": {
                str = getZH_CNName(getMojangKey(type));
                break;
            }
            case "zh_tw": {
                str = getZH_TWName(getMojangKey(type));
                break;
            }
            case "en_us": {
                str = getEN_USName(getMojangKey(type));
                break;
            }
        }
        ;
        return str;
    }

}
