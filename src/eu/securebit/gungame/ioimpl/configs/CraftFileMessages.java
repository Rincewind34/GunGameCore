package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.ioutil.IOUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileMessages extends AbstractConfig implements FileMessages {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileMessages.defaults.add(new ConfigDefault("messages.prefix", "&7[&eGunGame&7]", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.player.join", "&e${player} &7joined the game.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.player.quit", "&e${player} &7left the game.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.player.serverquit", "&e${player} &7left the game.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.countdown.lobby", "&7The game starts in &e${time} &7seconds.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.countdown.grace", "&7The graceperiod ends in &e${time} &7seconds.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.countdown.end", "&cServer shutdown in ${time} seconds.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.mapteleport", "&7All players have been teleported to the map.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.graceperiod.start", "&7All players are invulnerable for 15 seconds now.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.graceperiod.end", "&eDamage enabled - Fight!", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.ingame.kill", "&e${victim} &7was killed by &e${killer}&7.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.ingame.death", "&7The player &e${victim} &7died.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.ingame.respawn", "&7You are now level &e${level}&7.", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("messages.winner", "&e&lCongratulations! &r&e${winner} is the winner of this game!", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.lobby.join", "Lobby", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.lobby.full", "Lobby", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.grace", "Grace", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.ingame", "Ingame", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.end", "Restarting", String.class));
		CraftFileMessages.defaults.add(new ConfigDefault("motds.maintenance", "Maintenance", String.class));
	}
	
	public CraftFileMessages(File file, ErrorHandler handler) {
		super(file, handler, FileMessages.ERROR_MAIN, FileMessages.ERROR_LOAD, FileMessages.ERROR_FOLDER, FileMessages.ERROR_CREATE, FileMessages.ERROR_MALFORMED);
	}
	
	@Override
	public String getPrefix() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("messages.prefix")) + " ";
	}
	
	@Override
	public String getJoinLobby() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.player.join"));
	}

	@Override
	public String getQuitLobby() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.player.quit"));
	}
	
	@Override
	public String getServerQuit() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.player.serverquit"));
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.countdown.lobby");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.countdown.grace");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.countdown.end");
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getMapTeleport() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.mapteleport"));
	}

	@Override
	public String getGracePeriodStarts() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.graceperiod.start"));
	}

	@Override
	public String getGracePeriodEnds() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return IOUtil.prepare(this.getPrefix(), super.config.getString("messages.graceperiod.end"));
	}

	@Override
	public String getWinner(Player player) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.winner");
		msg = IOUtil.replace(msg, "winner", player.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg =  super.config.getString("messages.ingame.kill");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		msg = IOUtil.replace(msg, "killer", killer.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.ingame.death");
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getRespawn(int level) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		String msg = super.config.getString("messages.ingame.respawn");
		msg = IOUtil.replace(msg, "level", level);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileMessages.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}

	@Override
	public String getMotD(String gamestate) {
		// TODO motd
		return null;
	}

	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return "messages";
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileMessages.defaults) {
			if (!entry.validate(super.config)) {
				this.throwError(FileMessages.ERROR_MALFORMED);
				break;
			}
		}
	}
	
}
