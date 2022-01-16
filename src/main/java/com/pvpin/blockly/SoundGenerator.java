package com.pvpin.blockly;

import com.pvpin.pvpincore.api.PVPINLogManager;
import com.pvpin.translation.PVPINTranslation;
import org.bukkit.Sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author William_Shi
 */
public class SoundGenerator {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Sound> it = Arrays.stream(Sound.values()).iterator();
        while (it.hasNext()) {
            Sound sound = it.next();
            sb.append("[");
            sb.append("\"");
            sb.append(PVPINTranslation.getLocalizedName("zh_cn", sound) == null ? sound.toString() : PVPINTranslation.getLocalizedName("zh_cn", sound));
            sb.append("\"");
            sb.append(", ");
            sb.append("\"");
            sb.append(sound.toString());
            sb.append("\"");
            sb.append("]");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "Sound.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                PVPINLogManager.log(ex);
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            PVPINLogManager.log(ex);
        }
    }
}
