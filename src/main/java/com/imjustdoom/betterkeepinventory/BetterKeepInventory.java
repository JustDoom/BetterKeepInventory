package com.imjustdoom.betterkeepinventory;

import com.imjustdoom.betterkeepinventory.command.BetterKeepInventoryCmd;
import com.imjustdoom.betterkeepinventory.config.Config;
import com.imjustdoom.betterkeepinventory.listener.PlayerDeathListener;
import com.imjustdoom.cmdinstruction.CMDInstruction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterKeepInventory extends JavaPlugin {

    private static BetterKeepInventory INSTANCE;

    public static BetterKeepInventory get() {
        return INSTANCE;
    }

    public BetterKeepInventory() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Config.init();

        CMDInstruction.registerCommands(this, new BetterKeepInventoryCmd().setName("betterkeepinventory").setPermission("betterkeepinventory.commands"));

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
