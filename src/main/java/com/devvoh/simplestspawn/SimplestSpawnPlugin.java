package com.devvoh.simplestspawn;

import org.bukkit.plugin.java.JavaPlugin;

public class SimplestSpawnPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getCommand("spawn").setExecutor(new SimplestSpawnCommand(this));
        getCommand("setspawn").setExecutor(new SimplestSpawnCommand(this));
    }
}
