package com.imjustdoom.betterkeepinventory.listener;

import better.reload.api.ReloadEvent;
import com.imjustdoom.betterkeepinventory.BetterKeepInventory;
import com.imjustdoom.betterkeepinventory.config.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadListener implements Listener {
    @EventHandler
    public void onReloadEvent(ReloadEvent event) {
        Config.init(event.getCommandSender() instanceof Player player ? player : null);
        event.getCommandSender().sendMessage(Component.text(BetterKeepInventory.PREFIX + " BetterKeepInventory has been reloaded!", BetterKeepInventory.TEXT_COLOR));
    }
}
