package com.imjustdoom.betterkeepinventory.listener;

import com.imjustdoom.betterkeepinventory.config.Config;
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
        Config.Options worldOptions = Config.WORLDS.getOrDefault(player.getWorld().getName(), Config.GLOBAL_OPTIONS);
        if (worldOptions.requirePermission && !player.hasPermission("betterkeepinventory.keep")) {
            return;
        }

        Entity killer = player.getLastDamageCause().getDamageSource().getCausingEntity();
        if ((worldOptions.keepOnNaturalDeath && killer == null)
                || (worldOptions.keepOnMobDeath && killer instanceof Mob)
                || (worldOptions.keepOnPlayerDeath && killer instanceof Player)) {
            event.setKeepInventory(true); // TODO: Maybe allow specifying what to save. So you could have players keep experience but not inventory
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}