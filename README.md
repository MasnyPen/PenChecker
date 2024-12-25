# PenChecker

PenChecker is a plugin for Minecraft servers that allows administrators to monitor and check players for cheating or suspicious behavior. Players can be "checked" by an admin, and if any wrongdoing is detected, they can be punished using the appropriate commands.

## Commands

### `/startcheck <player>`
- **Description**: Allows an admin to check a player by teleporting them to a special spawn point to monitor their activities.
- **Example**: `/startcheck MasnyPen>`

### `/setchecklocation`
- **Description**: Sets the location where players will be teleported to when being checked. The player must stand in the location they want to set as the "check spawn."
- **Example**: `/setchecklocation`

### `/markclean`
- **Description**: Allows an admin to clear a player's accusations after checking them. The player is returned to their previous location and the check is concluded.
- **Example**: `/markclean`

### `/markguilty`
- **Description**: Punishes the player (e.g., bans them) after being flagged as a cheater. This command can be executed after checking the player.
- **Example**: `/markguilty`


## Configuration


```yaml
# Prefix for all plugin messages
prefix: "&7[&bPenChecker&7]"

# Command used to ban a player when they are flagged as a cheater
cheaterCmd: "ban {player} cheaty"

# Command used to ban a player when they log out during a check
logoutCmd: "ban {player} Wychodzenie podczas sprawdzania"

# Flags for monitoring player actions during checks
flags:
  enabled: true
  # Flags: build (Break/Place), move, cmd, interact, chat, enderpearl, drop, pickup
  flags: ["build", "cmd", "interact", "chat", "enderpearl", "drop", "pickup"]

# Admin teleport to the check location
admin_tp: true
```
