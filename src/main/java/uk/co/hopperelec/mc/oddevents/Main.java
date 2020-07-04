package uk.co.hopperelec.mc.oddevents;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    Random random = new Random();
    boolean toggled = true;

    @Override
    public void onEnable() {getServer().getPluginManager().registerEvents(this,this);}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (toggled) {HandlerList.unregisterAll(); toggled = false; author.sendMessage("Toggled off");}
        else {getServer().getPluginManager().registerEvents(this,this); toggled = true; author.sendMessage("Toggled on");}
        return true;}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (random.nextInt(16) == 1) {event.setCancelled(true);}}

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (random.nextBoolean()) {
            Material blocktype = event.getBlock().getType();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> event.getBlock().setType(blocktype),1);
        } else {event.setDropItems(false);}}

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (random.nextBoolean()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> {
                ItemStack giveItem = event.getItemInHand(); giveItem.setAmount(1);
                event.getPlayer().getInventory().addItem(giveItem);
        },1);
        } else {Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> event.getBlock().setType(Material.AIR),1);}}

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event){
        if (random.nextBoolean()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> {
                ItemStack giveItem = event.getItem(); giveItem.setAmount(1);
                event.getPlayer().getInventory().addItem(giveItem);
            },1);
        } else {event.setCancelled(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this,() -> {
                ItemStack removeItem = event.getItem(); removeItem.setAmount(1);
                event.getPlayer().getInventory().remove(removeItem);
            },1);}}

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event){
        int choice = random.nextInt(4);
        if (choice == 0) {event.setDamage(event.getDamage()*2);
        } else if (choice == 1) {event.setCancelled(true);
            if (event.getEntity() instanceof LivingEntity) {
                ((LivingEntity) event.getEntity()).damage(event.getDamage());}
        } else if (choice == 2) {event.setCancelled(true);}}
//
//    @EventHandler
//    public void onTNTPrimed(ExplosionPrimeEvent event) {event.setFire(true);}
//    @EventHandler
//    public void onTNTExplodes(EntityExplodeEvent event) {
//        if (event.getLocation().getWorld() != null) {event.getLocation().getWorld().createExplosion(event.getLocation(),20);}}
//
//    @EventHandler
//    public void onClick(PlayerInteractEvent event) {event.getPlayer().sendMessage("Phantom is dumb");}
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        if (new Location(event.getTo().getWorld(),event.getTo().getX(),event.getTo().getY()-1,event.getTo().getZ()).getBlock().getType() == Material.GRASS_BLOCK && random.nextInt(32) == 1) {
//            event.getTo().getWorld().createExplosion(new Location(event.getTo().getWorld(),event.getTo().getX(),event.getTo().getY()-30,event.getTo().getZ()),15);
//        }
//    }
}
