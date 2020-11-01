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

public class Home implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;
    private FileWorker homeList;
    LinkedList<String[]> allHomes = new LinkedList<>();
    public Home(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            updatePlayer(player);
            if(args.length > 0) {
                if(args.length == 1) {
                    if(getHome(args[0]) != null) {
                        Entity mount = player.getVehicle();
                        player.teleport(getDest(getHome(args[0])));
                        if(mount != null) {
                            mount.teleport(getDest(getHome(args[0])));
                        }
                    }
                    else {
                        player.sendMessage("§cThe home you entered does not exist.");
                    }
                }
                else {
                    if(args[1].equals("set")) {
                        if(getHome(args[0]) != null) {
                            player.sendMessage("§cThat home already exists. You must remove the old one or use update.");
                        }
                        else {
                            addHome(args[0],player.getLocation());
                            player.sendMessage("§bHome §3" + args[0] + " §bcreated successfully!");
                        }
                    }
                    else if(args[1].equals("remove")) {
                        if(getHome(args[0]) != null) {
                            removeHome(args[0]);
                            player.sendMessage("§6Home §3" + args[0] + " §6removed successfully!");
                        }
                        else {
                            player.sendMessage("§cThe home you entered does not exist.");
                        }
                    }
                    else if(args[1].equals("get")) {
                        if(getHome(args[0]) != null) {
                            player.sendMessage(getHomeLoc(getHome(args[0])));
                        }
                        else {
                            player.sendMessage("§cThe home you entered does not exist.");
                        }
                    }
                    else if(args[1].equals("update")) {
                        if(getHome(args[0]) != null) {
                            removeHome(args[0]);
                            addHome(args[0], player.getLocation());
                            player.sendMessage("§bHome §3" + args[0] + " §bupdated successfully!");
                        }
                        else {
                            player.sendMessage("§cThe home you entered does not exist.");
                        }
                    }
                    else {
                        player.sendMessage("§cThe subcommand you entered does not exist.");

                    }
                }
            }
            else {
                player.sendMessage(listHomes());
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
            updatePlayer((Player) sender);
        List<String> subCommands = Arrays.asList("set","remove","get","update");
        String[] names = new String[allHomes.size()];
        for(int g = 0; g < allHomes.size(); g++)
            names[g] = allHomes.get(g)[0];
        List<String> homeNames = Arrays.asList(names);
        if(args.length > 2)
            return Collections.emptyList();
        else if(args.length > 1)
            return StringUtil.copyPartialMatches(args[1], subCommands, new ArrayList<>());
        else if(args.length > 0)
            return StringUtil.copyPartialMatches(args[0], homeNames, new ArrayList<>());
        else
            return null;
    }

    public void updatePlayer(Player player) {
        homeList = new FileWorker(plugin,player.getUniqueId().toString(),"homes");
        homeList.loadFile();
        allHomes = new LinkedList<>();
        for(String s : homeList.getData()) {
            allHomes.add(homeList.splitLine(s));
        }
    }

    public String listHomes() {
        String homes = "§3Homes: §7";
        for(String[] home : allHomes) {
            homes += home[0];
            homes += ", ";
        }
        homes = homes.substring(0,homes.length()-2);
        return homes;
    }

    public String[] getHome(String name) {
        for(String[] home : allHomes) {
            if(home[0].equals(name)) {
                return home;
            }
        }
        return null;
    }

    public String getHomeLoc(String[] home) {
        String homeMessage = "§3Home: §8[";
        homeMessage += "§3Name:§7 ";
        homeMessage += home[0];
        homeMessage += " §3World:§7 ";
        homeMessage += home[1];
        homeMessage += " §3X:§7 ";
        homeMessage += home[2];
        homeMessage += " §3Y:§7 ";
        homeMessage += home[3];
        homeMessage += " §3Z:§7 ";
        homeMessage += home[4];
        homeMessage += "§8]";
        return homeMessage;
    }

    public Location getDest(String[] home) {
        Location dest;
        if(home.length > 5 && home[5] != null) {
            dest = new Location(Bukkit.getWorld(home[1]), Double.parseDouble(home[2]), Double.parseDouble(home[3]), Double.parseDouble(home[4]), Float.parseFloat(home[5]), 0);
        }
        else {
            dest = new Location(Bukkit.getWorld(home[1]), Double.parseDouble(home[2]), Double.parseDouble(home[3]), Double.parseDouble(home[4]));
        }
        return dest;
    }

    public void addHome(String name, Location loc) {
        //Format: 'Name:[name] World:[world] X:[x] Y:[y] Z:[z] Facing:[angle]'
        String[] format = {"Name","World","X","Y","Z","Facing"};
        String[] home = new String[6];
        home[0] = name;
        home[1] = loc.getWorld().getName();
        home[2] = ""+(Math.floor(loc.getX()) + 0.5);
        home[3] = ""+loc.getY();
        home[4] = ""+(Math.floor(loc.getZ()) + 0.5);
        home[5] = ""+loc.getYaw();
        allHomes.add(home);
        homeList.createLine(format,home);
        homeList.saveFile();
    }

    public void removeHome(String name) {
        allHomes.remove(getHome(name));
        homeList.removeLine(name);
        homeList.saveFile();
    }
}

