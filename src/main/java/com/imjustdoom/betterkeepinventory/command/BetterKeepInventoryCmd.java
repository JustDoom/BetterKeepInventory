package com.imjustdoom.betterkeepinventory.command;

import com.imjustdoom.cmdinstruction.Command;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class BetterKeepInventoryCmd extends Command {

    public BetterKeepInventoryCmd() {
        setSubcommands(new ReloadCmd().setName("reload").setPermission("betterkeepinventory.reload"));

        List<String> tabCompletions = new ArrayList<>();
        for (SubCommand subCommand : getSubcommands()) {
            tabCompletions.add(subCommand.getName());
        }
        setTabCompletions(tabCompletions);
    }

    /**
     * @param sender
     * @param strings
     */
    @Override
    public void execute(CommandSender sender, String[] strings) {
        sender.sendMessage("§c/betterkeepinventory reload");
    }
}
