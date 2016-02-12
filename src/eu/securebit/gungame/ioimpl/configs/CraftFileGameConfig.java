package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.ioutil.ConfigUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileGameConfig extends CraftFileGunGameConfig implements FileGameConfig {
	
	public CraftFileGameConfig(File file, CraftErrorHandler handler, World world) {
		super(file, handler, FileGameConfig.ERROR_LOAD, FileGameConfig.ERROR_FOLDER, FileGameConfig.ERROR_CREATE, FileGameConfig.ERROR_MALFORMED, "gameconfig");
		
		String dataFolder = "data" + File.separator;
		
		this.getDefaults().add(new ConfigDefault("game.editmode", true, boolean.class));
		this.getDefaults().add(new ConfigDefault("game.muted", false, boolean.class));
		this.getDefaults().add(new ConfigDefault("game.file.levels", dataFolder + "levels.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.messages", dataFolder + "messages.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.scoreboard", dataFolder + "scoreboard.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.options", dataFolder + "options.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.map", dataFolder + "map.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.playercount.minimal", 1, int.class));
		this.getDefaults().add(new ConfigDefault("game.playercount.maximal", 3, int.class));
		
		ConfigUtil.setLocation("game.lobby", world.getSpawnLocation(), this.getDefaults());
	}
	
	@Override
	public boolean isEditMode() {
		this.checkReady();
		
		return super.config.getBoolean("game.editmode");
	}
	
	@Override
	public boolean isMuted() {
		this.checkReady();
		
		return super.config.getBoolean("game.muted");
	}
	
	@Override
	public int getMinPlayers() {
		this.checkReady();
		
		return super.config.getInt("game.playercount.minimal");
	}
	
	@Override
	public int getMaxPlayers() {
		this.checkReady();
		
		return super.config.getInt("game.playercount.maximal");
	}
	
	@Override
	public String getFileLevelsLocation() {
		this.checkReady();
		
		return super.config.getString("game.file.levels");
	}
	
	@Override
	public String getFileMessagesLocation() {
		this.checkReady();
		
		return super.config.getString("game.file.messages");
	}

	@Override
	public String getFileScoreboardLocation() {
		this.checkReady();
		
		return super.config.getString("game.file.scoreboard");
	}
	
	@Override
	public String getFileOptionsLocation() {
		this.checkReady();
		
		return super.config.getString("game.file.options");
	}
	
	@Override
	public String getFileMapLocation() {
		this.checkReady();
		
		return super.config.getString("game.file.map");
	}
	
	@Override
	public Location getLocationLobby() {
		this.checkReady();
		
		return ConfigUtil.getLocation(super.config, "game.lobby");
	}
	
	@Override
	public void setEditMode(boolean enabled) {
		this.checkReady();
		
		super.config.set("game.editmode", enabled);
		this.save();
	}
	
	@Override
	public void setMuted(boolean muted) {
		this.checkReady();
		
		super.config.set("game.muted", muted);
		this.save();
	}
	
	@Override
	public void setLocationLobby(Location loc) {
		this.checkReady();
		
		ConfigUtil.setLocation(super.config, "game.lobby", loc);
		this.save();
	}
	
}