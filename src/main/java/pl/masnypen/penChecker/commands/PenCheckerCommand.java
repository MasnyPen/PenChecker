package pl.masnypen.penChecker.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.masnypen.penChecker.Main;

import java.util.List;

public class PenCheckerCommand implements CommandExecutor, TabCompleter {

    Main main;
    public PenCheckerCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            showHelp(sender);
            return false;
        } else
            switch (args[0].toLowerCase()) {
                case "reload":
                    if (sender.hasPermission("pencore.reload")) {
                        reloadPlugin(sender);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.langManager.getMessage("general.no_permission", "&6Nie masz uprawnień do wykonania tej komendy."));
                    }
                    return true;
                case "help":
                    showHelp(sender);
                    return true;
                case "version":
                    showVersion(sender);
                    return true;
                default:
                    showHelp(sender);
                    return false;
            }
    }
    private void reloadPlugin(CommandSender sender) {
        main.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.langManager.getMessage("general.reload", "&&6Konfiguracja została przeładowana."));
    }


    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "Dostępne komendy:");
        sender.sendMessage(ChatColor.GOLD + "/penchecker reload - Przeładuj plugin");
        sender.sendMessage(ChatColor.GOLD + "/penchecker help - Wyświetl pomoc");
        sender.sendMessage(ChatColor.GOLD + "/penchecker version - Wyświetl wersję pluginu");
        sender.sendMessage(ChatColor.GOLD + "/penchecker version - Wyświetl wersję pluginu");
    }
    private void showVersion(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + ChatColor.GOLD + "Plugin PenCore w wersji" + " " + ChatColor.RED + main.getDescription().getVersion());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of("reload", "version", "help");
    }
}
