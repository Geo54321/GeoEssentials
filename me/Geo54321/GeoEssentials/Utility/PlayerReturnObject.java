package me.Geo54321.GeoEssentials.Utility;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerReturnObject {
    private Player player;
    private Location returnLocation;

    public PlayerReturnObject(Player player,Location returnLocation) {
        this.player = player;
        this.returnLocation = returnLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getReturnLocation() {
        return returnLocation;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setReturnLocation(Location returnLocation) {
        this.returnLocation = returnLocation;
    }
}
