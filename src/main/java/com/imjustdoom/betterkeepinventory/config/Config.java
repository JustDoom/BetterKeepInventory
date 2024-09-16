package com.imjustdoom.betterkeepinventory.config;

import com.imjustdoom.betterkeepinventory.BetterKeepInventory;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    public static List<String> WORLDS;
    public static boolean SPECIFIC_WORLDS;
    public static boolean PERMISSION;

    public static void init() {
        BetterKeepInventory.get().saveDefaultConfig();

        FileConfiguration config = BetterKeepInventory.get().getConfig();

        WORLDS = config.getStringList("enabled-worlds");
        SPECIFIC_WORLDS = config.getBoolean("only-in-specific-worlds");
        PERMISSION = config.getBoolean("permission");
    }
}
