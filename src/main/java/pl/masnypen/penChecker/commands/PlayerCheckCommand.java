package pl.masnypen.penChecker.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.masnypen.penChecker.Main;
import pl.masnypen.penChecker.utils.Checked;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerCheckCommand implements CommandExecutor, TabCompleter {
    private Main main;
    public PlayerCheckCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1 && Bukkit.getPlayerExact(args[0]) != null) {
                Player player = Bukkit.getPlayerExact(args[0]);

                // sender
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("commands.sprawdz.senderMessage", "&6Sprawdzasz {player}").replace("{player}", player.getName()));

                // broadcast
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("commands.sprawdz.broadcastMessage", "&6{player} Jest &cSPRAWDZANY &6przez {sender}").replace("{player}", player.getName()).replace("{sender}", sender.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                int taskID = Bukkit.getScheduler().runTaskTimer(main, () -> {
                    player.sendMessage(main.langManager.getMessage("commands.sprawdz.target.message", "&6Jesteś sprawdzany przez {sender}").replace("{sender}", sender.getName()));
                    player.sendTitle(main.langManager.getMessage("commands.sprawdz.target.title", "&4JESTES SPRAWDZANY"), main.langManager.getMessage("commands.sprawdz.target.subtitle", "&7Wbij na discorda na kanal głosowy jestes-sprawdzany"), 20, 130, 20);
                }, 0L, 200L).getTaskId();


                main.checkedList.put(player.getUniqueId(), new Checked(player.getLocation(), taskID));
                main.adminsChecked.put(((Player) sender).getUniqueId(), player.getUniqueId());

                YamlConfiguration checkspawn = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "checkspawn.yml"));
                player.teleport(new Location(Bukkit.getWorld(checkspawn.getString("world-name")), checkspawn.getDouble("x"),checkspawn.getDouble("y"), checkspawn.getDouble("z"), (float) checkspawn.getDouble("yaw"), (float) checkspawn.getDouble("pitch")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("commands.sprawdz.usage", "&6Złe użycie komendy! /playercheck <gracz>") );
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.langManager.getMessage("general.command_not_from_console", "Nie możesz wykonać tej komendy z konsoli."));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList tabComplete = new ArrayList();

        for (Player player : Bukkit.getOnlinePlayers()) {
            tabComplete.add(player.getName());
        }
        return tabComplete;
    }
}
