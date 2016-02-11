package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import eu.securebit.gungame.util.ConfigDefault;

public abstract class AbstractConfig extends AbstractFile implements FileConfig {
	
	protected FileConfiguration config;
	
	private List<ConfigDefault> defaults;
	
	private ThrownError errorLoad;
	private ThrownError errorMalformed;
	
	public AbstractConfig(File file, CraftErrorHandler handler, String errorMain, String errorLoad, String errorFolder, String errorCreate, String errorMalformed) {
		super(file, handler, errorMain, errorFolder, errorCreate);
		
		this.defaults = new ArrayList<>();
		this.errorLoad = this.createError(errorLoad);
		this.errorMalformed = this.createError(errorMalformed);
	}
	
	@Override
	public void create() {
		super.create();
		
		if (!super.handler.isErrorPresent(this.getErrorMain())) {
			try {
				this.config = YamlConfiguration.loadConfiguration(super.file);
				
				for (ConfigDefault configDefault : this.defaults) {
					this.config.addDefault(configDefault.getPath(), configDefault.getValue());
				}
				
				this.save();
			} catch (ScannerException ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				super.handler.throwError(this.errorMalformed);
			} finally {
				for (ConfigDefault entry : this.defaults) {
					if (!entry.validate(this.config)) {
						super.handler.throwError(this.errorMalformed);
						break;
					}
				}
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
	
	protected List<ConfigDefault> getDefaults() {
		return this.defaults;
	}
	
}
