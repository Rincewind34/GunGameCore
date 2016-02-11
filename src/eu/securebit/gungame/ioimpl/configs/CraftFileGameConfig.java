package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.ioutil.ConfigUtil;
import eu.securebit.gungame.ioutil.DataUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileGameConfig extends CraftFileGunGameConfig implements FileGameConfig {
	
	public CraftFileGameConfig(File file, CraftErrorHandler handler, World world) {
		super(file, handler,
				FileGameConfig.ERROR_MAIN, FileGameConfig.ERROR_LOAD, FileGameConfig.ERROR_FOLDER, FileGameConfig.ERROR_CREATE, FileGameConfig.ERROR_MALFORMED,
				"gameconfig");
		
		String dataFolder = "data" + File.separator;
		
		this.getDefaults().add(new ConfigDefault("game.editmode", true, boolean.class));
		this.getDefaults().add(new ConfigDefault("game.muted", false, boolean.class));
		this.getDefaults().add(new ConfigDefault("game.file.levels", dataFolder + "levels.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.messages", dataFolder + "messages.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.scoreboard", dataFolder + "scoreboard.yml", String.class));
		this.getDefaults().add(new ConfigDefault("game.file.options", dataFolder + "options.yml", String.class));
		this.getDefaults().add(new ConfigDefault("start-level", 1, int.class));
		this.getDefaults().add(new ConfigDefault("game.playercount.minimal", 1, int.class));
		this.getDefaults().add(new ConfigDefault("game.playercount.maximal", 3, int.class));
		
		ConfigUtil.setLocation("game.lobby", world.getSpawnLocation(), this.getDefaults());
	}
	
	@Override
	public boolean isEditMode() {
		this.checkAccessability();
		
		return super.config.getBoolean("editmode");
	}
	
	@Override
	public boolean isMuted() {
		this.checkAccessability();
		
		return super.config.getBoolean("muted");
	}
	
	@Override
	public int getStartLevel() {
		this.checkAccessability();
		
		return super.config.getInt("start-level");
	}
	
	@Override
	public int getMinPlayers() {
		this.checkAccessability();
		
		return super.config.getInt("playercount.minimal");
	}
	
	@Override
	public int getMaxPlayers() {
		this.checkAccessability();
		
		return super.config.getInt("playercount.maximal");
	}
	
	@Override
	public String getFileLevelsLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.levels");
	}
	
	@Override
	public String getFileMessagesLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.messages");
	}

	@Override
	public String getFileScoreboardLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.scoreboard");
	}
	
	@Override
	public String getFileOptionsLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.options");
	}
	
	@Override
	public Location getLocationLobby() {
		this.checkAccessability();
		
		return ConfigUtil.getLocation(super.config, "location.lobby");
	}
	
	@Override
	public void setEditMode(boolean enabled) {
		this.checkAccessability();
		
		super.config.set("editmode", enabled);
		this.save();
	}
	
	@Override
	public void setMuted(boolean muted) {
		this.checkAccessability();
		
		super.config.set("muted", muted);
		this.save();
	}
	
	@Override
	public void setStartLevel(int level) {
		this.checkAccessability();
		
		super.config.set("start-level", level);
		this.save();
	}

	@Override
	public void setLocationLobby(Location loc) {
		this.checkAccessability();
		
		ConfigUtil.setLocation(super.config, "location.lobby", loc);
		this.save();
	}
	
	@Override
	public void validate() {
		{
			int nextId = this.getNextSpawnId();
			
			if (this.getSpawns().containsKey(nextId)) {
				super.handler.throwError(this.createError(FileGameConfig.ERROR_SPAWNID));
			}
		} {
			FileLevels fileLevels = Core.getRootDirectory().getLevelsFile(this.getFileLevelsLocation());
			
			if (fileLevels.isAccessable()) {
				int startLevel = super.config.getInt("start-level");
				int levelCount = fileLevels.getLevels().size();
				
				if (startLevel < 1) {
					super.handler.throwError(this.createError(FileGameConfig.ERROR_LEVELCOUNT_SMALLER));
				}
				
				if (startLevel > levelCount) {
					super.handler.throwError(this.createError(FileGameConfig.ERROR_LEVELCOUNT_GREATER));
				}
			} else {
				// TODO WARNING
			}
		}
	}
	
}