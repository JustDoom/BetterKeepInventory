# Better Keep Inventory

[![Discord](https://img.shields.io/discord/810752039470235688?style=for-the-badge&logo=discord&label=Discord&labelColor=grey&color=green)](https://discord.imjustdoom.com)
[![X (formerly Twitter) Follow](https://img.shields.io/twitter/follow/ImJustDoom?style=for-the-badge&logo=x&label=Follow!&color=gray)](https://x.com/ImJustDoom)
[![Static Badge](https://img.shields.io/badge/Backers-%20?style=for-the-badge&logo=ko-fi&color=gray)](https://ko-fi.com/justdoom) [![GitHub](https://img.shields.io/github/stars/JustDoom/BetterKeepInventory?style=for-the-badge&logo=github)](https://github.com/JustDoom/BetterKeepInventory)

Better Keep Inventory is a plugin that builds more functionality on top of the "Keep Inventory" gamerule idea.
The gamerule is very limited with the only configuration available is enabling/disabling it and having it work in specific worlds.
This plugin can handle those scenarios for you and more! Per world configuration and keeping your inventory in specific scenarios such as natural deaths, mob deaths or player deaths.
Whether your items are kept or dropped for whatever reason is up to you!

## Features

- Per world configuration
- Permissions
- Reloadable config without restarting the server
- Keep/Drop on PVP, Natural or Mob caused death!
- Paper and Sponge support

### Planned

- Configurable message on keep inventory

## Usage

Just install the plugin to your plugins folder for your platform (Paper or Sponge)

A player death is classified as a player other than yourself causing your death, whether it be from a sword, arrow, or hitting off a cliff.
As long as Minecraft classifies it as being killed by another player it is labelled a player death in the plugin.

A natural death is when you die from starvation, fall damage, drowning, or from the void. It should cover death from the `/kill` command too.

A mob death is when you are killed by a hostile mob, creeper, skeleton, spider etc.

### Commands

There is one command `/betterkeepinventory reload` which reloads the config file and applies the changes made.
It requires the permission `betterkeepinventory.commands`. You can also run `/bki reload` for short.

### Permissions

By default, every player will have the keep inventory functionality enabled. 
To only give it to specific people you can set the option `require-permission` to true and give only the players you want to have the keep inventory ability the permission `betterkeepinventory.keep`.
To do that you will need a permission plugin like [LuckPerms](https://luckperms.net/).

### Configuration

The Paper plugin uses a `.yml` file for configuration and the Sponge plugin uses a `.json` file.
For world specific settings the Sponge config needs the full world name such as `minecraft:overworld`.
Default world names are also different between Paper and Sponge

```yml
# NOTE: DO NOT HAVE THE KEEP INVENTORY GAMERULE ENABLED WITH THIS PLUGIN

# Global default settings that affect all worlds. Set all of them to false for vanilla functionality (No keep inventory)

# Should the permission "betterkeepinventory.keep" be required for a player to keep their inventory
# If false everyone will keep their inventory
require-permission: false
# Should the player keep their inventory when killed by another player
keep-on-player-death: false
# Should the player keep their inventory on a natural death. Fall damage, starving, drowning etc
keep-on-natural-death: true
# Should the player keep their inventory when killed by a mob
keep-on-mob-death: true

# World specific settings. These will override the above global settings for the specified world
worlds:
  # The name of the world
  world:
    # Should these settings override the global ones. Set to false to disable the override
    enabled: false
    require-permission: false
    keep-on-killed-by-player: false
    keep-on-natural-death: true
    keep-on-mob-death: true
  world_nether:
    enabled: false
    require-permission: true
    keep-on-killed-by-player: false
    # The "keep-on-natural-death" and "keep-on-mob-death" options have been left out. These two will default to the global
    # defaults set at the top of the file while the other settings that are specified will have whatever option is set
    # above inside the world section
```

<details>
  <summary>Example for only keeping your inventory in the nether no matter what</summary>

```yml
require-permission: false
keep-on-player-death: false
keep-on-natural-death: false
keep-on-mob-death: false

worlds:
  world_nether:
    enabled: true
    require-permission: false
    keep-on-killed-by-player: true
    keep-on-natural-death: true
    keep-on-mob-death: true
```
</details>

## Support

Support can be provided on my [discord server](https://discord.gg/ydGK5jYV6t) or you can report any bugs on the GitHub repository [here](https://github.com/JustDoom/BetterKeepInventory/issues)

## Sponsorship
[![WinterNode](https://i.imgur.com/RdDhfXF.png)](https://winterno.de/justdoom)
[WinterNode](https://winterno.de/justdoom) offers industry-leading performance, stability, and ease of use all at a great price! I have partnered with them and use their services because of their amazing customer support and reliability.
Use code **DinoNuggies** for 20% off your first 4 months!
