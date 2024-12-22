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
            if (main.adminsChecked.get(((Player) sender).getUniqueId()) != null) {
                UUID targetId = main.adminsChecked.get(((Player) sender).getUniqueId());
                Checked checked = main.checkedList.get(targetId);
                Player target = Bukkit.getPlayer(targetId);

                Bukkit.getScheduler().cancelTask(checked.taskID);
                target.teleport(checked.location);

                sender.sendMessage(main.langManager.getMessage("commands.skazany.sender", "&6Pomyślnie skazano &b{player}&6!").replace("{player}", target.getName()));

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(main.langManager.getMessage("commands.skazany.broadcast", "&b{player}&6 został skazany!").replace("{player}", target.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                Bukkit.dispatchCommand(sender, main.getConfig().getString("cheaterCmd").replace("{player}", target.getName()));

                main.checkedList.remove(targetId);
                main.adminsChecked.remove(((Player) sender).getUniqueId());
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("skazany.no_target_player", "&6Nie sprawdzasz żadnego gracza!") );
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("general.command_not_from_console", "Nie możesz wykonać tej komendy z konsoli."));
        }

        return false;
    }
}
