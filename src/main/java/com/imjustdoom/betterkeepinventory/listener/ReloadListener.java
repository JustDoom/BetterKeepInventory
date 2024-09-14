package com.imjustdoom.betterkeepinventory.listener;

import better.reload.api.ReloadEvent;
import com.imjustdoom.betterkeepinventory.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadListener implements Listener {

    @EventHandler
    public void onReloadEvent(ReloadEvent event) {
        Config.init();
        event.getCommandSender().sendMessage("§aReloaded config!");
    }
}
