package pl.masnypen.penChecker.manager;

import org.bukkit.Location;
import pl.masnypen.penChecker.utils.Checked;

import java.util.HashMap;
import java.util.UUID;

public class CheckManager {
    private HashMap<UUID, Checked> checkedList = new HashMap<>();

    public void put(UUID checkPlayerUUID, Location location, int taskID, UUID sender, Location locationSender) {
        checkedList.put(checkPlayerUUID, new Checked(location, taskID, sender, checkPlayerUUID, locationSender));
    }

    public void remove(UUID checkPlayerUUID) {
        checkedList.remove(checkPlayerUUID);
    }

    public Checked get(UUID checkPlayerUUID) {
        return checkedList.get(checkPlayerUUID);
    }

    public boolean isChecked(UUID checkPlayerUUID) {
        return checkedList.containsKey(checkPlayerUUID);
    }
    public boolean isAdminChecking(UUID adminUUID) {
        for (Checked checked : checkedList.values()) {
            if (checked.getSender().equals(adminUUID)) {
                return true;
            }
        }
        return false;
    }

    public UUID getCheckedByAdmin(UUID uuid) {
        for (Checked checked : checkedList.values()) {
            if (checked.getSender().equals(uuid)) {
                return checked.getCheckPlayer();
            }
        }
        return null;
    }
}
