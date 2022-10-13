package com.rustrush.barrelloot.Configs;

import com.rustrush.barrelloot.BarrelLoot;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigBarrels {
    private BarrelLoot instance ;
    private File customConfigFile;
    private FileConfiguration customConfig;
    public ConfigBarrels(BarrelLoot barrelLoot){
        instance =barrelLoot;
        createSpawnsConfig();
    }
    public FileConfiguration getSpawnsConfig() {
        return this.customConfig;
    }
    public void createSpawnsConfig() {
        customConfigFile = new File(instance.getDataFolder(), "spawns.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            instance.saveResource("spawns.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void save(){
        try{
            customConfig.save(customConfigFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void reloadSpawnsConfig(){
        File f = new File(instance.getDataFolder(), "spawns.yml");
        try {
            customConfig.load(f);
        }catch (InvalidConfigurationException | IOException exception){
            exception.printStackTrace();
        }

    }
}
