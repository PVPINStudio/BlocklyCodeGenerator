package com.pvpin.blockly;

import com.pvpin.pvpincore.api.PVPINLogManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author William_Shi
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            this.getDataFolder().mkdirs();
            Class.forName(EnchantmentGenerator.class.getName());
            Class.forName(EntityTypeGenerator.class.getName());
            Class.forName(MaterialGenerator.class.getName());
            Class.forName(ParticleGenerator.class.getName());
            Class.forName(PotionEffectTypeGenerator.class.getName());
            Class.forName(SoundGenerator.class.getName());
            Class.forName(TreeTypeGenerator.class.getName());
        } catch (ClassNotFoundException ex) {
            PVPINLogManager.log(ex);
        }
    }

    public static void main(String[] args) {

    }
}
