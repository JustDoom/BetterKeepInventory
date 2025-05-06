package com.imjustdoom.betterkeepinventory.sponge;

import com.imjustdoom.betterkeepinventory.common.BetterPlayer;
import com.imjustdoom.betterkeepinventory.common.Configuration;
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
import java.util.Map;
import java.util.Optional;

public class SpongeConfig extends Configuration {
    private static GsonConfigurationLoader LOADER = GsonConfigurationLoader.builder().path(Path.of("config/betterkeepinventory/config.json")).build();

    public void init(BetterPlayer<?> player) {
        BasicConfigurationNode root;
        try {
            root = LOADER.load();
        } catch (IOException e) {
            BetterKeepInventorySponge.get().getLogger().error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            BetterKeepInventorySponge.get().setDisabled(true);
            return;
        }
        boolean wasEmpty = root.empty();

        globalOptions = new Options();
        worlds.clear();

        // Get default global options
        globalOptions.requirePermission = root.node("require-permission").getBoolean(globalOptions.requirePermission);
        globalOptions.keepOnPlayerDeath = root.node("keep-on-player-death").getBoolean(globalOptions.keepOnPlayerDeath);
        globalOptions.keepOnNaturalDeath = root.node("keep-on-natural-death").getBoolean(globalOptions.keepOnNaturalDeath);
        globalOptions.keepOnMobDeath = root.node("keep-on-mob-death").getBoolean(globalOptions.keepOnMobDeath);
        globalOptions.keepOnSuicide = root.node("keep-on-suicide").getBoolean(globalOptions.keepOnSuicide);

        // Create initial world examples on first generate
        if (wasEmpty) {
            root.node("worlds", "minecraft:overworld", "enabled").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "require-permission").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "keep-on-player-death").getBoolean(false);
            root.node("worlds", "minecraft:overworld", "keep-on-natural-death").getBoolean(true);
            root.node("worlds", "minecraft:overworld", "keep-on-mob-death").getBoolean(true);
            root.node("worlds", "minecraft:overworld", "keep-on-suicide").getBoolean(false);

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
                    ((Player) player.player()).sendMessage(Component.text(BetterKeepInventorySponge.PREFIX + " " + message, BetterKeepInventorySponge.TEXT_COLOR));
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
                    ((Player) player.player()).sendMessage(Component.text(BetterKeepInventorySponge.PREFIX + " " + message, BetterKeepInventorySponge.TEXT_COLOR));
                }
                continue;
            }

            Options worldOptions = new Options();
            worldOptions.requirePermission = root.node("worlds", worldName, "require-permission").getBoolean(globalOptions.requirePermission);
            worldOptions.keepOnPlayerDeath = root.node("worlds", worldName, "keep-on-player-death").getBoolean(globalOptions.keepOnPlayerDeath);
            worldOptions.keepOnNaturalDeath = root.node("worlds", worldName, "keep-on-natural-death").getBoolean(globalOptions.keepOnNaturalDeath);
            worldOptions.keepOnMobDeath = root.node("worlds", worldName, "keep-on-mob-death").getBoolean(globalOptions.keepOnMobDeath);
            worldOptions.keepOnSuicide = root.node("worlds", worldName, "keep-on-suicide").getBoolean(globalOptions.keepOnSuicide);
            worlds.put(worldName, worldOptions);
        }

        try {
            LOADER.save(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
