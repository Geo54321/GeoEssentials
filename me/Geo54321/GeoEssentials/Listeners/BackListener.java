package me.Geo54321.GeoEssentials.Listeners;

import me.Geo54321.GeoEssentials.Utility.PlayerReturnObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.LinkedList;

public class BackListener implements Listener {
    private LinkedList<PlayerReturnObject> players;

    public BackListener(LinkedList<PlayerReturnObject> players) {
        this.players = players;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND) || event.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN) || event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            for (int g = 0; g < players.size(); g++) {
                if (players.get(g).getPlayer().equals(event.getPlayer())) {
                    players.get(g).setReturnLocation(event.getFrom());
                    return;
                }
            }
            players.add(new PlayerReturnObject(event.getPlayer(), event.getFrom()));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        boolean temp = true;
        for (int g = 0; g < players.size(); g++) {
            if (players.get(g).getPlayer().equals(event.getEntity().getPlayer())) {
                players.get(g).setReturnLocation(event.getEntity().getLocation());
                temp = false;
            }
        }
        if(temp)
            players.add(new PlayerReturnObject(event.getEntity().getPlayer(), event.getEntity().getLocation()));
    }
}
