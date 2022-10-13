package com.rustrush.barrelloot.Barrels;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Loot {
    private final ItemStack item = new ItemStack(Material.PAPER);
    private final ItemMeta temp = item.getItemMeta();
    public ItemStack getScrap(){
        temp.setDisplayName(ChatColor.DARK_GRAY+"Scrap");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getRope(){
        temp.setDisplayName(ChatColor.YELLOW+"Rope");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getMetalBlade(){
        temp.setDisplayName(ChatColor.GRAY+"Metal Blade");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getEmptyPropaneTank(){
        temp.setDisplayName(ChatColor.DARK_GRAY+"Empty Propane Tank");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getTarp(){
        temp.setDisplayName(ChatColor.BLUE+"Tarp");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getSewingKit(){
        temp.setDisplayName(ChatColor.GOLD+"Sewing Kit");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getRoadSigns(){
        temp.setDisplayName(ChatColor.RED+"Road Signs");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getSemiAutomaticBody(){
        temp.setDisplayName(ChatColor.DARK_AQUA+"Semi Automatic Body");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getMetalPipe(){
        temp.setDisplayName(ChatColor.DARK_RED+"Metal Pipe");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getLowGradeFuel(){
        temp.setDisplayName(ChatColor.YELLOW+"LowGradeFuel");
        item.setItemMeta(temp);
        return item;
    }
    public ItemStack getCrudeOil(){
        temp.setDisplayName(ChatColor.BLACK+"Crude Oil");
        item.setItemMeta(temp);
        return item;
    }
}
