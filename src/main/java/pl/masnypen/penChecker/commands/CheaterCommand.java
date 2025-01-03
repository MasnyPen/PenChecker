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

public class CheaterCommand implements CommandExecutor {
    private Main main;
    public CheaterCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            if (main.getCheckManager().isAdminChecking(((Player) sender).getUniqueId())) {
                UUID targetId = main.getCheckManager().getCheckedByAdmin(((Player) sender).getUniqueId());
                Checked checked = main.getCheckManager().get(targetId);
                Player target = Bukkit.getPlayer(targetId);

                Bukkit.getScheduler().cancelTask(checked.getTaskID());
                if (main.getConfig().getBoolean("useCheckLocation")) {
                    target.teleport(checked.getLocation());
                    if (main.getConfig().getBoolean("admin_tp")) {
                        ((Player) sender).teleport(checked.getLocationSender());
                    }
                }

                sender.sendMessage(main.getLangManager().getMessage("commands.markguilty.sender", "&6Successfully convicted &b{player}&6!").replace("{player}", target.getName()));

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(main.getLangManager().getMessage("commands.markguilty.broadcast", "&b{player}&6 has been convicted!").replace("{player}", target.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                main.getCheckManager().remove(targetId);

                Bukkit.dispatchCommand(sender, main.getConfig().getString("cheaterCmd").replace("{player}", target.getName()));
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("commands.markguilty.no_target_player", "&6You are not checking any player!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.command_not_from_console", "You cannot execute this command from the console."));
        }

        return false;
    }
}
