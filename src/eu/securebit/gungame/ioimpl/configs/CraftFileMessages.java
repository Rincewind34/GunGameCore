package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.ioutil.IOUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileMessages extends CraftFileGunGameConfig implements FileMessages {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		
	}
	
	public CraftFileMessages(File file, CraftErrorHandler handler) {
		super(file, handler, FileMessages.ERROR_MAIN, FileMessages.ERROR_LOAD, FileMessages.ERROR_FOLDER, FileMessages.ERROR_CREATE, FileMessages.ERROR_MALFORMED,
				"messages");
		
		this.getDefaults().add(new ConfigDefault("message.prefix", "&7[&eGunGame&7]", String.class));
		this.getDefaults().add(new ConfigDefault("message.player.join", "&e${player} &7joined the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.player.quit", "&e${player} &7left the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.player.serverquit", "&e${player} &7left the game.", String.class));
		this.getDefaults().add(new ConfigDefault("message.countdown.lobby", "&7The game starts in &e${time} &7seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.countdown.grace", "&7The graceperiod ends in &e${time} &7seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.countdown.end", "&cServer shutdown in ${time} seconds.", String.class));
		this.getDefaults().add(new ConfigDefault("message.countdown.lobby-cancle", "&cThere are not enough players to play this round (${current}/${minimal})",
				String.class));
		this.getDefaults().add(new ConfigDefault("message.mapteleport", "&7All players have been teleported to the map.", String.class));
		this.getDefaults().add(new ConfigDefault("message.graceperiod.start", "&7All players are invulnerable for 15 seconds now.", String.class));
		this.getDefaults().add(new ConfigDefault("message.graceperiod.end", "&eDamage enabled - Fight!", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.kill", "&e${victim} &7was killed by &e${killer}&7.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.death", "&7The player &e${victim} &7died.", String.class));
		this.getDefaults().add(new ConfigDefault("message.ingame.respawn", "&7You are now level &e${level}&7.", String.class));
		this.getDefaults().add(new ConfigDefault("message.winner", "&e&lCongratulations! &r&e${winner} is the winner of this game!", String.class));
		this.getDefaults().add(new ConfigDefault("motd.lobby.join", "Lobby", String.class));
		this.getDefaults().add(new ConfigDefault("motd.lobby.full", "Lobby", String.class));
		this.getDefaults().add(new ConfigDefault("motd.grace", "Grace", String.class));
		this.getDefaults().add(new ConfigDefault("motd.ingame", "Ingame", String.class));
		this.getDefaults().add(new ConfigDefault("motd.end", "Restarting", String.class));
		this.getDefaults().add(new ConfigDefault("motd.maintendance", "Maintendance", String.class));
	}
	
	@Override
	public String getPrefix() {
		this.checkAccessability();
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("message.prefix")) + " ";
	}
	
	@Override
	public String getJoinLobby() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.player.join"));
	}

	@Override
	public String getQuitLobby() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.player.quit"));
	}
	
	@Override
	public String getServerQuit() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.player.serverquit"));
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.countdown.lobby");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.countdown.grace");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.countdown.end");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	@Override
	public String getCountdownLobbyCancle(int currentPlayers, int minimalPlayers) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.countdown.lobby-cancle");
		msg = IOUtil.replace(msg, "current", currentPlayers);
		msg = IOUtil.replace(msg, "minimal", minimalPlayers);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getMapTeleport() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.mapteleport"));
	}

	@Override
	public String getGracePeriodStarts() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.graceperiod.start"));
	}

	@Override
	public String getGracePeriodEnds() {
		this.checkAccessability();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("message.graceperiod.end"));
	}

	@Override
	public String getWinner(Player player) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.winner");
		msg = IOUtil.replace(msg, "winner", player.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		this.checkAccessability();
		
		String msg =  super.config.getString("message.ingame.kill");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		msg = IOUtil.replace(msg, "killer", killer.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.ingame.death");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getRespawn(int level) {
		this.checkAccessability();
		
		String msg = super.config.getString("message.ingame.respawn");
		msg = IOUtil.replace(msg, "level", level);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	@Override
	public String getMotD(String gamestate) {
		// TODO motd
		return null;
	}

	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileMessages.defaults) {
			if (!entry.validate(super.config)) {
				super.handler.throwError(this.createError(FileMessages.ERROR_MALFORMED));
				break;
			}
		}
	}
	
}
