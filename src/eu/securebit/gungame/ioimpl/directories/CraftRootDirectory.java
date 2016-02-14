package eu.securebit.gungame.ioimpl.directories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMap;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.ioimpl.CraftFileBootConfig;
import eu.securebit.gungame.ioimpl.CraftFileConfigRegistry;
import eu.securebit.gungame.ioimpl.abstracts.AbstractDirectory;
import eu.securebit.gungame.ioimpl.configs.CraftFileGameConfig;
import eu.securebit.gungame.ioimpl.configs.CraftFileLevels;
import eu.securebit.gungame.ioimpl.configs.CraftFileMap;
import eu.securebit.gungame.ioimpl.configs.CraftFileMessages;
import eu.securebit.gungame.ioimpl.configs.CraftFileOptions;
import eu.securebit.gungame.ioimpl.configs.CraftFileScoreboard;
import eu.securebit.gungame.ioutil.IOUtil;
import eu.securebit.gungame.util.ColorSet;

public class CraftRootDirectory extends AbstractDirectory implements RootDirectory {
	
	private BootDirectory bootDirectory;
	private AddonDirectory addonDirectory;
	
	private FileBootConfig bootConfig;
	private FileConfigRegistry configRegistry;
	
	private Map<String, FileIdentifyable> savedFiles;
	
	private CraftErrorHandler handler;
	
	private File frame;
	
	public CraftRootDirectory(File file, CraftErrorHandler handler) {
		super(file, handler, RootDirectory.ERROR_MAIN, RootDirectory.ERROR_FILE, RootDirectory.ERROR_CREATE);
		
		this.handler = handler;
		this.savedFiles = new HashMap<>();
	}
	
	@Override
	public void resolveColorSet() {
		if (this.bootConfig.isReady()) {
			String colorset = this.bootConfig.getColorSet();
			
			try {
				ColorSet.valueOf(colorset).prepare(Main.layout());
			} catch (Exception ex) {
				this.handler.throwError(ColorSet.ERROR_ENTRY);
			}
		} else {
			this.handler.throwError(ColorSet.ERROR_MAIN);
		}
	}
	
	@Override
	public void deleteBootConfig() {
		IOUtil.delete(new File(this.bootConfig.getAbsolutePath()));
	}
	
	@Override
	public void deleteConfigRegistry() {
		IOUtil.delete(new File(this.configRegistry.getAbsolutePath()));
	}
	
	@Override
	public void setColorSet(ColorSet colorset) {
		this.bootConfig.setColorSet(colorset.toString());
	}
	
	@Override
	public void setDebugMode(boolean debug) {
		this.bootConfig.setDebugMode(debug);
	}
	
	@Override
	public boolean isFramePresent() {
		return this.frame != null;
	}
	
	@Override
	public boolean isDebugMode() {
		return this.bootConfig.isDebugMode();
	}
	
	@Override
	public FileMessages getMessagesFile(String path, String name) {
		return this.getMessagesFile(this.createFromRelativDatas(path, name));
	}
	
	@Override
	public FileMessages getMessagesFile(String relativPath) {
		return this.getMessagesFile(this.createFromRelativDatas(relativPath));
	}
	
