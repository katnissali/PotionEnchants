package me.BigMarco254.potionenchants.commands;

import me.BigMarco254.potionenchants.menus.EnchanterMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PEnchantsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage("must be player");
        else new EnchanterMenu((Player) sender);
        return false;
    }
}
