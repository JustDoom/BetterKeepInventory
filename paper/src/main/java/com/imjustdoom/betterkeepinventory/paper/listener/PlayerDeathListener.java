package com.imjustdoom.betterkeepinventory.paper.listener;

import com.imjustdoom.betterkeepinventory.paper.BetterKeepInventoryPaper;
import com.imjustdoom.betterkeepinventory.paper.PaperConfig;
import io.papermc.paper.world.damagesource.CombatEntry;
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

        System.out.println(player.getCombatTracker().computeMostSignificantFall());
        System.out.println(player.getCombatTracker().getCombatEntries());
        System.out.println(player.getCombatTracker());
        System.out.println(player.getCombatTracker().isInCombat() + " - " + player.getCombatTracker().isTakingDamage());
        Entity killer = player.getLastDamageCause().getDamageSource().getCausingEntity();
//        Entity tracker = player.getCombatTracker().getCombatEntries().

        if ((worldOptions.keepOnNaturalDeath && killer == null)
                || (worldOptions.keepOnMobDeath && killer instanceof Mob)
                || (worldOptions.keepOnPlayerDeath && killer instanceof Player && player != killer)
                || (worldOptions.keepOnSuicide && killer == player)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}