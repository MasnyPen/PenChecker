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
                    if (sender.hasPermission("pencore.admin") || sender.isOp()) {
                        reloadPlugin(sender);
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + ChatColor.GOLD +" You do not have permission to execute this command.");
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
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.langManager.getMessage("general.reload", "&6The configuration has been reloaded."));
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "Available commands:");
        sender.sendMessage(ChatColor.GOLD + "/penchecker reload - Reload the plugin");
        sender.sendMessage(ChatColor.GOLD + "/penchecker help - Display help");
        sender.sendMessage(ChatColor.GOLD + "/penchecker version - Display the plugin version");
        sender.sendMessage(ChatColor.GOLD + "/penchecker setchecklocation - Set the location where players will be checked");
        sender.sendMessage(ChatColor.GOLD + "/penchecker startcheck <player> - Start checking a specific player");
        sender.sendMessage(ChatColor.GOLD + "/penchecker markclean <player> - Mark the player as clean");
        sender.sendMessage(ChatColor.GOLD + "/penchecker markguilty <player> - Mark the player as guilty (cheater)");
    }

    private void showVersion(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + ChatColor.GOLD + "Plugin PenCore version" + " " + ChatColor.RED + main.getDescription().getVersion());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of("reload", "version", "help");
    }
}
