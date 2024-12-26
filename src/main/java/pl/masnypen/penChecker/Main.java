package pl.masnypen.penChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import pl.masnypen.penChecker.commands.*;
import pl.masnypen.penChecker.events.CheckedLogout;
import pl.masnypen.penChecker.events.isCheckerEvent;
import pl.masnypen.penChecker.manager.LangManager;
import pl.masnypen.penChecker.utils.Checked;

import java.util.HashMap;
import java.util.UUID;


public final class Main extends JavaPlugin {
    private LangManager langManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                getLogger().info("Plugin folder created!");
            } else {
                getLogger().severe("Failed to create the plugin folder!");
                return;
            }
        }
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        this.langManager = new LangManager(getDataFolder(), this);

        getLogger().info("Plugin has been enabled!");
        getLogger().info("PenChecker!");
        getLogger().info("Version " + getDescription().getVersion());

        registerCommands();
        registerEvents();
    }


    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " Zostal wylaczony!");
    }

    public void registerCommands() {
        getCommand("setchecklocation").setExecutor(new SetCheckLocationCommand(this));
        PlayerCheckCommand playerCheckCommand = new PlayerCheckCommand(this);
        getCommand("startcheck").setExecutor(playerCheckCommand);
        getCommand("startcheck").setTabCompleter(playerCheckCommand);
        getCommand("markclean").setExecutor(new ClearCommand(this));
        getCommand("markguilty").setExecutor(new CheaterCommand(this));
        PenCheckerCommand penCheckerCommand = new PenCheckerCommand(this);
        getCommand("penchecker").setExecutor(penCheckerCommand);
        getCommand("penchecker").setTabCompleter(penCheckerCommand);
    }
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new isCheckerEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new CheckedLogout(this), this);
    }

    @Override
    public void reloadConfig() {

        this.langManager = new LangManager(getDataFolder(), this);

        super.reloadConfig();
    }


    public HashMap<UUID, Checked> checkedList = new HashMap<>();
    public HashMap<UUID, UUID> adminsChecked = new HashMap<>();

    public LangManager getLangManager() {
        return langManager;
    }
}
