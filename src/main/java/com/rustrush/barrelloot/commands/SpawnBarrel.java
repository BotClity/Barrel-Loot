package com.rustrush.barrelloot.commands;

import com.google.common.collect.Lists;
import com.rustrush.barrelloot.BarrelLoot;
import com.rustrush.barrelloot.Barrels.Barrels;
import com.rustrush.barrelloot.Configs.ConfigBarrels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnBarrel extends AbstractCommands{
    private final ConfigBarrels cfg;
    private final BarrelLoot instance = BarrelLoot.getInstance();
    public SpawnBarrel() {
        super("spawn-barrel");
        cfg = new ConfigBarrels(instance);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            Bukkit.getLogger().info(ChatColor.RED + "This command only for player");
            return;
        }
        Player player = (Player) sender;
        if (player.hasPermission("spawn-barrel")){
            if (args[0].equalsIgnoreCase("standard") || args[0].equalsIgnoreCase("fuel")){
                addSpawn(player,args[0]);
                if (args[0].equalsIgnoreCase("standard")) {
                    player.getLocation().getBlock().setType(Material.BLUE_WOOL);
                    return;
                }
                player.getLocation().getBlock().setType(Material.RED_WOOL);
            }

        }
    }
    public void addSpawn(Player player,String type){
        int count = cfg.getSpawnsConfig().getInt("count");
        int nextCount = count + 1;
        cfg.getSpawnsConfig().set("count",nextCount);
        cfg.getSpawnsConfig().set(nextCount+".type",type);
        cfg.getSpawnsConfig().set(nextCount+".hp",100);
        cfg.getSpawnsConfig().set(nextCount+".x",(int) player.getLocation().getX());
        cfg.getSpawnsConfig().set(nextCount+".y",(int) player.getLocation().getY());
        cfg.getSpawnsConfig().set(nextCount+".z",(int) player.getLocation().getZ());
        cfg.save();
        new Barrels(instance).resetID();
        cfg.reloadSpawnsConfig();
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args[0].length() >= 0){return Lists.newArrayList("standard","fuel");}
        return Lists.newArrayList();
    }
}
