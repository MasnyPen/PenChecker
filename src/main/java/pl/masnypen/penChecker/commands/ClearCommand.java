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
            if (main.getCheckManager().isAdminChecking(((Player) sender).getUniqueId())) {
                UUID targetId = main.getCheckManager().getCheckedByAdmin(((Player) sender).getUniqueId());
                Checked checked = main.getCheckManager().get(targetId);
                Player target = Bukkit.getPlayer(targetId);

                target.teleport(checked.getLocation());
                if (main.getConfig().getBoolean("admin_tp")) {
                    ((Player) sender).teleport(checked.getLocationSender());
                }

                Bukkit.getScheduler().cancelTask(checked.getTaskID());

                target.sendMessage(main.getLangManager().getMessage("commands.czysty.target.message", "&7You have been cleared of accusations!"));
                target.sendTitle(main.getLangManager().getMessage("commands.czysty.target.title", "&6You are clean!"), main.getLangManager().getMessage("commands.czysty.target.subtitle", "&7You have been cleared of accusations!"));

                sender.sendMessage(main.getLangManager().getMessage("commands.czysty.sender", "&6Successfully cleared &b{player}&6 of accusations!").replace("{player}", target.getName()));

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(main.getLangManager().getMessage("commands.czysty.broadcast", "&b{player}&6 is clean!").replace("{player}", target.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                main.getCheckManager().remove(targetId);
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.no_target_player", "&6You are not checking any player!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.command_not_from_console", "You cannot execute this command from the console."));
        }
        return false;
    }
}
