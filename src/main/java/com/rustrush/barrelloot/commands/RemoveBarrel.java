package com.rustrush.barrelloot.commands;

import com.google.common.collect.Lists;
import com.rustrush.barrelloot.BarrelLoot;
import com.rustrush.barrelloot.Barrels.Barrels;
import com.rustrush.barrelloot.Configs.ConfigBarrels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RemoveBarrel extends AbstractCommands{
    private final Barrels barrels;
    private final BarrelLoot instance = BarrelLoot.getInstance();
    private final ConfigBarrels cfg;
    public RemoveBarrel() {
        super("delete-spawn");
        barrels = new Barrels(instance);
        cfg = new ConfigBarrels(instance);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            Bukkit.getLogger().info(ChatColor.RED + "This command only for player");
            return;
        }
        Player player = (Player) sender;
        if (player.hasPermission("delete-barrel")){
           int id = barrels.getIds(barrels.settingsLocation(player.getLocation()));
           if (id == 0){
               player.sendMessage(ChatColor.RED+"Spawn doesn't found");
               return;
           }
           cfg.getSpawnsConfig().set(String.valueOf(id),null);
           int count = cfg.getSpawnsConfig().getInt("count");
           int newCount = count - 1;
           cfg.getSpawnsConfig().set("count",newCount);
           player.sendMessage(ChatColor.GREEN+"Spawn successfully deleted!");
           cfg.save();
           barrels.getCoolDown().remove(id);
        }
    }
}
