package com.pvpin.blockly;

import com.pvpin.translation.PVPINTranslation;
import org.bukkit.block.Biome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class BiomeGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Biome> it = Arrays.stream(Biome.values()).iterator();
        while (it.hasNext()) {
            Biome biome = it.next();
            sb.append("[");
            sb.append("\"");
            sb.append(PVPINTranslation.getLocalizedName("zh_cn", biome) == null ? biome.toString() : PVPINTranslation.getLocalizedName("zh_cn", biome));
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(biome.toString());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "Biome.txt");
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
