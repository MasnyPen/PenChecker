package pl.masnypen.penChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.masnypen.penChecker.commands.SetCheckLocationCommand;
import pl.masnypen.penChecker.manager.LangManager;


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
        getCommand("setchecklocation").setExecutor(new SetCheckLocationCommand(this));
    }
    public void registerEvents() {

    }

    @Override
    public void reloadConfig() {

        this.langManager = new LangManager(getDataFolder());

        super.reloadConfig();
    }
}
