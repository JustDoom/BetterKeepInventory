package com.imjustdoom.betterkeepinventory.listener;

import com.imjustdoom.betterkeepinventory.BetterKeepInventory;
import com.imjustdoom.betterkeepinventory.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (Config.PERMISSION && !player.hasPermission("betterkeepinventory.keep")) return;
        if (!Config.WORLDS.contains(player.getWorld().getName())) return;

        if (killer == null) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}