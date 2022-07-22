package com.pvpin.translation.v1_17;

import com.pvpin.pvpincore.impl.nms.VersionChecker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.pvpin.translation.TranslationManager.*;

/**
 * @author William_Shi
 */
public class Material_17 {
    /**
     * @param locale   locale type
     * @param material material to be translated
     * @return localized name
     */
    public static String getLocalizedName(String locale, Material material) throws Exception {
        String str = null;
        switch (locale) {
            case "zh_cn": {
                str = getZH_CNName(getMojangKey(material));
                break;
            }
            case "zh_tw": {
                str = getZH_TWName(getMojangKey(material));
                break;
            }
            case "en_us": {
                str = getEN_USName(getMojangKey(material));
                break;
            }
        }
        return str;
    }

    public static String getMojangKey(Material mat) throws Exception {
        if (mat.isItem()) {
            ItemStack stack = new ItemStack(mat);
            Class<?> obcMagicNumbers = Class.forName("org.bukkit.craftbukkit." + VersionChecker.version + ".util.CraftMagicNumbers");
            Object nmsItemObj = obcMagicNumbers.getMethod("getItem", Material.class).invoke(null, mat);
            Class<?> nmsItem = nmsItemObj.getClass();
            Method nmsItem_getName = null;
            for (Method method : nmsItem.getMethods()) {
                if (method.getReturnType() == String.class && method.getParameterTypes().length == 0) {
                    if (method.getName().equals("toString")) continue;
                    nmsItem_getName = method;
                    break;
                }
            }
            String result = (String) nmsItem_getName.invoke(nmsItemObj);
            return result;
        } else {
            var block = Bukkit.getWorlds().get(0).getBlockAt(0, 0, 0);
            block.setType(mat);
            Class<?> obcBlock = Class.forName("org.bukkit.craftbukkit." + VersionChecker.version + ".block.CraftBlock");
            Method obcBlock_getNMS = obcBlock.getMethod("getNMS");
            var nmsIBlockDataObj = obcBlock_getNMS.invoke(block);
            Method nmsIBlockData_getBlock = null;
            for (Method method : nmsIBlockDataObj.getClass().getMethods()) {
                if (method.getReturnType().getName().equals("net.minecraft.world.level.block.Block") && method.getParameterTypes().length == 0) {
                    nmsIBlockData_getBlock = method;
                }
            }
            var nmsBlockObj = nmsIBlockData_getBlock.invoke(nmsIBlockDataObj);
            Method nmsBlock_getName = null;
            for (Method method : nmsBlockObj.getClass().getMethods()) {
                if (method.getReturnType() == String.class && method.getParameterTypes().length == 0) {
                    if (method.getName().equals("toString")) continue;
                    nmsBlock_getName = method;
                    break;
                }
            }
            String result = (String) nmsBlock_getName.invoke(nmsBlockObj);
            return result;
        }
    }

}
