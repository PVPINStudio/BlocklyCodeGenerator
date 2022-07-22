package com.pvpin.blockly;

import org.bukkit.TreeType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class TreeTypeGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<TreeType> it = Arrays.stream(TreeType.values()).iterator();
        while (it.hasNext()) {
            TreeType type = it.next();
            sb.append("[");
            sb.append("\"");
            sb.append(type.toString());
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(type.toString());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "TreeType.txt");
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
