package eu.securebit.gungame.util;

import eu.securebit.gungame.Main;

public class CoreMessages {
	
	/* TODO's */
	
	public static String lobbyKickPremium() {  //TODO config
		return Main.layout().format("\\pre-You were kicked by a premiummember!");
	}
	
	public static String lobbyKickStaffMember() {  //TODO config
		return Main.layout().format("\\pre-You were kicked by a staffmember!-");
	}
	
	public static String lobbyFull() {  //TODO config
		return Main.layout().format("\\pre-The server is full!-");
	}
	
	public static String serverNotJoinable() { //TODO config
		return Main.layout().format("\\pre-The server is not joinable!-");
	}
	
	public static String maintendanceKick() {
		return Main.layout().format("\\preThe server is currently under maintenance, please visit us later!"); // TODO Config (User-Message)
	}
	
	public static String wrongMode(String mode) {
		return Main.layout().format("\\pre-You cannot execute that in the- *" + mode + "*-!-");
	}
	
	public static String syntax(String syntax) {
		return Main.layout().format("\\pre-Syntax: " + syntax + "-");
	}
	
	public static String invalidNumber(String input) {
		return Main.layout().format("\\pre-'-*" + input + "*-' is not a valid number!-");
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
	
	public static String levelNotExists(int levelId) {
		return Main.layout().format("\\preCannot find level with id *" + levelId + "*.");
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
	
	public static String maintendance() {
		return Main.layout().format("\\preThe server is currently under maintenance! GameMode updated!");
	}
	
	public static String maintendanceJoin() {
		return Main.layout().format("\\pre*${player}* joined the server!");
	}
	
	public static String maintendanceQuit() {
		return Main.layout().format("\\pre*${player}* left the server!");
	}
	
	public static String notInGame() {
		return Main.layout().format("\\pre-You have to be in a game!-");
	}
	
	public static String muteSwitch(String muteState) {
		return Main.layout().format("\\preTurned mute to: *" + muteState + "*!");
	}
	
	public static String alreadyMuted() {
		return Main.layout().format("\\preThe game is already muted!");
	}
	
	public static String alreadyUnmuted() {
		return Main.layout().format("\\preThe game is already unmuted!");
	}
	
	public static String gamestateSkiped() {
		return Main.layout().format("\\pre+Gamestate skiped!+");
	}
	
	public static String currentGamestate(String name) {
		return Main.layout().format("\\preCurrent gamestate: *" + name + "*");
	}
	
	public static String changeMode(String to) {
		return Main.layout().format("\\pre*Success!* The mode now: " + to + "!");
	}
	
	public static String suggestReload() {
		return Main.layout().format("\\preEnter */rl* to update the changes!");
	}
	
	public static String frameDisabled() {
		return Main.layout().format("\\pre-The frame is not enabled!-");
	}
	
}
