package com.imjustdoom.betterkeepinventory.common;

import java.util.HashMap;
import java.util.Map;

public abstract class Configuration {
    public static class Options {
        public boolean requirePermission = false;
        public boolean keepOnPlayerDeath = false;
        public boolean keepOnNaturalDeath = true;
        public boolean keepOnMobDeath = true;
    }

    public Options globalOptions;
    public Map<String, Options> worlds = new HashMap<>();

    public abstract void init(BetterPlayer<?> player);
}
