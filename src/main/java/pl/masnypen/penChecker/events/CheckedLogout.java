package pl.masnypen.penChecker.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.masnypen.penChecker.Main;
import pl.masnypen.penChecker.utils.Checked;


public class CheckedLogout implements Listener {
    private Main main;

    public CheckedLogout(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Checked checked = main.checkedList.get(event.getPlayer().getUniqueId());
        if (checked != null) {
            Player target = event.getPlayer();
            Player sender = Bukkit.getPlayer(checked.sender);

            if (sender == null) {
                System.out.println("Can't find a sender");
                return;
            }

            Bukkit.getScheduler().cancelTask(checked.taskID);
            target.teleport(checked.location);
            if (main.getConfig().getBoolean("admin_tp")) {
                sender.teleport(checked.locationSender);
            }

            sender.sendMessage(main.langManager.getMessage("commands.logout.sender", "&b{player}&6 wyszedł z serwera oraz został ukarany!").replace("{player}", target.getName()));

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(main.langManager.getMessage("commands.logout.broadcast", "&b{player}&6 wyszedł podczas sprawdzania!").replace("{player}", target.getName()));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");

            Bukkit.dispatchCommand(sender, main.getConfig().getString("logoutCmd").replace("{player}", target.getName()));

            main.checkedList.remove(target.getUniqueId());
            main.adminsChecked.remove(sender.getUniqueId());
        }
    }
}
