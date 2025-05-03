package com.imjustdoom.betterkeepinventory.paper.listener;

import com.imjustdoom.betterkeepinventory.paper.BetterKeepInventoryPaper;
import com.imjustdoom.betterkeepinventory.paper.PaperConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PaperConfig.Options worldOptions = BetterKeepInventoryPaper.get().getPluginConfig().worlds.getOrDefault(player.getWorld().getName(), BetterKeepInventoryPaper.get().getPluginConfig().globalOptions);
        if (worldOptions.requirePermission && !player.hasPermission("betterkeepinventory.keep")) {
            return;
        }

        Entity killer = player.getLastDamageCause().getDamageSource().getCausingEntity();
        if ((worldOptions.keepOnNaturalDeath && killer == null)
                || (worldOptions.keepOnMobDeath && killer instanceof Mob)
                || (worldOptions.keepOnPlayerDeath && killer instanceof Player)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}