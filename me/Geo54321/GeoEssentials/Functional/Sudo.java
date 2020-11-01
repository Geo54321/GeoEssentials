package me.Geo54321.GeoEssentials.Functional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sudo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getDisplayName().equals(args[0])) {
                    String sudoCommand = "/";
                    for (int g = 1; g < args.length; g++) {
                        sudoCommand += args[g];
                        sudoCommand += " ";
                    }
                    player.chat(sudoCommand);
                }
            }
        }
        else {
            sender.sendMessage("§cTarget and Command Required.");
        }
        return true;
    }
}
