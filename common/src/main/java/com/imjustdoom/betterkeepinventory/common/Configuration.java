package com.imjustdoom.betterkeepinventory.common;

import java.util.HashMap;
import java.util.Map;

public abstract class Configuration {
    public static class Options { // TODO: A way to have options for people with permissions and those without
        public boolean requirePermission = false;
        public boolean keepOnPlayerDeath = false;
        public boolean keepOnNaturalDeath = true;
        public boolean keepOnMobDeath = true;
        public boolean keepOnSuicide = false;

        public boolean keepInventory = true;
        public double keepExp = 1;
    }

    public Options globalOptions;
    public Map<String, Options> worlds = new HashMap<>();

    public abstract void init(BetterPlayer<?> player);
}
