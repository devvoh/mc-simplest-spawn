package com.devvoh.simplestspawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimplestSpawnTools {
    public static Boolean isCommand(Command command, String $commandString)
    {
        return command.getName().toLowerCase().equals($commandString);
    }

    public static Boolean isPlayer(CommandSender sender)
    {
        return sender instanceof Player;
    }

    public static Player getPlayer(CommandSender sender)
    {
        return (Player) sender;
    }
}
