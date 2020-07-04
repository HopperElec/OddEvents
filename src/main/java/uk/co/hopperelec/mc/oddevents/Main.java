package uk.co.hopperelec.mc.oddevents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    Random random = new Random();
    boolean toggled = true;

    @Override
    public void onEnable() {getServer().getPluginManager().registerEvents(this,this);}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {
        if (toggled) {HandlerList.unregisterAll(); toggled = false;
        } else {getServer().getPluginManager().registerEvents(this,this); toggled = true;}
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
}
