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

        // Get world specific options, if there are none default to global
        PaperConfig.Options options = BetterKeepInventoryPaper.get().getPluginConfig().worlds.getOrDefault(player.getWorld().getName(), BetterKeepInventoryPaper.get().getPluginConfig().globalOptions);
        if (options.requirePermission && !player.hasPermission("betterkeepinventory.keep")) {
            return;
        }

        Entity killer = player.getLastDamageCause().getDamageSource().getCausingEntity();
        if ((options.keepOnNaturalDeath && killer == null)
                || (options.keepOnMobDeath && killer instanceof Mob)
                || (options.keepOnPlayerDeath && killer instanceof Player && player != killer)
                || (options.keepOnSuicide && killer == player)) {
            if (options.keepInventory) {
                event.setKeepInventory(true);
                event.getDrops().clear();
            }
            if (options.keepExp == 1) {
                event.setKeepLevel(true);
                event.setShouldDropExperience(false);
            } else if (options.keepExp != 0) {
                event.setDroppedExp((int) (event.getDroppedExp() * (1 - options.keepExp)));
                event.setNewExp((int) (player.calculateTotalExperiencePoints() * options.keepExp));
            }
        }
    }
}