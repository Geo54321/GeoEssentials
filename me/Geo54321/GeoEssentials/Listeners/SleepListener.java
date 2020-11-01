package me.Geo54321.GeoEssentials.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.LinkedList;

public class SleepListener implements Listener {
    private LinkedList asleep = new LinkedList();
    private int inWorldCount;
    private double sleepPercent;
    private int requiredAmount;

    public SleepListener(double sleepPercent) {
        this.sleepPercent = sleepPercent;
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) {
        inWorldCount = event.getBed().getWorld().getPlayers().size();
        requiredAmount = (int) Math.ceil(inWorldCount * sleepPercent);
        if(event.getBed().getWorld().getTime() > 12540 || event.getBed().getWorld().isThundering()) {
            if(!asleep.contains(event.getPlayer().getDisplayName())) {
                asleep.add(event.getPlayer().getDisplayName());
                Bukkit.broadcastMessage("§9" + event.getPlayer().getDisplayName() + " has fallen asleep. Currently asleep: " + asleep.size() + "/" + inWorldCount + ". " + requiredAmount + " are needed to pass the night.");
                event.getPlayer().setStatistic(Statistic.TIME_SINCE_REST,0);
                if (asleep.size() >= requiredAmount) {
                    event.getBed().getWorld().setTime(1000);
                    event.getBed().getWorld().setStorm(false);
                    event.getBed().getWorld().setThundering(false);
                    Bukkit.broadcastMessage("§6Good Morning! :D");
                    asleep.clear();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAwake(PlayerBedLeaveEvent event) {
        asleep.remove(event.getPlayer().getDisplayName());
        inWorldCount = event.getBed().getWorld().getPlayers().size();
        requiredAmount = (int) Math.ceil(inWorldCount*sleepPercent);
        if(event.getBed().getWorld().getTime() > 12545 || event.getBed().getWorld().isThundering()) {
            Bukkit.broadcastMessage("§3" + event.getPlayer().getDisplayName() + " has woke up. Currently asleep: " + asleep.size() + "/" + inWorldCount + ". " + requiredAmount + " are needed to pass the night.");
        }
    }
}
