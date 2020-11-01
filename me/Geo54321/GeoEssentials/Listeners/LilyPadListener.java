package me.Geo54321.GeoEssentials.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LilyPadListener implements Listener {
    Random rng = new Random();
    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getClickedBlock().getType().equals(Material.LILY_PAD) && event.getMaterial().equals(Material.BONE_MEAL)) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                if(rng.nextInt(3) == 2)
                    event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.LILY_PAD, 1));
            }
        }
    }
}
