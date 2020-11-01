package me.Geo54321.GeoEssentials.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MagnetListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInOffHand().getType().equals(Material.NETHER_STAR) || player.getInventory().getItemInOffHand().getType().equals(Material.WITHER_ROSE)) {
            for(Entity entity : player.getNearbyEntities(5,5,5)) {
                if(entity instanceof Item || entity instanceof ExperienceOrb) {
                    entity.teleport(player);
                }
            }
        }
    }
}
