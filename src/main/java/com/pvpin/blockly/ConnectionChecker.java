package com.pvpin.blockly;

import org.bukkit.Bukkit;
import io.github.classgraph.ClassGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author William_Shi
 */
public class ConnectionChecker {
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        new ClassGraph().enableAllInfo().acceptPackages("org.bukkit").overrideClassLoaders(
                        new URLClassLoader(
                                new URL[]{Bukkit.class.getProtectionDomain().getCodeSource().getLocation()}
                                // These 2 lines took me so long!
                                // 2021.7.16 William_Shi
                        )
                ).scan().getAllClasses().filter(classInfo -> !classInfo.getPackageName().contains("craftbukkit"))
                .forEach(classInfo -> {
                    sb.append("\"");
                    sb.append(classInfo.getName());
                    sb.append("\"");
                    sb.append(": ");
                    sb.append("[");
                    var all = classInfo.getInterfaces();
                    all.addAll(classInfo.getSuperclasses());
                    var it = all.iterator();
                    while (it.hasNext()) {
                        var superInfo = it.next();
                        sb.append("\"");
                        sb.append(superInfo.getName());
                        sb.append("\"");
                        if (it.hasNext()) {
                            sb.append(",");
                        }
                    }
                    sb.append("],");
                });
        sb.append("}");

        File file = new File(Main.getProvidingPlugin(Main.class).getDataFolder(), "Connection.txt");
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
