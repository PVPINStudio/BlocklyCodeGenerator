package com.pvpin.translation.v1_17;

import com.pvpin.pvpincore.impl.nms.VersionChecker;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Method;

import static com.pvpin.translation.TranslationManager.*;

/**
 * @author William_Shi
 */
public class Ench_17 {
    /**
     * @param locale locale type
     * @param ench   enchantment type
     * @return localized name
     */
    public static String getLocalizedName(String locale, Enchantment ench) throws Exception {
        String str = null;
        switch (locale) {
            case "zh_cn": {
                str = getZH_CNName(getMojangKey(ench));
                break;
            }
            case "zh_tw": {
                str = getZH_TWName(getMojangKey(ench));
                break;
            }
            case "en_us": {
                str = getEN_USName(getMojangKey(ench));
                break;
            }
        }
        return str;
    }

    public static String getMojangKey(Enchantment ench) throws Exception {
        Class<?> obcEnchantment = Class.forName("org.bukkit.craftbukkit." + VersionChecker.version + ".enchantments.CraftEnchantment");
        Method obcEnchantment_getRaw = obcEnchantment.getMethod("getRaw", Enchantment.class);
        var nmsEnchantmentObj = obcEnchantment_getRaw.invoke(null, ench);
        Method nmsEnchantment_getName = null;
        for (Method method : nmsEnchantmentObj.getClass().getMethods()) {
            if (method.getReturnType() == String.class && method.getParameterTypes().length == 0) {
                if (method.getName().equals("toString")) continue;
                nmsEnchantment_getName = method;
                break;
            }
        }
        String result = (String) nmsEnchantment_getName.invoke(nmsEnchantmentObj);
        System.out.println(result);
        return result;
    }

}
