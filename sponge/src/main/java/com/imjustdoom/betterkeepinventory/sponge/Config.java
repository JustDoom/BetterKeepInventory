package com.imjustdoom.betterkeepinventory.sponge;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.gamerule.GameRules;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Config {
    private static GsonConfigurationLoader LOADER = GsonConfigurationLoader.builder().path(Path.of("config/betterkeepinventory/config.json")).build();
    public static class Options {
        public boolean requirePermission = false;
        public boolean keepOnPlayerDeath = false;
        public boolean keepOnNaturalDeath = true;
        public boolean keepOnMobDeath = true;
    }

    public static Options GLOBAL_OPTIONS;
    public static Map<String, Options> WORLDS = new HashMap<>();

    public static void init(Player player) {
        BasicConfigurationNode root;
        try {
            root = LOADER.load();
        } catch (IOException e) {
            System.err.println("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            System.exit(1);// TODO: Disable plugin instead
            return;
        }
        boolean wasEmpty = root.empty();

        GLOBAL_OPTIONS = new Options();
        WORLDS.clear();

        // Get default global options
        GLOBAL_OPTIONS.requirePermission = root.node("require-permission").getBoolean(GLOBAL_OPTIONS.requirePermission);
        GLOBAL_OPTIONS.keepOnPlayerDeath = root.node("keep-on-player-death").getBoolean(GLOBAL_OPTIONS.keepOnPlayerDeath);
        GLOBAL_OPTIONS.keepOnNaturalDeath = root.node("keep-on-natural-death").getBoolean(GLOBAL_OPTIONS.keepOnNaturalDeath);
        GLOBAL_OPTIONS.keepOnMobDeath = root.node("keep-on-mob-death").getBoolean(GLOBAL_OPTIONS.keepOnMobDeath);

        // Create initial world examples on first generate
        if (wasEmpty) {
            root.node("worlds", "minecraft:overworld", "enabled").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "require-permission").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "keep-on-player-death").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "keep-on-natural-death").getBoolean(true);
            root.node("worlds", "minecraft:overworld", "keep-on-mob-death").getBoolean(true);

            root.node("worlds", "minecraft:the_nether", "enabled").getBoolean(false);
            root.node("worlds", "minecraft:the_nether", "require-permission").getBoolean(true);
            root.node("worlds", "minecraft:the_nether", "keep-on-player-death").getBoolean(false);
        }

        // Get world specific options
        for (Map.Entry<Object, BasicConfigurationNode> node : root.node("worlds").childrenMap().entrySet()) {
            String worldName = node.getKey().toString();
            Optional<ServerWorld> world = Sponge.server().worldManager().world(ResourceKey.resolve(worldName));
            if (world.isEmpty()) {
                String message = "The world '" + worldName + "' was unable to be found. Please make sure you spelt it correctly.";
                BetterKeepInventorySponge.get().getLogger().warn(message);
                if (player != null) {
                    player.sendMessage(Component.text(BetterKeepInventorySponge.PREFIX + " " + message, BetterKeepInventorySponge.TEXT_COLOR));
                }
                continue;
            }

            if (!root.node("worlds", worldName, "enabled").getBoolean(false)) {
                continue;
            }

            if (world.get().properties().gameRule(GameRules.KEEP_INVENTORY.get())) {
                String message = "The world '" + worldName + "' has the 'Keep Inventory' gamerule enabled. This will mess with the functionality of the plugin in that world so we have skipped it. Please disable the gamerule and reload the plugin with '/bki reload' or restart the server";
                BetterKeepInventorySponge.get().getLogger().warn(message);
                if (player != null) {
                    player.sendMessage(Component.text(BetterKeepInventorySponge.PREFIX + " " + message, BetterKeepInventorySponge.TEXT_COLOR));
                }
                continue;
            }

            Options worldOptions = new Options();
            worldOptions.requirePermission = root.node("worlds", worldName, "require-permission").getBoolean(GLOBAL_OPTIONS.requirePermission);
            worldOptions.keepOnPlayerDeath = root.node("worlds", worldName, "keep-on-player-death").getBoolean(GLOBAL_OPTIONS.keepOnPlayerDeath);
            worldOptions.keepOnNaturalDeath = root.node("worlds", worldName, "keep-on-natural-death").getBoolean(GLOBAL_OPTIONS.keepOnNaturalDeath);
            worldOptions.keepOnMobDeath = root.node("worlds", worldName, "keep-on-mob-death").getBoolean(GLOBAL_OPTIONS.keepOnMobDeath);
            WORLDS.put(worldName, worldOptions);
        }

        try {
            LOADER.save(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
