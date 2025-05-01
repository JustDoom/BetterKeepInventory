package com.imjustdoom.betterkeepinventory.config;

import com.imjustdoom.betterkeepinventory.BetterKeepInventory;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Config {
    public static class Options {
        public boolean requirePermission = false;
        public boolean keepOnPlayerDeath = false;
        public boolean keepOnNaturalDeath = true;
        public boolean keepOnMobDeath = true;
    }

    public static Options GLOBAL_OPTIONS;
    public static Map<String, Options> WORLDS = new HashMap<>();

    public static void init() {
        BetterKeepInventory.get().saveDefaultConfig();
        BetterKeepInventory.get().reloadConfig();
        FileConfiguration config = BetterKeepInventory.get().getConfig();

        GLOBAL_OPTIONS = new Options();
        WORLDS.clear();

        // Get default global options
        GLOBAL_OPTIONS.requirePermission = config.getBoolean("require-permission", GLOBAL_OPTIONS.requirePermission);
        GLOBAL_OPTIONS.keepOnPlayerDeath = config.getBoolean("keep-on-player-death", GLOBAL_OPTIONS.keepOnPlayerDeath);
        GLOBAL_OPTIONS.keepOnNaturalDeath = config.getBoolean("keep-on-natural-death", GLOBAL_OPTIONS.keepOnNaturalDeath);
        GLOBAL_OPTIONS.keepOnMobDeath = config.getBoolean("keep-on-mob-death", GLOBAL_OPTIONS.keepOnMobDeath);

        // Get world specific options
        for (String worldName : config.getConfigurationSection("worlds").getKeys(false)) {
            World world = BetterKeepInventory.get().getServer().getWorld(worldName);
            if (world == null) {
                BetterKeepInventory.get().getLogger().warning("The world '" + worldName + "' was unable to be found. Please make sure you spelt it correctly.");
                continue;
            }

            if (Boolean.TRUE.equals(world.getGameRuleValue(GameRule.KEEP_INVENTORY))) {
                BetterKeepInventory.get().getLogger().warning("The world '" + worldName + "' has the 'Keep Inventory' gamerule enabled. This will mess with the functionality of the plugin in that world so we have skipped it. Please disable the gamerule and reload the plugin with '/bki reload' or restart the server");
                continue;
            }

            if (!config.getBoolean("worlds." + worldName + ".enabled", false)) {
                continue;
            }
            Options worldOptions = new Options();
            worldOptions.requirePermission = config.getBoolean("worlds." + worldName + ".require-permission", GLOBAL_OPTIONS.requirePermission);
            worldOptions.keepOnPlayerDeath = config.getBoolean("worlds." + worldName + ".keep-on-player-death", GLOBAL_OPTIONS.keepOnPlayerDeath);
            worldOptions.keepOnNaturalDeath = config.getBoolean("worlds." + worldName + ".keep-on-natural-death", GLOBAL_OPTIONS.keepOnNaturalDeath);
            worldOptions.keepOnMobDeath = config.getBoolean("worlds." + worldName + ".keep-on-mob-death", GLOBAL_OPTIONS.keepOnMobDeath);
            WORLDS.put(worldName, worldOptions);
        }
    }
}
