package com.devvoh.simplestspawn;

import org.bukkit.ChatColor;import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SimplestSpawnCommand implements CommandExecutor
{
    final private SimplestSpawnPlugin plugin;
    final int cooldown = 5;
    HashMap<String, Long> spawnLastUsed = new HashMap<>();

    public SimplestSpawnCommand(SimplestSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!com.devvoh.simplestspawn.SimplestSpawnTools.isPlayer(sender)) {
            return false;
        }

        Player player = com.devvoh.simplestspawn.SimplestSpawnTools.getPlayer(sender);

        if (com.devvoh.simplestspawn.SimplestSpawnTools.isCommand(command, "spawn")) {
            return sendToSpawn(player);
        }

        if (com.devvoh.simplestspawn.SimplestSpawnTools.isCommand(command, "setspawn")) {
            return this.setSpawn(player);
        }

        return false;
    }

    private Boolean sendToSpawn(Player player) {
        double x = this.plugin.getConfig().getDouble("x");
        double y = this.plugin.getConfig().getDouble("y");
        double z = this.plugin.getConfig().getDouble("z");
        int yaw = this.plugin.getConfig().getInt("yaw");
        int pitch = this.plugin.getConfig().getInt("pitch");

        if (x == 0 && y == 0 && z == 0) {
            player.sendMessage(
                "§5[SimplestSpawn]§c[ERROR] §eNo spawn set! First run /setspawn to set your current location as spawn."
            );
            return false;
        }

        player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        String playerUUID = player.getUniqueId().toString();
        Long currentTimestamp = System.currentTimeMillis() / 1000L;
        Long playerLastUsed = this.spawnLastUsed.get(playerUUID);

        if (playerLastUsed != null && (currentTimestamp - playerLastUsed) < this.cooldown) {
            player.sendMessage(
                "§5[SimplestSpawn]§d[NOPE] §ePlease wait at least " + this.cooldown + " seconds before using /spawn again."
            );
            return false;
        }

        spawnLastUsed.put(playerUUID, currentTimestamp);

        return true;
    }

    private Boolean setSpawn(Player player) {
        Double x = player.getLocation().getX();
        Double y = player.getLocation().getY() + 0.25; // bump it a little
        Double z = player.getLocation().getZ();
        Float yaw = player.getLocation().getYaw();
        Float pitch = player.getLocation().getPitch();

        player.sendMessage("§5[SimplestSpawn]§a[OK] §eSet spawn to: "
                + String.format("%.2f", x)
                + " "
                + String.format("%.2f", y)
                + " "
                + String.format("%.2f", z)
                + " ("
                + String.format("%.2f", yaw)
                + " "
                + String.format("%.2f", pitch)
                + ")"
        );

        this.plugin.getConfig().set("x", x);
        this.plugin.getConfig().set("y", y);
        this.plugin.getConfig().set("z", z);
        this.plugin.getConfig().set("yaw", yaw);
        this.plugin.getConfig().set("pitch", pitch);

        this.plugin.saveConfig();

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        return true;
    }
}
