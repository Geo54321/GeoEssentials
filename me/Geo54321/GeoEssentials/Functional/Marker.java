package me.Geo54321.GeoEssentials.Functional;

import me.Geo54321.GeoEssentials.Utility.FileWorker;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.*;

public class Marker implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;
    private FileWorker markList;
    LinkedList<String[]> allMarks = new LinkedList<>();
    public Marker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            updatePlayer(player);
            if(args.length > 0) {
                if(args.length == 1) {
                    if(getMark(args[0]) != null) {
                        player.sendMessage(getMarkLoc(getMark(args[0])));
                    }
                    else {
                        player.sendMessage("§cThe marker you entered does not exist.");
                    }
                }
                else {
                    if(args[1].equals("set")) {
                        if(getMark(args[0]) != null) {
                            player.sendMessage("§cThat marker already exists. You must remove the old one or use update.");
                        }
                        else {
                            addMark(args[0],player.getLocation());
                            player.sendMessage("§9Marker §5" + args[0] + " §9created successfully!");
                        }
                    }
                    else if(args[1].equals("remove")) {
                        if(getMark(args[0]) != null) {
                            removeMark(args[0]);
                            player.sendMessage("§6Marker §5" + args[0] + " §6removed successfully!");
                        }
                        else {
                            player.sendMessage("§cThe marker you entered does not exist.");
                        }
                    }
                    else if(args[1].equals("update")) {
                        if(getMark(args[0]) != null) {
                            removeMark(args[0]);
                            addMark(args[0], player.getLocation());
                            player.sendMessage("§bMarker §3" + args[0] + " §bupdated successfully!");
                        }
                        else {
                            player.sendMessage("§cThe mark you entered does not exist.");
                        }
                    }
                    else {
                        player.sendMessage("§cThe subcommand you entered does not exist.");

                    }
                }
            }
            else {
                player.sendMessage(listMarks());
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
            updatePlayer((Player) sender);
        List<String> subCommands = Arrays.asList("set","remove","update","track");
        String[] names = new String[allMarks.size()];
        for(int g = 0; g < allMarks.size(); g++)
            names[g] = allMarks.get(g)[0];
        List<String> markNames = Arrays.asList(names);
        if(args.length > 1)
            return StringUtil.copyPartialMatches(args[1], subCommands, new ArrayList<>());
        else if(args.length > 0)
            return StringUtil.copyPartialMatches(args[0], markNames, new ArrayList<>());
        else
            return null;
    }

    public void updatePlayer(Player player) {
        markList = new FileWorker(plugin,player.getUniqueId().toString(),"marks");
        markList.loadFile();
        allMarks = new LinkedList<>();
        for(String s : markList.getData()) {
            allMarks.add(markList.splitLine(s));
        }
    }

    public String listMarks() {
        String marks = "§5Marks: §7";
        for(String[] mark : allMarks) {
            marks += mark[0];
            marks += ", ";
        }
        marks = marks.substring(0,marks.length()-2);
        return marks;
    }

    public String[] getMark(String name) {
        for(String[] mark : allMarks) {
            if(mark[0].equals(name)) {
                return mark;
            }
        }
        return null;
    }

    public String getMarkLoc(String[] mark) {
        String markMessage = "§5Mark: §8[";
        markMessage += "§dName:§7 ";
        markMessage += mark[0];
        markMessage += " §dWorld:§7 ";
        markMessage += mark[1];
        markMessage += " §dX:§7 ";
        markMessage += mark[2];
        markMessage += " §dY:§7 ";
        markMessage += mark[3];
        markMessage += " §dZ:§7 ";
        markMessage += mark[4];
        markMessage += "§8]";
        return markMessage;
    }

    public void addMark(String name, Location loc) {
        //Format: 'Name:[name] World:[world] X:[x] Y:[y] Z:[z] Facing:[angle]'
        String[] format = {"Name","World","X","Y","Z","Facing"};
        String[] mark = new String[6];
        mark[0] = name;
        mark[1] = loc.getWorld().getName();
        mark[2] = ""+(Math.floor(loc.getX()) + 0.5);
        mark[3] = ""+loc.getY();
        mark[4] = ""+(Math.floor(loc.getZ()) + 0.5);
        mark[5] = ""+loc.getYaw();
        allMarks.add(mark);
        markList.createLine(format,mark);
        markList.saveFile();
    }

    public void removeMark(String name) {
        allMarks.remove(getMark(name));
        markList.removeLine(name);
        markList.saveFile();
    }
}
