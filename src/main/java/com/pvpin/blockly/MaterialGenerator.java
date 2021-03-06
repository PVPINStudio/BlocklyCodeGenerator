package com.pvpin.blockly;

import com.pvpin.translation.PVPINTranslation;
import org.bukkit.Material;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class MaterialGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Material> it = Arrays.stream(Material.values()).iterator();
        while (it.hasNext()) {
            Material material = it.next();
            if (material.isLegacy()) continue;
            sb.append("[");
            sb.append("\"");
            try {
                sb.append(PVPINTranslation.getLocalizedName("zh_cn", material));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(material.toString());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "Material.txt");
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
