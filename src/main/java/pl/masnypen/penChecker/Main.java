package pl.masnypen.penChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import pl.masnypen.penChecker.commands.CheaterCommand;
import pl.masnypen.penChecker.commands.ClearCommand;
import pl.masnypen.penChecker.commands.PlayerCheckCommand;
import pl.masnypen.penChecker.commands.SetCheckLocationCommand;
import pl.masnypen.penChecker.events.CheckedLogout;
import pl.masnypen.penChecker.events.isCheckerEvent;
import pl.masnypen.penChecker.manager.LangManager;
import pl.masnypen.penChecker.utils.Checked;

import java.util.HashMap;
import java.util.UUID;


public final class Main extends JavaPlugin {
    public LangManager langManager;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        this.langManager = new LangManager(getDataFolder());

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Zostal wlaczony!");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "----------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  PenChecker!");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  Wersja 1.0");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "----------------");

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Zostal wylaczony!");
    }

    public void registerCommands() {
        getCommand("ustawsprawdzania").setExecutor(new SetCheckLocationCommand(this));
        PlayerCheckCommand playerCheckCommand = new PlayerCheckCommand(this);
        getCommand("sprawdz").setExecutor(playerCheckCommand);
        getCommand("sprawdz").setTabCompleter(playerCheckCommand);
        getCommand("czysty").setExecutor(new ClearCommand(this));
        getCommand("skazany").setExecutor(new CheaterCommand(this));
    }
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new isCheckerEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckedLogout(this), this);
    }

    @Override
    public void reloadConfig() {

        this.langManager = new LangManager(getDataFolder());

        super.reloadConfig();
    }


    public HashMap<UUID, Checked> checkedList = new HashMap<>();
    public HashMap<UUID, UUID> adminsChecked = new HashMap<>();
}