	@Override
	public FileMessages getMessagesFile(File file) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileMessages(file, this.handler)));
	}

	@Override
	public FileScoreboard getScoreboardFile(String path, String name) {
		return this.getScoreboardFile(this.createFromRelativDatas(path, name));
	}
	
	@Override
	public FileScoreboard getScoreboardFile(String relativPath) {
		return this.getScoreboardFile(this.createFromRelativDatas(relativPath));
	}
	
	@Override
	public FileScoreboard getScoreboardFile(File file) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileScoreboard(file, this.handler)));
	}

	@Override
	public FileLevels getLevelsFile(String path, String name) {
		return this.getLevelsFile(this.createFromRelativDatas(path, name));
	}
	
	@Override
	public FileLevels getLevelsFile(String relativPath) {
		return this.getLevelsFile(this.createFromRelativDatas(relativPath));
	}

	@Override
	public FileLevels getLevelsFile(File file) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileLevels(file, this.handler)));
	}
	
	@Override
	public FileOptions getOptionsFile(String path, String name) {
		return this.getOptionsFile(this.createFromRelativDatas(path, name));
	}
	
	@Override
	public FileOptions getOptionsFile(String relativPath) {
		return this.getOptionsFile(this.createFromRelativDatas(relativPath));
	}

	@Override
	public FileOptions getOptionsFile(File file) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileOptions(file, this.handler)));
	}
	
	@Override
	public FileMap getMapFile(String path, String name) {
		return this.getMapFile(this.createFromRelativDatas(path, name));
	}
	
	@Override
	public FileMap getMapFile(String relativPath) {
		return this.getMapFile(this.createFromRelativDatas(relativPath));
	}

	@Override
	public FileMap getMapFile(File file) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileMap(file, this.handler)));
	}
	
	@Override
	public FileGameConfig getGameConfigFile(String path, String name, World lobbyWorld) {
		return this.getGameConfigFile(this.createFromRelativDatas(path, name), lobbyWorld);
	}
	
	@Override
	public FileGameConfig getGameConfigFile(String relativPath, World lobbyWorld) {
		return this.getGameConfigFile(this.createFromRelativDatas(relativPath), lobbyWorld);
	}
	
	@Override
	public FileGameConfig getGameConfigFile(File file, World lobbyWorld) {
		return this.createFileConfig(this.getIdentifyableFile(new CraftFileGameConfig(file, this.handler, lobbyWorld)));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends FileIdentifyable> T getIdentifyableFile(T file) {
		if (!this.savedFiles.containsKey(file.getAbsolutePath())) {
			this.savedFiles.put(file.getAbsolutePath(), file);
			this.registerFile(file);
		}
		
		return (T) this.savedFiles.get(file.getAbsolutePath());
	}
	
	@Override
	public File getFrameJar() {
		if (!this.isFramePresent()) {
			throw GunGameErrorPresentException.create();
		}
		
		return this.frame;
	}
	
	@Override
	public File getFile(String path) {
		File file = this.createFromRelativDatas(path);
		
		if (file.exists()) {
			return file;
		} else {
			return new File(path);
		}
	}

	@Override
	public BootDirectory getBootFolder() {
		return this.bootDirectory;
	}
	
	@Override
	public AddonDirectory getAddonDirectory() {
		return this.addonDirectory;
	}
	
	@Override
	public void create() {
		super.create();
		
		if (this.isCreated()) {
			this.configRegistry = new CraftFileConfigRegistry(this.getAbsolutPath(), this.handler);
			this.configRegistry.create();
			
			if (this.configRegistry.isReady()) {
				for (String element : this.configRegistry.getEntries()) {
					if (CraftFileConfigRegistry.Util.split(element).length != 2) {
						super.handler.throwError(FileConfigRegistry.ERROR_MALFORMED_ENTRIES);
					}
				}
			}
			
			if (this.configRegistry.isReady()) {
				((CraftFileConfigRegistry) this.configRegistry).cleanUp();
			}
			
			this.bootConfig = new CraftFileBootConfig(this.getAbsolutPath(), this.handler);
			this.bootConfig.create();
			
			this.addonDirectory = new CraftAddonDirectory(new File(this.getAbsolutPath(), "addons"), this.handler);
			this.addonDirectory.create();
			
			if (this.bootConfig.isReady()) {
				this.registerFile(this.bootConfig);
				
				this.bootDirectory = new CraftBootDirectory(new File(this.getAbsolutPath(), this.bootConfig.getBootFolder()), this.handler);
				this.bootDirectory.create();
			
				File frame = this.createFromRelativDatas(this.bootConfig.getFrameJar());
				
				if (!frame.exists()) {
					this.handler.throwError(RootDirectory.ERROR_FRAME_EXIST);
					return;
				}
				
				if (frame.isDirectory()) {
					this.handler.throwError(RootDirectory.ERROR_FRAME_NOJAR);
					return;
				}
				
				try {
					IOUtil.checkJarFile(frame);
				} catch (Exception ex) {
					this.handler.throwError(RootDirectory.ERROR_FRAME_NOJAR);
					return;
				}
				
				this.frame = frame;
			} else {
				this.handler.throwError(BootDirectory.ERROR_MAIN);
				this.handler.throwError(RootDirectory.ERROR_FRAME);
			}
		}
	}
	
	private File createFromRelativDatas(String path) {
		return new File(this.getAbsolutPath(), path);
	}
	
	private File createFromRelativDatas(String path, String name) {
		return this.createFromRelativDatas(path + File.separator + name);
	}
	
	private void registerFile(FileIdentifyable file) {
		if (this.configRegistry.isReady()) {
			if (!this.configRegistry.contains(file.getAbsolutePath())) {
				this.configRegistry.add(file.getAbsolutePath(), file.getIdentifier());
			}
		}
	}
	
	private <T extends FileConfig> T createFileConfig(T file) {
		if (!file.isCreated()) {
			file.create();
		}
		
		return file;
	}
	
}
