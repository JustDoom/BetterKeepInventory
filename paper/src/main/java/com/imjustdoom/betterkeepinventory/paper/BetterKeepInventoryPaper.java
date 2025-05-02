package com.imjustdoom.betterkeepinventory.paper;

import com.imjustdoom.betterkeepinventory.common.Configuration;
import com.imjustdoom.betterkeepinventory.paper.listener.PlayerDeathListener;
import com.imjustdoom.betterkeepinventory.paper.listener.ReloadListener;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BetterKeepInventoryPaper extends JavaPlugin {
    public static String PREFIX = "[BKI]";
    public static TextColor TEXT_COLOR = TextColor.color(96, 179, 255);

    private final Configuration pluginConfig;

    public BetterKeepInventoryPaper() {
        this.pluginConfig = new PaperConfig();
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading config...");
        getPluginConfig().init(null);
        getLogger().info("Loaded config");

        getLogger().info("Registering commands and events...");
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            LiteralCommandNode<CommandSourceStack> buildCommand = Commands.literal("betterkeepinventory")
                    .requires(sender -> sender.getSender().hasPermission("betterkeepinventory.commands"))
                    .executes(ctx -> {
                        ctx.getSource().getSender().sendMessage(Component.text(PREFIX + " BetterKeepInventory version " + getPluginMeta().getVersion(), TEXT_COLOR));
                        return Command.SINGLE_SUCCESS;
                    }).then(Commands.literal("reload").executes(ctx -> {
                        getPluginConfig().init(ctx.getSource().getSender() instanceof Player player ? new PaperPlayer(player) : null);
                        ctx.getSource().getSender().sendMessage(Component.text(PREFIX + " BetterKeepInventory has been reloaded!", TEXT_COLOR));
                        return Command.SINGLE_SUCCESS;
                    }))
                    .build();
            commands.registrar().register(buildCommand, List.of("bki"));
        });
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getLogger().info("Registered commands and events");

        getLogger().info("Checking for BetterReload...");
        if (getServer().getPluginManager().getPlugin("BetterReload") != null) {
            getLogger().info("BetterReload found, registering event...");
            getServer().getPluginManager().registerEvents(new ReloadListener(), this);
            getLogger().info("BetterReload event registered");
        } else {
            getLogger().info("BetterReload was not found so support for it will not be enabled");
        }

        Metrics metrics = new Metrics(this, 25697);
        metrics.addCustomChart(new SingleLineChart("overridden_worlds", () -> getPluginConfig().worlds.size()));
    }

    public Configuration getPluginConfig() {
        return this.pluginConfig;
    }

    private static BetterKeepInventoryPaper INSTANCE;
    public static BetterKeepInventoryPaper get() {
        return INSTANCE;
    }
}
