package com.imjustdoom.betterkeepinventory.paper;

import com.imjustdoom.betterkeepinventory.common.BetterPlayer;
import org.bukkit.entity.Player;

public record PaperPlayer(Player player) implements BetterPlayer<Player> {
}
