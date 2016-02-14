package eu.securebit.gungame.ioimpl;

import java.io.File;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.util.ColorSet;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileBootConfig extends AbstractConfig implements FileBootConfig {
	
	public CraftFileBootConfig(String path, CraftErrorHandler handler) {
		super(new File(path, "bootconfig.yml"), handler,
				FileBootConfig.ERROR_LOAD, FileBootConfig.ERROR_FOLDER, FileBootConfig.ERROR_CREATE, FileBootConfig.ERROR_MALFORMED);
		
		this.getDefaults().add(new ConfigDefault("path-frame-file", "frames/frame.jar", String.class));
		this.getDefaults().add(new ConfigDefault("path-boot-folder", "frames/bootfolder", String.class));
		this.getDefaults().add(new ConfigDefault("color-set", ColorSet.DEFAULT.toString(), String.class));
		this.getDefaults().add(new ConfigDefault("debug-mode", false, boolean.class));
	}
	

	@Override
	public void setColorSet(String colorset) {
		this.checkReady();
		
		super.config.set("color-set", colorset);
		this.save();
	}
	
	@Override
	public void setDebugMode(boolean debug) {
		this.checkReady();
		
		super.config.set("debug-mode", debug);
		this.save();
	}
	
	@Override
	public boolean isDebugMode() {
		this.checkReady();
		
		return this.config.getBoolean("debug-mode");
	}
	
	@Override
	public String getBootFolder() {
		this.checkReady();
		
		return super.config.getString("path-boot-folder");
	}

	@Override
	public String getFrameJar() {
		this.checkReady();
		
		return super.config.getString("path-frame-file");
	}

	@Override
	public String getColorSet() {
		this.checkReady();
		
		return super.config.getString("color-set");
	}
	
	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}

	@Override
	public String getIdentifier() {
		return "bootconfig";
	}
	
}
