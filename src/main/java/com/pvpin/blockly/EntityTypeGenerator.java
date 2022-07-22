package com.pvpin.blockly;

import com.pvpin.translation.PVPINTranslation;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class EntityTypeGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<EntityType> it = Arrays.stream(EntityType.values()).iterator();
        while (it.hasNext()) {
            EntityType entityType = it.next();
            sb.append("[");
            sb.append("\"");
            try {
                String str = PVPINTranslation.getLocalizedName("zh_cn", entityType);
                sb.append(str);
            } catch (Exception ex) {
                // java.lang.IllegalArgumentException: EntityType doesn't have key! Is it UNKNOWN?
                sb.append(entityType.toString());
            }
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(entityType.toString());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "EntityType.txt");
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
