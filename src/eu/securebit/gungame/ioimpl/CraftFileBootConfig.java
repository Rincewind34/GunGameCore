package eu.securebit.gungame.ioimpl;

import java.io.File;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.util.ColorSet;

public class CraftFileBootConfig extends AbstractConfig implements FileBootConfig {
	
	public CraftFileBootConfig(String path, CraftErrorHandler handler) {
		super(new File(path, "bootconfig.yml"), handler,
				FileBootConfig.ERROR_MAIN, FileBootConfig.ERROR_LOAD, FileBootConfig.ERROR_FOLDER, FileBootConfig.ERROR_CREATE, FileBootConfig.ERROR_MALFORMED);
	}
	
	@Override
	public void addDefaults() {
		super.config.addDefault("path-frame-file", "frames/frame.jar");
		super.config.addDefault("path-boot-folder", "frames/bootfolder");
		super.config.addDefault("color-set", ColorSet.DEFAULT.toString());
	}
	
	@Override
	public String getBootFolder() {
		this.checkAccessability();
		
		return super.config.getString("path-boot-folder");
	}

	@Override
	public String getFrameJar() {
		this.checkAccessability();
		
		return super.config.getString("path-frame-file");
	}

	@Override
	public String getColorSet() {
		this.checkAccessability();
		
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
	
	@Override
	public void validate() {
		if (!super.config.isString("path-frame-file") || !super.config.isString("path-boot-folder") || !super.config.isString("color-set")) {
			super.handler.throwError(FileBootConfig.ERROR_MALFORMED);
		}
	}

}
