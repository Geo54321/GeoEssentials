package me.Geo54321.GeoEssentials.Functional;

import me.Geo54321.GeoEssentials.Utility.PlayerReturnObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Back implements CommandExecutor {
    private LinkedList<PlayerReturnObject> players;

    public Back(LinkedList<PlayerReturnObject> players) {
        this.players = players;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            for (int g = 0; g < players.size(); g++) {
                if (players.get(g).getPlayer().equals(player)) {
                    player.teleport(players.get(g).getReturnLocation());
                    return true;
                }
            }
            player.sendMessage("§cYou do not have a location to go back to.");
        }
        return false;
    }
}
