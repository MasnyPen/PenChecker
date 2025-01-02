package pl.masnypen.penChecker.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("cmd")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(main.getLangManager().getMessage("events.command_use", "&6Nie możesz używać komend podczas sprawdzania!"));
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("build")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("build")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId())  &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("interact")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId())  &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("chat")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                    ((main.getConfig().getBoolean("flags.enabled") &&
                    main.getConfig().getStringList("flags.flags").contains("enderpearl")) ||
                    !main.getConfig().getBoolean("useCheckLocation"))) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                main.getConfig().getStringList("flags.flags").contains("move")) ||
                !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
                ((main.getConfig().getBoolean("flags.enabled") &&
                        main.getConfig().getStringList("flags.flags").contains("drop")) ||
                        !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (main.getCheckManager().isChecked(event.getPlayer().getUniqueId()) &&
        ((main.getConfig().getBoolean("flags.enabled") &&
                main.getConfig().getStringList("flags.flags").contains("pickup")) ||
                !main.getConfig().getBoolean("useCheckLocation"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (main.getCheckManager().isChecked(event.getEntity().getUniqueId())){
            event.setCancelled(true);
        }
    }

}
