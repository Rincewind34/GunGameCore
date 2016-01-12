package eu.securebit.gungame.io.impl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.AbstractFileConfig;
import eu.securebit.gungame.io.FileMessages;

public class CraftFileMessages extends AbstractFileConfig implements FileMessages {
	
	public CraftFileMessages(String path, String name) {
		super(path, name, "messages");
	}
	
	@Override
	public void validate() throws MalformedConfigException {
		
	}
	
	@Override
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("messages.prefix")) + " ";
	}
	
	@Override
	public String getJoinLobby() {
		return this.prepare(super.config.getString("messages.player.join"));
	}

	@Override
	public String getQuitLobby() {
		return this.prepare(super.config.getString("messages.player.quit"));
	}
	
	@Override
	public String getServerQuit() {
		return this.prepare(super.config.getString("messages.player.serverquit"));
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		String msg = super.config.getString("messages.countdown.lobby");
		msg = this.replace(msg, "time", secondsLeft);
		
		return this.prepare(msg);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		String msg = super.config.getString("messages.countdown.grace");
		msg = this.replace(msg, "time", secondsLeft);
		
		return this.prepare(msg);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		String msg = super.config.getString("messages.countdown.end");
		msg = this.replace(msg, "time", secondsLeft);
		
		return this.prepare(msg);
	}

	@Override
	public String getMapTeleport() {
		return this.prepare(super.config.getString("messages.mapteleport"));
	}

	@Override
	public String getGracePeriodStarts() {
		return this.prepare(super.config.getString("messages.graceperiod.start"));
	}

	@Override
	public String getGracePeriodEnds() {
		return this.prepare(super.config.getString("messages.graceperiod.end"));
	}

	@Override
	public String getWinner(Player player) {
		String msg = super.config.getString("messages.winner");
		msg = this.replace(msg, "winner", player.getDisplayName());
		
		return this.prepare(msg);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		String msg =  super.config.getString("messages.ingame.kill");
		msg = this.replace(msg, "victim", victim.getDisplayName());
		msg = this.replace(msg, "killer", killer.getDisplayName());
		
		return this.prepare(msg);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		String msg = super.config.getString("messages.ingame.death");
		msg = this.replace(msg, "victim", victim.getDisplayName());
		
		return this.prepare(msg);
	}

	@Override
	public String getRespawn(int level) {
		String msg = super.config.getString("messages.ingame.respawn");
		msg = this.replace(msg, "level", level);
		
		return this.prepare(msg);
	}
	
	public void addDefaults() {
		super.config.addDefault("messages.prefix", "&7[&eGunGame&7]");
		super.config.addDefault("messages.player.join", "&e${player} &7joined the game.");
		super.config.addDefault("messages.player.quit", "&e${player} &7left the game.");
		super.config.addDefault("messages.player.serverquit", "&e${player} &7left the game.");
		super.config.addDefault("messages.countdown.lobby", "&7The game starts in &e${time} &7seconds.");
		super.config.addDefault("messages.countdown.grace", "&7The graceperiod ends in &e${time} &7seconds.");
		super.config.addDefault("messages.countdown.end", "&cServer shutdown in ${time} seconds.");
		super.config.addDefault("messages.mapteleport", "&7All players have been teleported to the map.");
		super.config.addDefault("messages.graceperiod.start", "&7All players are invulnerable for 15 seconds now.");
		super.config.addDefault("messages.graceperiod.end", "&eDamage enabled - Fight!");
		super.config.addDefault("messages.ingame.kill", "&e${victim} &7was killed by &e${killer}&7.");
		super.config.addDefault("messages.ingame.death", "&7The player &e${victim} &7died.");
		super.config.addDefault("messages.ingame.respawn", "&7You are now level &e${level}&7.");
		super.config.addDefault("messages.winner", "&e&lCongratulations! &r&e${winner} is the winner of this game!");
	}
	
	private String prepare(String str) {
		if (str == null) {
			return null;
		}
		
		return this.getPrefix() + ChatColor.translateAlternateColorCodes('&', str);
	}
	
	private String replace(String str, String variable, Object value) {
		if (str == null) {
			return str;
		}
		
		return str.replace("${" + variable + "}", String.valueOf(value));
	}

}
