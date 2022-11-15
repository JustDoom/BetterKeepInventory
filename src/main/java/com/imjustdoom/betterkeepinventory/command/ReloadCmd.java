package com.imjustdoom.betterkeepinventory.command;

import com.imjustdoom.betterkeepinventory.config.Config;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCmd extends SubCommand {

    /**
     * @param sender
     * @param strings
     */
    @Override
    public void execute(CommandSender sender, String[] strings) {
        Config.init();
        sender.sendMessage("§aReloaded config!");
    }
}
