package me.Geo54321.GeoEssentials.Utility;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;

public class FileWorker {
    private JavaPlugin plugin;
    private String folderName;
    private String fileName;
    private File file;
    private File folder;
    private BufferedReader reader;
    private BufferedWriter writer;
    private LinkedList<String> fileData;

    public FileWorker(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        fileData = new LinkedList<>();
        createFile();
    }

    public FileWorker(JavaPlugin plugin, String fileName, String folderName) {
        this.plugin = plugin;
        this.fileName = fileName;
        fileData = new LinkedList<>();
        createFile(folderName);
    }

    public void createFile() {
        file = new File("plugins/GeoEssentials/" + fileName + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            }
            catch(IOException e) {
                plugin.getLogger().log(Level.FINE,"Error Creating File.",e);
            }
        }
    }

    public void createFile(String folderName) {
        if(new File("plugins/GeoEssentials/"+folderName).mkdir()) {
            file = new File("plugins/GeoEssentials/"+folderName+"/"+fileName+".yml");
        }
        else if(new File("plugins/GeoEssentials/"+folderName).exists()) {
            file = new File("plugins/GeoEssentials/"+folderName+"/"+fileName+".yml");
        }
        else {
            file = new File("plugins/GeoEssentials/" + fileName + ".yml");
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            }
            catch(IOException e) {
                plugin.getLogger().log(Level.FINE,"Error Creating File.",e);
            }
        }
    }

    public void loadFile() {
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException e) {
            plugin.getLogger().log(Level.FINE,"Could Not Find File to Read.",e);
        }
        String line;
        try {
            while((line = reader.readLine()) != null)
                fileData.add(line);
            reader.close();
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.FINE,"Error Reading from File",e);
        }
    }

    public void saveFile() {
        try {
            writer = new BufferedWriter(new FileWriter(file));
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.FINE,"Could Not Find File to Write.",e);
        }
        try {
            for(String line : fileData) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e) {
            plugin.getLogger().log(Level.FINE,"Error Writing to File",e);
        }
    }

    public String[] splitLine(String str) {
        String[] firstSplit = str.split(" ");
        String[] content = new String[6];
        String[] secondSplit;
        for(int g = 0; g < firstSplit.length; g++) {
            secondSplit = firstSplit[g].split(":");
            content[g] = secondSplit[1];
        }
        return content;
    }

    public void createLine(String[] format, String[] data) {
        String s = "";
        for(int g = 0; g < format.length; g++) {
            s += format[g];
            s += ":";
            s += data[g];
            s += " ";
        }
        s = s.trim();
        fileData.add(s);
    }

    public void removeLine(String content) {
        String toRemove = null;
        for(int g = 0; g < fileData.size(); g++) {
            String[] temp = splitLine(fileData.get(g));
            if(temp[0].equals(content)) {
                toRemove = fileData.get(g);
            }
        }
        if(toRemove != null)
            fileData.remove(toRemove);
    }

    public LinkedList<String> getData() {
        return fileData;
    }
}
