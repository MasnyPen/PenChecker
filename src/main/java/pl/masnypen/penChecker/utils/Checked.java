package pl.masnypen.penChecker.utils;

import org.bukkit.Location;

import java.util.UUID;

public class Checked {
    public Location location;
    public int taskID;
    public UUID sender;
    public Location locationSender;

    public Checked(Location location, int taskID, UUID sender, Location locationSender) {
        this.location = location;
        this.taskID = taskID;
        this.sender = sender;
        this.locationSender = locationSender;
    }
}
