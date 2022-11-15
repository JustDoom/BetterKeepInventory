package com.imjustdoom.betterkeepinventory.config;

import com.imjustdoom.betterkeepinventory.BetterKeepInventory;

import java.util.List;

public class Config {

    public static List<String> WORLDS;
    public static boolean PERMISSION;

    public static void init() {
        BetterKeepInventory.get().saveDefaultConfig();

        WORLDS = BetterKeepInventory.get().getConfig().getStringList("enabled-worlds");

        PERMISSION = BetterKeepInventory.get().getConfig().getBoolean("permission");
    }
}
