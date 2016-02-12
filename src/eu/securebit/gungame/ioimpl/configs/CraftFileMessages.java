package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import org.bukkit.ChatColor;

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
	public String getCountdownLobby() {
		this.checkReady();
		
		return super.config.getString("message.ingame.countdown.lobby");
	}

	@Override
	public String getCountdownGrace() {
		this.checkReady();
		
		return super.config.getString("message.ingame.countdown.grace");
	}

	@Override
	public String getCountdownEnd() {
		this.checkReady();
		
		return super.config.getString("message.ingame.countdown.end");
	}
	
	@Override
	public String getCountdownLobbyCancle() {
		this.checkReady();
		
		return super.config.getString("message.ingame.countdown.lobby-cancle");
	}

	@Override
	public String getMapTeleport() {
		this.checkReady();
		
		return super.config.getString("message.ingame.mapteleport");
	}

	@Override
	public String getGracePeriodStarts() {
		this.checkReady();
		
		return super.config.getString("message.ingame.graceperiod.start");
	}

	@Override
	public String getGracePeriodEnds() {
		this.checkReady();
		
		return super.config.getString("message.ingame.graceperiod.end");
	}

	@Override
	public String getWinner() {
		this.checkReady();
		
		return super.config.getString("message.ingame.winner");
	}

	@Override
	public String getKillBroadcast() {
		this.checkReady();
		
		return super.config.getString("message.ingame.kill");
	}

	@Override
	public String getDeathBroadcast() {
		this.checkReady();
		
		return super.config.getString("message.ingame.death");
	}

	@Override
	public String getRespawn() {
		this.checkReady();
		
		return super.config.getString("message.ingame.respawn");
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
	public String getMotDGrace() {
		this.checkReady();
		
		return super.config.getString("message.motd.grace");
	}

	@Override
	public String getMotDIngame() {
		this.checkReady();
		
		return super.config.getString("message.motd.ingame");
	}

	@Override
	public String getMotDEnd() {
		this.checkReady();
		
		return super.config.getString("message.motd.end");
	}
	
	@Override
	public String getMotDMaintendance() {
		this.checkReady();
		
		return super.config.getString("message.motd.maintendace");
	}
	
}
