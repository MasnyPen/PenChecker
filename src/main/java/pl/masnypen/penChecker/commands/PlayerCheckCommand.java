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
                YamlConfiguration checkspawn = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "checkspawn.yml"));

                if (!checkspawn.contains("world-name") || !checkspawn.contains("world") || !checkspawn.contains("x") || !checkspawn.contains("y") || !checkspawn.contains("z") || !checkspawn.contains("yaw") || !checkspawn.contains("pitch")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.no_spawn", "&6The spawn point has not been set!"));
                    return false;
                }
                if (sender.getName().equals(args[0])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.cannot_check_self", "&6You cannot check yourself!"));
                    return false;
                }
                Player player = Bukkit.getPlayerExact(args[0]);

                // sender
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("commands.sprawdz.senderMessage", "&6You are checking {player}").replace("{player}", player.getName()));

                // broadcast
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("commands.sprawdz.broadcastMessage", "&6{player} is being &cCHECKED &6by {sender}").replace("{player}", player.getName()).replace("{sender}", sender.getName()));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");

                int taskID = Bukkit.getScheduler().runTaskTimer(main, () -> {
                    player.sendMessage(main.getLangManager().getMessage("commands.sprawdz.target.message", "&6You are being checked by {sender}").replace("{sender}", sender.getName()));
                    player.sendTitle(main.getLangManager().getMessage("commands.sprawdz.target.title", "&4YOU ARE BEING CHECKED"), main.getLangManager().getMessage("commands.sprawdz.target.subtitle", "&7Join the Discord voice channel you're-being-checked"), 20, 130, 20);
                }, 0L, 200L).getTaskId();


                main.checkedList.put(player.getUniqueId(), new Checked(player.getLocation(), taskID, ((Player) sender).getUniqueId(), ((Player) sender).getLocation()));
                main.adminsChecked.put(((Player) sender).getUniqueId(), player.getUniqueId());
                Location spawn = new Location(Bukkit.getWorld(checkspawn.getString("world-name")), checkspawn.getDouble("x"),checkspawn.getDouble("y"), checkspawn.getDouble("z"), (float) checkspawn.getDouble("yaw"), (float) checkspawn.getDouble("pitch"));
                player.teleport(spawn);
                if (main.getConfig().getBoolean("admin_tp")) {
                    ((Player) sender).teleport(spawn);
                }

                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("commands.sprawdz.usage", "&6Incorrect command usage! /playercheck <player>") );
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + main.getLangManager().getMessage("general.command_not_from_console", "&6You cannot execute this command from the console."));
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
