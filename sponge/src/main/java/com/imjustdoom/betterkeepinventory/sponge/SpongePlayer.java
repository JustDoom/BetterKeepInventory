package com.imjustdoom.betterkeepinventory.sponge;

import com.imjustdoom.betterkeepinventory.common.BetterPlayer;
import org.spongepowered.api.entity.living.player.Player;

public record SpongePlayer(Player player) implements BetterPlayer<Player> {
}
