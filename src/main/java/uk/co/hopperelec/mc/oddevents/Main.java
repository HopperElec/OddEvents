package uk.co.hopperelec.mc.oddevents;

import org.bukkit.event.EventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    Random random = new Random();

    @Override
    public void onEnable() {getServer().getPluginManager().registerEvents(this,this);}

    @Override
    public boolean onCommand(CommandSender author, Command cmd, String label, String[] args) {HandlerList.unregisterAll();return true;}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {if (random.nextInt(96) == 0) {event.setCancelled(true);}}
}
