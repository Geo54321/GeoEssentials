package me.Geo54321.GeoEssentials.Functional;

import me.Geo54321.GeoEssentials.Utility.FileWorker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.*;

public class Warp implements CommandExecutor, TabCompleter {
    private LinkedList<String[]> allWarps = new LinkedList<>();
    private FileWorker warpList;
    public Warp(JavaPlugin plugin) {
        warpList = new FileWorker(plugin,"warps");
        warpList.loadFile();
        for(String s : warpList.getData()) {
            allWarps.add(warpList.splitLine(s));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0 && sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                if(getWarp(args[0]) != null) {
                    Entity mount = player.getVehicle();
                    player.teleport(getDest(getWarp(args[0])));
                    if(mount != null) {
                        mount.teleport(getDest(getWarp(args[0])));
                    }
                }
                else {
                    player.sendMessage("§cThe warp you entered does not exist.");
                }
            }
            else {
                if(player.hasPermission("GeoPlugin.staff.warp")) {
                    if(args[1].equals("set")) {
                        if(getWarp(args[0]) != null) {
                            player.sendMessage("§cThat warp already exists. You must remove the old one or use update.");
                        }
                        else {
                            addWarp(args[0],player.getLocation());
                            player.sendMessage("§aWarp §2" + args[0] + " §acreated successfully!");
                        }
                    }
                    else if(args[1].equals("remove")) {
                        if(getWarp(args[0]) != null) {
                            removeWarp(args[0]);
                            player.sendMessage("§6Warp §2" + args[0] + " §6removed successfully!");
                        }
                        else {
                            player.sendMessage("§cThe warp you entered does not exist.");
                        }
                    }
                    else if(args[1].equals("get")) {
                        if(getWarp(args[0]) != null) {
                            player.sendMessage(getWarpLoc(getWarp(args[0])));
                        }
                        else {
                            player.sendMessage("§cThe warp you entered does not exist.");
                        }
                    }
                    else if(args[1].equals("update")) {
                        if(getWarp(args[0]) != null) {
                            removeWarp(args[0]);
                            addWarp(args[0],player.getLocation());
                            player.sendMessage("§aWarp §2" + args[0] + " §aupdated successfully!");
                        }
                        else {
                            player.sendMessage("§cThe warp you entered does not exist.");
                        }
                    }
                    else {
                        player.sendMessage("§cThe subcommand you entered does not exist.");

                    }
                }
                else {
                    player.sendMessage("§cYou do not have permission to alter warps.");
                }
            }
        }
        else {
            sender.sendMessage(listWarps());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> subCommands = Arrays.asList("set","remove","get","update");
        String[] names = new String[allWarps.size()];
        for(int g = 0; g < allWarps.size(); g++)
            names[g] = allWarps.get(g)[0];
        List<String> warpNames = Arrays.asList(names);
        if(args.length > 2)
            return Collections.emptyList();
        else if(args.length > 1)
            return StringUtil.copyPartialMatches(args[1], subCommands, new ArrayList<>());
        else if(args.length > 0)
            return StringUtil.copyPartialMatches(args[0], warpNames, new ArrayList<>());
        else
            return null;
    }

    public String listWarps() {
        String warps = "§2Warps: §7";
        for(String[] warp : allWarps) {
            warps += warp[0];
            warps += ", ";
        }
        warps = warps.substring(0,warps.length()-2);
        return warps;
    }

    public String[] getWarp(String name) {
        for(String[] warp : allWarps) {
            if(warp[0].equals(name)) {
                return warp;
            }
        }
        return null;
    }

    public String getWarpLoc(String[] warp) {
        String warpMessage = "§2Warp: §8[";
        warpMessage += "§2Name:§7 ";
        warpMessage += warp[0];
        warpMessage += " §2World:§7 ";
        warpMessage += warp[1];
        warpMessage += " §2X:§7 ";
        warpMessage += warp[2];
        warpMessage += " §2Y:§7 ";
        warpMessage += warp[3];
        warpMessage += " §2Z:§7 ";
        warpMessage += warp[4];
        warpMessage += "§8]";
        return warpMessage;
    }

    public Location getDest(String[] warp) {
        Location dest;
        if(warp.length > 5 && warp[5] != null) {
            dest = new Location(Bukkit.getWorld(warp[1]), Double.parseDouble(warp[2]), Double.parseDouble(warp[3]), Double.parseDouble(warp[4]), Float.parseFloat(warp[5]), 0);
        }
        else {
            dest = new Location(Bukkit.getWorld(warp[1]), Double.parseDouble(warp[2]), Double.parseDouble(warp[3]), Double.parseDouble(warp[4]));
        }
        return dest;
    }

    public void addWarp(String name, Location loc) {
        //Format: 'Name:[name] World:[world] X:[x] Y:[y] Z:[z] Facing:[angle]'
        String[] format = {"Name","World","X","Y","Z","Facing"};
        String[] warp = new String[6];
        warp[0] = name;
        warp[1] = loc.getWorld().getName();
        warp[2] = ""+(Math.floor(loc.getX()) + 0.5);
        warp[3] = ""+loc.getY();
        warp[4] = ""+(Math.floor(loc.getZ()) + 0.5);
        warp[5] = ""+loc.getYaw();
        allWarps.add(warp);
        warpList.createLine(format,warp);
        warpList.saveFile();
    }

    public void removeWarp(String name) {
        allWarps.remove(getWarp(name));
        warpList.removeLine(name);
        warpList.saveFile();
    }
}
