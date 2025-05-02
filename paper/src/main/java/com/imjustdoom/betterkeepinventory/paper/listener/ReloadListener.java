package com.imjustdoom.betterkeepinventory.paper.listener;

import better.reload.api.ReloadEvent;
import com.imjustdoom.betterkeepinventory.paper.BetterKeepInventoryPaper;
import com.imjustdoom.betterkeepinventory.paper.PaperPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadListener implements Listener {
    @EventHandler
    public void onReloadEvent(ReloadEvent event) {
        BetterKeepInventoryPaper.get().getPluginConfig().init(event.getCommandSender() instanceof Player player ? new PaperPlayer(player) : null);
        event.getCommandSender().sendMessage(Component.text(BetterKeepInventoryPaper.PREFIX + " BetterKeepInventory has been reloaded!", BetterKeepInventoryPaper.TEXT_COLOR));
    }
}
