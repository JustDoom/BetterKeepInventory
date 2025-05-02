package com.imjustdoom.betterkeepinventory.sponge;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("betterkeepinventory")
public class BetterKeepInventorySponge {
    private static BetterKeepInventorySponge INSTANCE;
    public static String PREFIX = "[BKI]";
    public static TextColor TEXT_COLOR = TextColor.color(96, 179, 255);

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    public static BetterKeepInventorySponge get() {
        return INSTANCE;
    }

    public BetterKeepInventorySponge() {
        INSTANCE = this;
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        getLogger().info("Registering commands...");
        // Reload command
        Command.Parameterized reloadCommand = Command.builder()
                .executor(context -> {
                    Config.init(context.cause() instanceof Player player ? player : null);
                    context.sendMessage(Component.text(PREFIX + " BetterKeepInventory has been reloaded!", TEXT_COLOR));
                    return CommandResult.success();
                })
                .permission("betterkeepinventory.commands")
                .shortDescription(Component.text("BetterKeepInventory Reload Config"))
                .build();

        // Base command
        event.register(this.container, Command.builder()
                        .executor(context -> {
                            context.sendMessage(Component.text(PREFIX + " BetterKeepInventory version " + getContainer().metadata().version(), TEXT_COLOR));
                            return CommandResult.success();
                        })
                        .permission("betterkeepinventory.commands")
                        .shortDescription(Component.text("Hello World Command"))
                        .addChild(reloadCommand, "reload")
                        .build(),
                "betterkeepinventory", "bki");
        getLogger().info("Registered commands");
    }

    @Listener
    public void onServerStart(final StartedEngineEvent<Server> event) {
        getLogger().info("Loading config...");
        Config.init(null);
        getLogger().info("Loaded config");

        // TODO: Metrics
    }

    // TODO: Double check this gets auto unloaded when needed since it auto loads in this @Plugin class
    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event) {
        if (!(event.entity() instanceof ServerPlayer player)) {
            return;
        }

        Config.Options worldOptions = Config.WORLDS.getOrDefault(player.serverLocation().worldKey().formatted(), Config.GLOBAL_OPTIONS);
        if (worldOptions.requirePermission && !player.hasPermission("betterkeepinventory.keep")) {
            return;
        }

        Entity killer = null;
        if (player.lastAttacker().isPresent()) {
            killer = player.lastAttacker().get().get();
        }
        if ((worldOptions.keepOnNaturalDeath && killer == null)
                || (worldOptions.keepOnMobDeath && killer instanceof Hostile)
                || (worldOptions.keepOnPlayerDeath && killer instanceof Player)) {
            event.setKeepInventory(true);
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    public PluginContainer getContainer() {
        return this.container;
    }
}
