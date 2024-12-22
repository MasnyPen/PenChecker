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

            Bukkit.getScheduler().cancelTask(checked.taskID);
            target.teleport(checked.location);

            target.sendMessage(main.langManager.getMessage("commands.czysty.target.message", "&7Oczyszczono cie z zarzutów!"));
            target.sendTitle(main.langManager.getMessage("commands.czysty.target.title", "&6Jesteś czysty!"), main.langManager.getMessage("commands.sprawdz.target.subtitle", "&7Oczyszczono cie z zarzutów!"));

            sender.sendMessage(main.langManager.getMessage("commands.czysty.sender", "&6Pomyślnie oczyszczono &b{player}&6 z zarzutów!").replace("{player}", target.getName()));
            Bukkit.broadcastMessage(main.langManager.getMessage("commands.czysty.broadcast", "&b{player}&6 jest czysty!").replace("{player}", target.getName()));

            main.checkedList.remove(targetId);
            main.adminsChecked.remove(((Player) sender).getUniqueId());
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("commands.czysty.no_target_player", "&6Nie sprawdzasz żadnego gracza!") );
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("general.command_not_from_console", "Nie możesz wykonać tej komendy z konsoli."));
        }
        return false;
    }
}
