package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileGunGameConfig;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;

public abstract class CraftFileGunGameConfig extends AbstractConfig implements FileGunGameConfig {
	
	private String type;
	
	public CraftFileGunGameConfig(File file, CraftErrorHandler handler, String errorMain, String errorFolder, String errorCreate,
			String errorMalformed, String type) {
		super(file, handler, errorMain, errorFolder, errorCreate, errorMalformed);
		
		this.type = type;
	}

	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return this.type;
	}
	
}
