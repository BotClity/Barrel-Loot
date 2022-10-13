package com.rustrush.barrelloot.Barrels;

import com.google.common.collect.Lists;
import com.rustrush.barrelloot.BarrelLoot;
import com.rustrush.barrelloot.Configs.ConfigBarrels;
import com.rustrush.barrelloot.commands.SpawnBarrel;
import com.sun.imageio.spi.RAFImageInputStreamSpi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Barrels implements Listener {
    private final Loot loot = new Loot();
    private final BarrelLoot instance;
    private final ConfigBarrels cfg;
    private final HashMap<Integer, Location> ids = new HashMap<>();
    private final HashMap<Integer,Integer> coolDowns = new HashMap<>();
    private final World world;
    public Barrels(BarrelLoot barrelLoot){
        instance = barrelLoot;
        cfg = new ConfigBarrels(instance);
        delay();
        coolDown();
        world=Bukkit.getWorlds().get(0);
    }


    public void spawnBarrels(){
        int count = cfg.getSpawnsConfig().getInt("count");
        if (count == 0)return;
        for (int i = 1;i<=count;i++){
            String type = cfg.getSpawnsConfig().getString(i+".type");
            int x = cfg.getSpawnsConfig().getInt(i+".x");
            int y = cfg.getSpawnsConfig().getInt(i+".y");
            int z = cfg.getSpawnsConfig().getInt(i+".z");
            world.loadChunk(x,z);
            Location loc = new Location(world,x,y,z);
            ids.put(i,loc);
            if (loc == null){Bukkit.getLogger().info("Loc is null");return;}
            //Location loc = new Location(Bukkit.getWorld("rust-rush"),x,y,z);
            if (loc.getBlock() ==null || loc.getBlock().getType() == null || loc.getBlock().getType() == Material.AIR){
                spawnBarrel(type, i);
            }
        }
    }
    @EventHandler
    public void BlockBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL){
            if (ids.containsValue(settingsLocation(block.getLocation()))) {
                e.setCancelled(true);
            }

        }
    }
    @EventHandler
    public void BlockClick(PlayerInteractEvent e){
        if (e.getAction() != Action.LEFT_CLICK_BLOCK)return;
        Block temp = e.getClickedBlock();
        if (temp == null)return;
        if (temp.getType() == Material.RED_WOOL || temp.getType() == Material.BLUE_WOOL){
            if (ids.containsValue(settingsLocation(temp.getLocation()))){
                if (e.getItem() == null)return;
                if (e.getItem().getType() == Material.STONE && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("rock")){
                    int id = getIds(settingsLocation(temp.getLocation()));
                    if (id == 0)return;
                    setHpByDamage(id,25);
                }
            }
        }
    }
    public HashMap<Integer, Integer> getCoolDown(){return coolDowns;}
    public int getHp(int id) {
        return cfg.getSpawnsConfig().getInt(String.valueOf(id)+".hp");
    }
    public int getHp(String id){
        return cfg.getSpawnsConfig().getInt(id+".hp");
    }
    public void setHp(String id,int hp) {
        cfg.getSpawnsConfig().set(id+".hp",hp);
        cfg.save();
    }
    public void setHp(int id,int hp) {
        cfg.getSpawnsConfig().set(String.valueOf(id)+".hp",hp);
        cfg.save();
    }
    public boolean setHpByDamage(int id,int damage){
        int hp = getHp(id);
        int temp = hp - damage;
        if (temp <= 0){setHp(id,100);spawnLoot(cfg.getSpawnsConfig().getString(String.valueOf(id)+".type"),id);return false;}
        setHp(id,temp);
        return true;
    }
    public boolean setHpByDamage(String id,int damage){
        int hp = getHp(id);
        int temp = hp - damage;
        if (temp <= 0){setHp(id,100);spawnLoot(cfg.getSpawnsConfig().getString(id+".type"), Integer.parseInt(id));return false;}
        setHp(id,temp);
        return true;
    }
    public void spawnLoot(String type,int id){
        if (type.equalsIgnoreCase("standard")){
            Location location = ids.get(id);
            location.getBlock().setType(Material.AIR);
            coolDowns.put(id,12);
            location.setY((int) location.getY()+1);
            ItemStack scrap = loot.getScrap();
            scrap.setAmount(2);
            location.getWorld().dropItemNaturally(location,scrap);
            int rand = Random(1,100);
            if (rand > 95){
                ItemStack metalPipe = loot.getMetalPipe();
                metalPipe.setAmount(Random(1,4));
                location.getWorld().dropItemNaturally(location,metalPipe);
                return;
            }
            if (rand > 85 && rand < 96){
                ItemStack SAB = loot.getSemiAutomaticBody(); //SAB - Semi Automatic Body
                SAB.setAmount(1);
                location.getWorld().dropItemNaturally(location,SAB);
                return;
            }
            if (rand>80 && rand <86){
                ItemStack RoadSigns = loot.getRoadSigns();
                RoadSigns.setAmount(Random(2,3));
                location.getWorld().dropItemNaturally(location,RoadSigns);
                return;
            }
            if (rand>70 && rand <81){
                ItemStack SewingKit = loot.getSewingKit();
                SewingKit.setAmount(Random(3,4));
                location.getWorld().dropItemNaturally(location,SewingKit);
                return;
            }
            if (rand>55 && rand<71){
                ItemStack tarp = loot.getTarp();
                tarp.setAmount(1);
                location.getWorld().dropItemNaturally(location,tarp);
                return;
            }
            if (rand>40 && rand<56){
                ItemStack EMP = loot.getEmptyPropaneTank(); // EMP - Empty Propane Tank
                EMP.setAmount(1);
                location.getWorld().dropItemNaturally(location,EMP);
                return;
            }
            if (rand>20 && rand<21){
                ItemStack MetalBlade = loot.getMetalBlade(); // EMP - Empty Propane Tank
                MetalBlade.setAmount(1);
                location.getWorld().dropItemNaturally(location,MetalBlade);
                return;
            }
            ItemStack Rope = loot.getRope(); // EMP - Empty Propane Tank
            Rope.setAmount(Random(1,2));
            location.getWorld().dropItemNaturally(location,Rope);
            return;
        }
        Location location = ids.get(id);
        location.getBlock().setType(Material.AIR);
        coolDowns.put(id,12);
        location.setY((int) location.getY()+1);
        ItemStack LGF = loot.getLowGradeFuel();//LGF - Low Grade fuel
        LGF.setAmount(Random(5,9));
        location.getWorld().dropItemNaturally(location,LGF);
        ItemStack CrudeOil = loot.getCrudeOil();
        CrudeOil.setAmount(Random(15,19));
        location.getWorld().dropItemNaturally(location,CrudeOil);
    }
    public Location getLocation(int id){
        int x = getX(id);
        int y = getY(id);
        int z = getZ(id);
        return new Location(world,x,y,z);
    }
    public Material spawnBarrelMaterial(String type){
        if (type.equalsIgnoreCase("standard"))return Material.BLUE_WOOL;
        return Material.RED_WOOL;
    }
    public void spawnBarrel(String type,int id){
        Material material = spawnBarrelMaterial(type);
        resetID();
        getLocation(id).getBlock().setType(material);
        setHp(id,100);
    }
    public int getIds(Location location){
        if (ids.size() == 0)return 0;
        for (int key:ids.keySet()){
            Location loc = ids.get(key);
            if (location.getY() == loc.getY() && location.getX() == loc.getX() && location.getZ() == loc.getZ())return key;
        }
        return 0;
    }
    public void delay(){
        new BukkitRunnable(){
            @Override
            public void run() {
                spawnBarrels();
            }
        }.runTaskLater(instance,200);
    }
    public void resetID(){
        ids.clear();
        int count = cfg.getSpawnsConfig().getInt("count");
        for (int i = 1;i<=count;i++){
            int x = cfg.getSpawnsConfig().getInt(i+".x");
            int y = cfg.getSpawnsConfig().getInt(i+".y");
            int z = cfg.getSpawnsConfig().getInt(i+".z");
            Location loc = new Location(world,x,y,z);
            ids.put(i,loc);
        }
    }
    public void coolDown(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (coolDowns.isEmpty())return;
                for (int id:coolDowns.keySet()){
                    int time = coolDowns.get(id);
                    coolDowns.remove(id);
                    int temp = time - 1;
                    if (temp == 0){
                        spawnBarrel(cfg.getSpawnsConfig().getString(id+".type"),id);
                    }else {
                        coolDowns.put(id, temp);
                    }
                }
            }
        }.runTaskTimer(instance,0,1200);
    }
    public Location settingsLocation(Location location){
        Location loc = new Location(world,(int)location.getX(),(int) location.getY(),(int) location.getZ());
        return loc;
    }
    public int getX(int id){
        return cfg.getSpawnsConfig().getInt(id+".x");
    }
    public int getY(int id){
        return cfg.getSpawnsConfig().getInt(id+".y");
    }
    public int getZ(int id){
        return cfg.getSpawnsConfig().getInt(id+".z");
    }
    public int Random(int min,int max){
        int temp = new Random().nextInt(max-min+1);
        return temp+min;
    }
}
