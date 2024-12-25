package pl.masnypen.penChecker.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.masnypen.penChecker.Main;
import pl.masnypen.penChecker.utils.Checked;

import java.util.UUID;

public class ClearCommand implements CommandExecutor {
    private Main main;
    public ClearCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (main.adminsChecked.get(((Player) sender).getUniqueId()) != null) {
                UUID targetId = main.adminsChecked.get(((Player) sender).getUniqueId());
                Checked checked = main.checkedList.get(targetId);
                Player target = Bukkit.getPlayer(targetId);

                target.teleport(checked.location);
                if (main.getConfig().getBoolean("admin_tp")) {
                    ((Player) sender).teleport(checked.locationSender);
                }

                Bukkit.getScheduler().cancelTask(checked.taskID);

                target.sendMessage(main.langManager.getMessage("commands.czysty.target.message", "&7You have been cleared of accusations!"));
                target.sendTitle(main.langManager.getMessage("commands.czysty.target.title", "&6You are clean!"), main.langManager.getMessage("commands.czysty.target.subtitle", "&7You have been cleared of accusations!"));

                sender.sendMessage(main.langManager.getMessage("commands.czysty.sender", "&6Successfully cleared &b{player}&6 of accusations!").replace("{player}", target.getName()));

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(main.langManager.getMessage("commands.czysty.broadcast", "&b{player}&6 is clean!").replace("{player}", target.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                main.checkedList.remove(targetId);
                main.adminsChecked.remove(((Player) sender).getUniqueId());
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("general.no_target_player", "&6You are not checking any player!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("general.command_not_from_console", "You cannot execute this command from the console."));
        }
        return false;
    }
}
