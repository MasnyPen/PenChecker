package pl.masnypen.penChecker;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import pl.masnypen.penChecker.commands.*;
import pl.masnypen.penChecker.events.CheckedLogout;
import pl.masnypen.penChecker.events.isCheckerEvent;
import pl.masnypen.penChecker.manager.CheckManager;
import pl.masnypen.penChecker.manager.LangManager;


public final class Main extends JavaPlugin {
    private LangManager langManager;
    private CheckManager checkManager;

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
        this.checkManager = new CheckManager();
        Metrics metrics = new Metrics(this, 24330);

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




    public LangManager getLangManager() {
        return langManager;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }
}
