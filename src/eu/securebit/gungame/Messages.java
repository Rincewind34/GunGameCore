package eu.securebit.gungame;

public class Messages {
	
	public static String wrongMode(String mode) {
		return Main.layout().format("\\pre-You cannot execute that in the- *" + mode + "*-!-");
	}
	
	public static String syntax(String syntax) {
		return Main.layout().format("\\pre-Syntax: " + syntax + "-");
	}
	
	public static String invalidNumber(String input) {
		return Main.layout().format("\\pre-'-*" + input + "*-' is not a valid number!-");
	}
	
	public static String gamestateSkiped() {
		return Main.layout().format("\\pre+Gamestate skiped!+");
	}
	
	public static String currentGamestate() {
		return Main.layout().format("\\preCurrent gamestate: *" + Main.instance().getGameStateManager().getCurrentName() + "*");
	}
	
	public static String spawnNotExisting(int spawnId) {
		return Main.layout().format("\\pre-There is no spawn with id '-*" + spawnId + "*-'!-");
	}
	
	public static String spawnTeleportedTo(int spawnId) {
		return Main.layout().format("\\preYou have been teleported to the spawn with id '*" + spawnId + "*'.");
	}
	
	public static String spawnRemoved(int spawnId) {
		return Main.layout().format("\\pre*Success!* The specified spawn with id '*" + spawnId + "*' was removed.");
	}
	
	public static String spawnAdded(int spawnId) {
		return Main.layout().format("\\pre*Success!* New spawn with id '*" + spawnId + "*' added.");
	}

	public static String commandHelp() {
		return Main.layout().format("\\pre*/gungame* for the help!");
	}
	
	public static String permission(String permission) {
		return Main.layout().format("\\preAsk for the permission *" + permission + "*!");
	}
	
	public static String changeMode(String to) {
		return Main.layout().format("\\pre*Success!* The mode now: " + to + "!");
	}
	
	public static String suggestReload() {
		return Main.layout().format("\\preEnter */rl* to update the changes!");
	}
	
	public static String reloadFiles() {
		return Main.layout().format("\\pre*Success!* Files reloaded!");
	}
	
	public static String lobbyTeleport() {
		return Main.layout().format("\\preYou have been teleported to the lobby!");
	}
	
	public static String lobbySet() {
		return Main.layout().format("\\pre*Success!* You set the lobby to your location!");
	}
	
	public static String worldNotFound(String world) {
		return Main.layout().format("\\pre-Could not find the " + world + " !-");
	}
	
	public static String levelGiven(int levelId) {
		return Main.layout().format("\\preYou have got the items for level *" + levelId + "*!");
	}
	
	public static String levelSaved(int levelId) {
		return Main.layout().format("\\preYou saved the items for the level *" + levelId + "*!");
	}
	
	public static String levelDeleted(int levelId) {
		return Main.layout().format("\\preYou deleted the level *" + levelId + "*!");
	}
	
	public static String greaterNull() {
		return Main.layout().format("\\pre-The value hast to be greater than 0!-");
	}
	
	public static String nextLevelId(int nextId) {
		return Main.layout().format("\\pre-The next levelId is " + nextId + "!-");
	}
	
	public static String noLevelToRemove() {
		return Main.layout().format("\\pre-There is no level remaining to remove.-");
	}
	
	public static String maintenance() {
		return Main.layout().format("\\preThe server is currently under maintenance! GameMode updated!");
	}
	
}
