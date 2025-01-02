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
        Checked checked = main.getCheckManager().get(event.getPlayer().getUniqueId());
        if (checked != null) {
            Player target = event.getPlayer();
            Player sender = Bukkit.getPlayer(checked.getSender());

            if (sender == null) {
                System.out.println("Can't find a sender");
                return;
            }

            Bukkit.getScheduler().cancelTask(checked.getTaskID());
            if (main.getConfig().getBoolean("useCheckLocation")) {
                target.teleport(checked.getLocation());
                if (main.getConfig().getBoolean("admin_tp")) {
                    sender.teleport(checked.getLocationSender());
                }
            }

            sender.sendMessage(main.getLangManager().getMessage("events.logout.sender", "&b{player}&6 has left the server and has been punished!").replace("{player}", target.getName()));

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(main.getLangManager().getMessage("events.logout.broadcast", "&b{player}&6 left during the check!").replace("{player}", target.getName()));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");

            Bukkit.dispatchCommand(sender, main.getConfig().getString("logoutCmd").replace("{player}", target.getName()));

            main.getCheckManager().remove(target.getUniqueId());
        }
    }
}
