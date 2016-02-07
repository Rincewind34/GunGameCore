package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.ioutil.FileType;

public abstract class AbstractConfig extends AbstractFile implements FileConfig {
	
	protected FileConfiguration config;
	
	private String errorLoad;
	private String errorMalformed;
	
	public AbstractConfig(File file, ErrorHandler handler, String errorMain, String errorLoad, String errorFolder, String errorCreate, String errorMalformed) {
		super(file, handler, errorMain, errorFolder, errorCreate);
		
		this.errorLoad = errorLoad;
		this.errorMalformed = errorMalformed;
	}
	
	@Override
	public void create() {
		super.create();
		
		if (!super.handler.isErrorPresent(this.getErrorMain())) {
			try {
				this.config = YamlConfiguration.loadConfiguration(super.file);
				this.addDefaults();
				this.save();
			} catch (ScannerException ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.throwError(this.errorMalformed);
			} finally {
				this.validate();
			}
		}
	}
	
	@Override
	public void save() {
		this.config.options().copyDefaults(true);
		
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			throw new GunGameIOException(e.getMessage(), e);
		}
	}
	
	@Override
	public void validate() {
		
	}
	
	@Override
	public boolean isCreated() {
		return this.config != null;
	}
	
	@Override
	public boolean isAccessable() {
		if (!this.isCreated()) {
			return false;
		}
		
		return !this.handler.isErrorPresent(this.errorLoad);
	}
	
	@Override
	public FileType getFileType() {
		return FileType.FILE;
	}
	
	public abstract void addDefaults();

}
