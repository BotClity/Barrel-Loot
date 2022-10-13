package com.rustrush.barrelloot;

import com.rustrush.barrelloot.Barrels.Barrels;
import com.rustrush.barrelloot.Configs.ConfigBarrels;
import com.rustrush.barrelloot.commands.RemoveBarrel;
import com.rustrush.barrelloot.commands.SpawnBarrel;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BarrelLoot extends JavaPlugin {
    private static BarrelLoot instance;
    private ConfigBarrels configBarrels = new ConfigBarrels(this);

    public static BarrelLoot getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        listen(new Barrels(this));
        new SpawnBarrel();
        new RemoveBarrel();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void listen(Listener listener){
        getServer().getPluginManager().registerEvents(listener,this);
    }
}
