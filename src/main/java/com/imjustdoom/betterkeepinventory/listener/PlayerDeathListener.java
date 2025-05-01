package com.imjustdoom.betterkeepinventory.listener;

import com.imjustdoom.betterkeepinventory.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (Config.PERMISSION && !player.hasPermission("betterkeepinventory.keep")) {
            return;
        }
        if (Config.SPECIFIC_WORLDS && !Config.WORLDS.contains(player.getWorld().getName())) {
            return;
        }

        if (player.getKiller() == null) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}