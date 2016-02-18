package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.ioutil.FileType;
import eu.securebit.gungame.util.ConfigDefault;

public abstract class AbstractConfig extends AbstractFile implements FileConfig {
	
	protected FileConfiguration config;
	
	private List<ConfigDefault> defaults;
	
	private ThrownError errorMalformed;
	
	public AbstractConfig(File file, ErrorHandler handler, String errorLoad, String errorFolder, String errorCreate, String errorMalformed) {
		super(file, handler, errorLoad, errorFolder, errorCreate);
		
		this.defaults = new ArrayList<>();
		this.errorMalformed = this.createError(errorMalformed);
	}
	
	@Override
	public void create() {
		super.create();
		
		if (!super.handler.isErrorPresent(this.getErrorLoad())) {
			try {
				this.config = YamlConfiguration.loadConfiguration(super.file);
				
				for (ConfigDefault configDefault : this.defaults) {
					this.config.addDefault(configDefault.getPath(), configDefault.getValue());
				}
				
				this.save();
			} catch (ScannerException ex) {
				if (Core.getSession().isDebugMode()) {
					ex.printStackTrace();
				}
				
				super.handler.throwError(this.errorMalformed);
			} finally {
				for (ConfigDefault entry : this.defaults) {
					if (!entry.validate(this.config)) {
						System.out.println(entry.getPath());
						
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
	public boolean isCreated() {
		return this.config != null;
	}
	
	@Override
	public FileType getFileType() {
		return FileType.FILE;
	}
	
	protected List<ConfigDefault> getDefaults() {
		return this.defaults;
	}
	
}
