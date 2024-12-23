package pl.masnypen.penChecker.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import pl.masnypen.penChecker.Main;

public class isCheckerEvent implements Listener {

    private Main main;
    public isCheckerEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("cmd")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(main.langManager.getMessage("events.command_use", "&6Nie możesz używać komend podczas sprawdzania!"));
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("build")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("build")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("interact")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("interact")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("enderpearl")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("drop")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (main.checkedList.get(event.getPlayer().getUniqueId()) != null && main.getConfig().getBoolean("flags.enabled") && main.getConfig().getStringList("flags.flags").contains("pickup")) {
            event.setCancelled(true);
        }
    }

}
