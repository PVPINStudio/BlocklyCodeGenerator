package com.pvpin.translation.v1_17;

import com.pvpin.pvpincore.impl.nms.VersionChecker;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Method;

import static com.pvpin.translation.TranslationManager.*;

/**
 * @author William_Shi
 */
public class Potion_17 {

    protected static String getMojangKey(PotionEffectType type) throws Exception {
        PotionEffect effect = new PotionEffect(type, 1, 1);
        Class<?> obcPotionUtil = Class.forName("org.bukkit.craftbukkit." + VersionChecker.version + ".potion.CraftPotionUtil");
        Method obcPotionUtil_fromBukkit = obcPotionUtil.getMethod("fromBukkit", effect.getClass());
        var nmsMobEffectObj = obcPotionUtil_fromBukkit.invoke(null, effect);
        Method nmsMobEffectObj_getMobEffectList = null;
        for (Method method : nmsMobEffectObj.getClass().getMethods()) {
            if (method.getReturnType().getName().equals("net.minecraft.world.effect.MobEffectList") && method.getParameterTypes().length == 0) {
                nmsMobEffectObj_getMobEffectList = method;
            }
        }
        var nmsObjMobEffectListObj = nmsMobEffectObj_getMobEffectList.invoke(nmsMobEffectObj);
        Method nmsObjMobEffectList_getName = null;
        for (Method method : nmsObjMobEffectListObj.getClass().getMethods()) {
            if (method.getReturnType() == String.class && method.getParameterTypes().length == 0) {
                if (method.getName().equals("toString")) continue;
                nmsObjMobEffectList_getName = method;
                break;
            }
        }
        String result = (String) nmsObjMobEffectList_getName.invoke(nmsObjMobEffectListObj);
        System.out.println(result);
        return result;
    }

    /**
     * @param locale locale type
     * @param type   potion effect type
     * @return localized name
     */
    public static String getLocalizedName(String locale, PotionEffectType type) throws Exception {
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
        return str;
    }

}
