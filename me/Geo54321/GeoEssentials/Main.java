package me.Geo54321.GeoEssentials;

import me.Geo54321.GeoEssentials.Functional.*;
import me.Geo54321.GeoEssentials.Listeners.*;
import me.Geo54321.GeoEssentials.Utility.PlayerReturnObject;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;

public class Main extends JavaPlugin {
    public LinkedList<PlayerReturnObject> players = new LinkedList<>();
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadDefaultConfigFile();
        if(getConfig().getBoolean("modules.sudo")) {
            this.getCommand("sudo").setExecutor(new Sudo());
        }
        if(getConfig().getBoolean("modules.percent-sleep")) {
            getServer().getPluginManager().registerEvents(new SleepListener(getConfig().getDouble("options.sleep-percentage")), this);
        }
        if(getConfig().getBoolean("modules.markers")) {
            this.getCommand("mark").setExecutor(new Marker(this));
        }
        if(getConfig().getBoolean("modules.homes")) {
            this.getCommand("home").setExecutor(new Home(this));
        }
        if(getConfig().getBoolean("modules.warps")) {
            this.getCommand("warp").setExecutor(new Warp(this));
        }
        if(getConfig().getBoolean("modules.back")) {
            getServer().getPluginManager().registerEvents(new BackListener(players), this);
            this.getCommand("back").setExecutor(new Back(players));
        }
        if(getConfig().getBoolean("modules.geotp")) {
            this.getCommand("geotp").setExecutor(new GeoTP(getConfig().getBoolean("options.safe-teleportation")));
        }
        if(getConfig().getBoolean("modules.magnet")) {
            getServer().getPluginManager().registerEvents(new MagnetListener(), this);
        }
        if(getConfig().getBoolean("modules.right-click-harvest")) {
            getServer().getPluginManager().registerEvents(new HarvestListener(this), this);
        }
        if(getConfig().getBoolean("modules.lily-pad-duplicating")) {
            getServer().getPluginManager().registerEvents(new LilyPadListener(), this);
        }
        if(getConfig().getBoolean("modules.lily-pad-duplicating")) {
            getServer().getPluginManager().registerEvents(new LilyPadListener(), this);
        }
    }

    @Override
    public void onDisable() {

    }

    private void loadDefaultConfigFile() {
        config.options().header("GeoPlugin Config File");
        config.addDefault("options.sleep-percentage",0.4);
        config.addDefault("options.safe-teleportation",true);
        //config.addDefault("back-to-backs", true);

        config.addDefault("modules.sudo",true);
        config.addDefault("modules.percent-sleep",true);
        config.addDefault("modules.markers",true);
        config.addDefault("modules.homes",true);
        config.addDefault("modules.warps",true);
        config.addDefault("modules.back",true);
        config.addDefault("modules.geotp",true);
        config.addDefault("modules.magnet",true);
        config.addDefault("modules.right-click-harvest",true);
        config.addDefault("modules.lily-pad-duplicating",true);
        config.addDefault("modules.spawn-tp",true);

        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
