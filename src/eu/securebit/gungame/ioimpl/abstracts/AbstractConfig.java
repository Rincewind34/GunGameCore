package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.ioutil.FileType;

public abstract class AbstractConfig extends AbstractFile implements FileConfig {
	
	protected FileConfiguration config;
	
	private ThrownError errorLoad;
	private ThrownError errorMalformed;
	
	public AbstractConfig(File file, CraftErrorHandler handler, String errorMain, String errorLoad, String errorFolder, String errorCreate, String errorMalformed) {
		super(file, handler, errorMain, errorFolder, errorCreate);
		
		this.errorLoad = this.createError(errorLoad);
		this.errorMalformed = this.createError(errorMalformed);
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
				
				super.handler.throwError(this.errorMalformed);
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
		} catch (IOException ex) {
			throw GunGameIOException.fromOther(ex);
		}
	}
	
	@Override
	public void validate() {
		
	}
	
	@Override
	public void checkAccessability() {
		if (!this.isAccessable()) {
			throw GunGameErrorPresentException.create();
		}
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
		
		return !super.handler.isErrorPresent(this.errorLoad);
	}
	
	@Override
	public FileType getFileType() {
		return FileType.FILE;
	}
	
	public abstract void addDefaults();

}
