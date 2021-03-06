package com.pvpin.blockly;

import com.pvpin.translation.PVPINTranslation;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class EnchantmentGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Enchantment> it = Arrays.stream(Enchantment.values()).iterator();
        while (it.hasNext()) {
            Enchantment ench = it.next();
            sb.append("[");
            sb.append("\"");
            try {
                sb.append(PVPINTranslation.getLocalizedName("zh_cn", ench));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(ench.getName());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "Enchantment.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
