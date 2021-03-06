/*
 * The MIT License
 * Copyright © 2020-2021 PVPINStudio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pvpin.translation;

import com.pvpin.pvpincore.impl.nms.NMSUtils;
import com.pvpin.pvpincore.modules.boot.PVPINLoadOnEnable;
import com.pvpin.pvpincore.impl.nms.VersionChecker;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static com.pvpin.translation.TranslationManager.*;
import static com.pvpin.pvpincore.impl.nms.VersionChecker.version;

/**
 * @author William_Shi
 */
public class TranslationEnchantment {
    protected static String getMojangKey(Enchantment ench) {
        if (VersionChecker.isCurrentHigherOrEquals("v1_13_R0")) {
            return "enchantment.minecraft." + EnchantmentTranslationNMSUtils.getEnchantmentKey(ench);
        } else {
            throw new RuntimeException("Not Finished Yet.");
        }
    }

    /**
     * @param locale locale type
     * @param ench   enchantment type
     * @return localized name
     */
    public static String getLocalizedName(String locale, Enchantment ench) {
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
        ;
        return str;
    }

    public static TranslatableComponent getTextComponent(Enchantment ench) {
        return new TranslatableComponent(getMojangKey(ench));
    }

}

/**
 * @author William_Shi
 */
class EnchantmentTranslationNMSUtils extends NMSUtils {

    protected static Class<?> nmsEnchantment;
    protected static Class<?> obcEnchantment;

    protected static Method obcEnchantment_getRaw;
    protected static Object nmsIRegistry_Enchantment;

    static {
        try {
            nmsEnchantment = Class.forName("net.minecraft.server." + version + ".Enchantment");
            obcEnchantment = Class.forName("org.bukkit.craftbukkit." + version + ".enchantments.CraftEnchantment");
            obcEnchantment_getRaw = obcEnchantment.getMethod("getRaw", Enchantment.class);

            Arrays.stream(nmsIRegistry.getDeclaredFields()).forEach(
                    action -> {
                        if (action.getGenericType().getTypeName().contains(nmsEnchantment.getName())) {
                            if (action.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType type = (ParameterizedType) action.getGenericType();
                                if (type.getRawType().getTypeName().contains(nmsIRegistry.getName())) {
                                    try {
                                        nmsIRegistry_Enchantment = action.get(null);
                                    } catch (IllegalAccessException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
            );
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | SecurityException ex) {
            ex.printStackTrace();
        }
    }

    public static String getEnchantmentKey(Enchantment ench) {
        String ret = null;
        try {
            Object nmsEnch = obcEnchantment_getRaw.invoke(null, ench);
            Object minecraftKey = nmsIRegistry_getKey.invoke(nmsIRegistry_Enchantment, nmsEnch);
            ret = (String) nmsMinecraftKey_getKey.invoke(minecraftKey);
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
