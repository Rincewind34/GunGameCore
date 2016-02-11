package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.ioutil.IOUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileMessages extends CraftFileGunGameConfig implements FileMessages {
	
	public CraftFileMessages(File file, CraftErrorHandler handler) {
		super(file, handler, FileMessages.ERROR_LOAD, FileMessages.ERROR_FOLDER, FileMessages.ERROR_CREATE, FileMessages.ERROR_MALFORMED, "messages");
		
		this.getDefaults().add(new ConfigDefault("message.prefix",
				"&7[&eGunGame&7]", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.player.join",
				"&e${player} &7joined the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.player.quit",
				"&e${player} &7left the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.player.serverquit",
				"&e${player} &7left the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.countdown.lobby",
				"&7The game starts in &e${time} &7seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.countdown.grace",
				"&7The graceperiod ends in &e${time} &7seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.countdown.end",
				"&cServer shutdown in ${time} seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.countdown.lobby-cancle",
				"&cThere are not enough players to play this round (${current}/${minimal})", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.mapteleport",
				"&7All players have been teleported to the map.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.graceperiod.start",
				"&7All players are invulnerable for 15 seconds now.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.graceperiod.end",
				"&eDamage enabled - Fight!", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.kill",
				"&e${victim} &7was killed by &e${killer}&7.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.death",
				"&7The player &e${victim} &7died.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.respawn",
				"&7You are now level &e${level}&7.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.winner",
				"&e&lCongratulations! &r&e${winner} is the winner of this game!", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.lobby.join",
				"Lobby", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.lobby.full",
				"Lobby", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.grace",
				"Grace", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.ingame",
				"Ingame", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.end",
				"Restarting", String.class));
		this.getDefaults().add(new ConfigDefault("message.motd.maintendance",
				"Maintendance", String.class));
	}
	
	@Override
	public String getPrefix() {
		this.checkReady();
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("message.prefix")) + " ";
	}
	
	@Override
	public String getJoinLobby() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.player.join"));
	}

	@Override
	public String getQuitLobby() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.player.quit"));
	}
	
	@Override
	public String getServerQuit() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.player.serverquit"));
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.countdown.lobby");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.countdown.grace");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.countdown.end");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	@Override
	public String getCountdownLobbyCancle(int currentPlayers, int minimalPlayers) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.countdown.lobby-cancle");
		msg = IOUtil.replace(msg, "current", currentPlayers);
		msg = IOUtil.replace(msg, "minimal", minimalPlayers);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getMapTeleport() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.mapteleport"));
	}

	@Override
	public String getGracePeriodStarts() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.graceperiod.start"));
	}

	@Override
	public String getGracePeriodEnds() {
		this.checkReady();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.ingame.graceperiod.end"));
	}

	@Override
	public String getWinner(Player player) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.winner");
		msg = IOUtil.replace(msg, "winner", player.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		this.checkReady();
		
		String msg =  super.config.getString("message.ingame.kill");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		msg = IOUtil.replace(msg, "killer", killer.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.death");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getRespawn(int level) {
		this.checkReady();
		
		String msg = super.config.getString("message.ingame.respawn");
		msg = IOUtil.replace(msg, "level", level);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getMotDLobbyJoin() {
		this.checkReady();
		
		return super.config.getString("message.motd.lobby.join");
	}

	@Override
	public String getMotDLobbyPremium() {
		this.checkReady();
		
		return super.config.getString("message.motd.lobby.premium");
	}

	@Override
	public String getMotDLobbyStaff() {
		this.checkReady();
		
		return super.config.getString("message.motd.lobby.staff");
	}

	@Override
	public String getMotDLobbySpawns() {
		this.checkReady();
		
		return super.config.getString("message.motd.spawns");
	}

	@Override
	public String getMotDLobbyIngame() {
		this.checkReady();
		
		return super.config.getString("message.motd.ingame");
	}

	@Override
	public String getMotDLobbyEnd() {
		this.checkReady();
		
		return super.config.getString("message.motd.end");
	}
	
}
