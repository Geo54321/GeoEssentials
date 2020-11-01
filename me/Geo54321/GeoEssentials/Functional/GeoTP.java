package me.Geo54321.GeoEssentials.Functional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeoTP implements CommandExecutor,TabCompleter {
    boolean safetyCheck;
    Player player;
    double x,y,z;
    String w,name;

    public GeoTP(boolean safetyCheck) {
        this.safetyCheck = safetyCheck;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            player = (Player) sender;
            name = player.getDisplayName();
            if(argumentChecking(args)) {
                Location dest = new Location(Bukkit.getWorld(w), x, y, z);
                if (safetyCheck) {
                    while (!isSafe(dest) && dest.getY() >= 0) {
                        if (!isSafeFooting(dest)) {
                            player.sendMessage("§cThere was no way to safely teleport the target. The floor at the location was unsafe.");
                            return true;
                        } else if (isFloating(dest))
                            dest.setY(dest.getY() - 1);
                        else if (isUnderground(dest))
                            dest.setY(dest.getY() + 1);
                    }
                    if (dest.getY() <= 0) {
                        player.sendMessage("§cThere was no way to safely teleport the target. There was no floor at the location.");
                        return true;
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getDisplayName().equals(name)) {
                        Entity mount = p.getVehicle();
                        p.teleport(dest);
                        if(mount != null) {
                            mount.teleport(dest);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> xcoord = Collections.singletonList("X");
        List<String> ycoord = Collections.singletonList("Y");
        List<String> zcoord = Collections.singletonList("Z");
        String[] playerNames = new String[Bukkit.getOnlinePlayers().size()];
        String[] worldPlayerNames = new String[Bukkit.getOnlinePlayers().size()+3];
        worldPlayerNames[0] = "-1";
        worldPlayerNames[1] = "0";
        worldPlayerNames[2] = "1";
        int count = 0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            playerNames[count] = p.getDisplayName();
            worldPlayerNames[count+3] = p.getDisplayName();
            count++;
        }
        List<String> players = Arrays.asList(playerNames);
        List<String> worldPlayers = Arrays.asList(worldPlayerNames);
        if(args.length > 5)
            return Collections.emptyList();
        else if(args.length > 4)
            return StringUtil.copyPartialMatches(args[4], players, new ArrayList<>());
        else if(args.length > 3)
            return StringUtil.copyPartialMatches(args[3], worldPlayers, new ArrayList<>());
        else if(args.length > 2)
            return StringUtil.copyPartialMatches(args[2], zcoord, new ArrayList<>());
        else if(args.length > 1)
            return StringUtil.copyPartialMatches(args[1], ycoord, new ArrayList<>());
        else if(args.length > 0)
            return StringUtil.copyPartialMatches(args[0], xcoord, new ArrayList<>());
        else
            return null;
    }

    private boolean isNumeric(String s) {
        try {
            double num = Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isSafe(Location dest) {
        return !isFloating(dest) && !isUnderground(dest) && isSafeFooting(dest);
    }

    private boolean isSafeFooting(Location dest) {
        Location temp = new Location(dest.getWorld(), dest.getX(), dest.getY()-1, dest.getZ());
        return !temp.getBlock().isLiquid() && !temp.getBlock().getType().equals(Material.FIRE);
    }

    private boolean isFloating(Location dest) {
        Location temp = new Location(dest.getWorld(), dest.getX(), dest.getY()-1, dest.getZ());
        return temp.getBlock().isPassable();
    }

    private boolean isUnderground(Location dest) {
        Location temp = new Location(dest.getWorld(), dest.getX(), dest.getY()+1, dest.getZ());
        return isDangerousBlock(temp.getBlock()) || isDangerousBlock(dest.getBlock());
    }

    private boolean isDangerousBlock(Block block) {
        if(block.isPassable()) {
            return block.isLiquid();
        }
        else
            return true;
    }

    private boolean argumentChecking(String[] args) {
        if(args.length > 2) {
            if(isNumeric(args[0]) && isNumeric(args[1]) && isNumeric(args[2])) {
                x = Double.parseDouble(args[0]) + 0.5;
                y = Double.parseDouble(args[1]);
                z = Double.parseDouble(args[2]) + 0.5;
                w = "world";
            }
            else {
                player.sendMessage("§cYou must input a numeric value for the coordinates.");
                return false;
            }
        }
        else {
            player.sendMessage("§cYou must input a coordinate for x, y, and z.");
            return false;
        }
        if(args.length > 3) {
            if(args.length > 4) {
                if(isNumeric(args[3])) {
                    w = findWorld(Integer.parseInt(args[3]));
                    name = args[4];
                }
                else {
                    player.sendMessage("§cThere was a problem with argument types or order.");
                    return false;
                }
            }
            else {
                if(isNumeric(args[3])) {
                    w = findWorld(Integer.parseInt(args[3]));
                }
                else {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(p.getDisplayName().equals(args[3])) {
                            name = args[3];
                            return true;
                        }
                    }
                    player.sendMessage("§cThe target player was not found or numeric dimID was not used.");
                    return false;
                }
            }
        }
        return true;
    }

    private String findWorld(int worldID) {
        if(worldID == 1) {
            return "world_the_end";
        }
        else if(worldID == -1) {
            return "world_nether";
        }
        else if(worldID == 0) {
            return "world";
        }
        else {
            player.sendMessage("§cWorld with given dimID not found.");
            return "world";
        }
    }
}
