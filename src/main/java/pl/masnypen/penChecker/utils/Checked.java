package pl.masnypen.penChecker.utils;

import org.bukkit.Location;

import java.util.UUID;

public class Checked {
    private Location location;
    private int taskID;
    private UUID sender;
    private UUID checkPlayer;
    private Location locationSender;

    public Checked(Location location, int taskID, UUID sender, UUID checkPlayer, Location locationSender) {
        this.location = location;
        this.taskID = taskID;
        this.sender = sender;
        this.checkPlayer = checkPlayer;
        this.locationSender = locationSender;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getCheckPlayer() {
        return checkPlayer;
    }

    public int getTaskID() {
        return taskID;
    }

    public Location getLocationSender() {
        return locationSender;
    }

    public UUID getSender() {
        return sender;
    }
}
