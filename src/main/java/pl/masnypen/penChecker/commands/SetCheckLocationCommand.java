package pl.masnypen.penChecker.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.masnypen.penChecker.Main;

import java.io.File;
import java.io.IOException;

public class SetCheckLocationCommand implements CommandExecutor {
    private Main main;

    public SetCheckLocationCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (!main.getConfig().getBoolean("useCheckLocation")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.getLangManager().getMessage("commands.spawn.cannot_set","&eYou can't set spawn." ));
                return false;
            }

            File file = new File(main.getDataFolder(), "checkspawn.yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("Can't load file! Error.");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.getLangManager().getMessage("commands.spawn.error","&eAn error occurred while saving" ));
                    return false;
                }
            }

            YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
            modifyFile.set("world-name",player.getLocation().getWorld().getName());
            modifyFile.set("world", player.getLocation().getWorld().getUID().toString());
            modifyFile.set("x", player.getLocation().getX());
            modifyFile.set("y", player.getLocation().getY());
            modifyFile.set("z", player.getLocation().getZ());
            modifyFile.set("yaw", player.getLocation().getYaw());
            modifyFile.set("pitch", player.getLocation().getPitch());

            try {
                modifyFile.save(file);
            } catch (IOException e) {
                System.out.println("Can't save file! Error.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.getLangManager().getMessage("commands.spawn.error","&eAn error occurred while saving" ));
                return false;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.getLangManager().getMessage("commands.spawn.set", "&6Check spawn has been set!"));

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix")) + " " + this.main.getLangManager().getMessage("general.command_not_from_console", "&eYou cannot execute this command from the console."));
        }

        return false;
    }
}
