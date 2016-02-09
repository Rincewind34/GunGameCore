package eu.securebit.gungame.ioimpl.directories;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.ioimpl.abstracts.AbstractDirectory;

public class CraftBootDirectory extends AbstractDirectory implements BootDirectory {
	
	private int frameId;
	
	private CraftErrorHandler handler;
	
	private File bootDataFile;
	private FileConfiguration bootDataConfig;
	
	public CraftBootDirectory(File directory, CraftErrorHandler handler) {
		super(directory, handler, BootDirectory.ERROR_MAIN, BootDirectory.ERROR_FILE, BootDirectory.ERROR_CREATE);
		
		this.handler = handler;
		this.frameId = -1;
	}
	
	@Override
	public void setUsingFrameId(int id) {
		if (!this.isReady()) {
			throw new GunGameErrorPresentException();
		}
		
		this.bootDataConfig.set("id", id);
		
		try {
			this.bootDataConfig.save(this.bootDataFile);
		} catch (IOException e) {
			throw new GunGameIOException(e.getMessage(), e);
		}
	}
	
	@Override
	public int getUsingFrameId() {
		if (!this.isReady()) {
			throw new GunGameErrorPresentException();
		}
		
		return this.frameId;
	}
	
	@Override
	public void create() {
		super.create();
		
		if (this.isCreated()) {
			this.bootDataFile = new File(this.getAbsolutPath(), ".bootdata.yml");
			
			if (this.bootDataFile.exists()) {
				if (this.bootDataFile.isDirectory()) {
					this.handler.throwError(BootDirectory.ERROR_BOOTDATA_FOLDER);
					return;
				}
			} else {
				try {
					this.bootDataFile.createNewFile();
				} catch (IOException ex) {
					this.handler.throwError(BootDirectory.ERROR_BOOTDATA_CREATE);
					return;
				}
			}
			
			try {
				this.bootDataConfig = YamlConfiguration.loadConfiguration(this.bootDataFile);
				
				if (!this.bootDataConfig.contains("id") || !this.bootDataConfig.isInt("id")) {
					this.bootDataConfig.set("id", 0);
					
					try {
						this.bootDataConfig.save(this.bootDataFile);
					} catch (IOException ex) {
						if (Main.DEBUG) {
							ex.printStackTrace();
						}
						
						this.handler.throwError(BootDirectory.ERROR_BOOTDATA_SAVE);
						return;
					}
				}
				
				this.frameId = this.bootDataConfig.getInt("id");
			} catch (ScannerException ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.handler.throwError(BootDirectory.ERROR_BOOTDATA_MALFORMED);
			}
		}
	}

}
