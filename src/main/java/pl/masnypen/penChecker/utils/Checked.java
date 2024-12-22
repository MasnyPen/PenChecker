package pl.masnypen.penChecker.utils;

import org.bukkit.Location;

public class Checked {
    public Location location;
    public int taskID;

    public Checked(Location location, int taskID) {
        this.location = location;
        this.taskID = taskID;
    }
}
