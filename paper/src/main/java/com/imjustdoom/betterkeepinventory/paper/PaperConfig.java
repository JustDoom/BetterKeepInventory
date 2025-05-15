package com.imjustdoom.betterkeepinventory.paper;

import com.imjustdoom.betterkeepinventory.common.BetterPlayer;
import com.imjustdoom.betterkeepinventory.common.Configuration;
import net.kyori.adventure.text.Component;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PaperConfig extends Configuration {
    @Override
    public void init(BetterPlayer<?> player) {
        BetterKeepInventoryPaper.get().saveDefaultConfig();
        BetterKeepInventoryPaper.get().reloadConfig();
        FileConfiguration config = BetterKeepInventoryPaper.get().getConfig();

        globalOptions = new Options();
        worlds.clear();

        // Get default global options
        globalOptions.requirePermission = config.getBoolean("require-permission", globalOptions.requirePermission);
        globalOptions.keepOnPlayerDeath = config.getBoolean("keep-on-player-death", globalOptions.keepOnPlayerDeath);
        globalOptions.keepOnNaturalDeath = config.getBoolean("keep-on-natural-death", globalOptions.keepOnNaturalDeath);
        globalOptions.keepOnMobDeath = config.getBoolean("keep-on-mob-death", globalOptions.keepOnMobDeath);
        globalOptions.keepOnSuicide = config.getBoolean("keep-on-suicide", globalOptions.keepOnSuicide);
        globalOptions.keepInventory = config.getBoolean("keep-inventory-items-on-death", globalOptions.keepInventory);
        globalOptions.keepExp = config.getDouble("keep-exp-on-death", globalOptions.keepExp);

        // Get world specific options
        for (String worldName : config.getConfigurationSection("worlds").getKeys(false)) {
            World world = BetterKeepInventoryPaper.get().getServer().getWorld(worldName);
            if (world == null) {
                String message = "The world '" + worldName + "' was unable to be found. Please make sure you spelt it correctly.";
                BetterKeepInventoryPaper.get().getLogger().warning(message);
                if (player != null) {
                    ((Player) player.player()).sendMessage(Component.text(BetterKeepInventoryPaper.PREFIX + " " + message, BetterKeepInventoryPaper.TEXT_COLOR));
                }
                continue;
            }

            if (!config.getBoolean("worlds." + worldName + ".enabled", false)) {
                continue;
            }

            if (Boolean.TRUE.equals(world.getGameRuleValue(GameRule.KEEP_INVENTORY))) {
                String message = "The world '" + worldName + "' has the 'Keep Inventory' gamerule enabled. This will mess with the functionality of the plugin in that world so we have skipped it. Please disable the gamerule and reload the plugin with '/bki reload' or restart the server";
                BetterKeepInventoryPaper.get().getLogger().warning(message);
                if (player != null) {
                    ((Player) player.player()).sendMessage(Component.text(BetterKeepInventoryPaper.PREFIX + " " + message, BetterKeepInventoryPaper.TEXT_COLOR));
                }
                continue;
            }

            Options worldOptions = new Options();
            worldOptions.requirePermission = config.getBoolean("worlds." + worldName + ".require-permission", globalOptions.requirePermission);
            worldOptions.keepOnPlayerDeath = config.getBoolean("worlds." + worldName + ".keep-on-player-death", globalOptions.keepOnPlayerDeath);
            worldOptions.keepOnNaturalDeath = config.getBoolean("worlds." + worldName + ".keep-on-natural-death", globalOptions.keepOnNaturalDeath);
            worldOptions.keepOnMobDeath = config.getBoolean("worlds." + worldName + ".keep-on-mob-death", globalOptions.keepOnMobDeath);
            worldOptions.keepOnSuicide = config.getBoolean("worlds." + worldName + ".keep-on-suicide", globalOptions.keepOnSuicide);
            worldOptions.keepInventory = config.getBoolean("worlds." + worldName + ".keep-inventory-items-on-death", worldOptions.keepInventory);
            worldOptions.keepExp = config.getDouble("worlds." + worldName + ".keep-exp-on-death", worldOptions.keepExp);
            worlds.put(worldName, worldOptions);
        }
    }
}
