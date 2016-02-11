package eu.securebit.gungame.ioimpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileConfigRegistry extends AbstractConfig implements FileConfigRegistry {
	
	public CraftFileConfigRegistry(String path, CraftErrorHandler handler) {
		super(new File(path, ".fileregistry"), handler,
				FileConfigRegistry.ERROR_LOAD, FileConfigRegistry.ERROR_FOLDER,
				FileConfigRegistry.ERROR_CREATE, FileConfigRegistry.ERROR_MALFORMED_STRUCTURE);
		
		this.getDefaults().add(new ConfigDefault("files", Arrays.asList(), null));
	}
	
	@Override
	public void add(String file, String type) {
		this.checkReady();
		
		List<String> entries = this.getEntries();
		entries.add(file + Util.separator + type);
		
		this.setEntries(entries);
	}

	@Override
	public void remove(String file) {
		this.checkReady();
		
		List<String> entries = this.getEntries();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (Util.split(element)[0].equals(file)) {
				entries.remove(element);
				break;
			}
		}
		
		this.setEntries(entries);
	}
	
	@Override
	public boolean contains(String file) {
		this.checkReady();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (Util.split(element)[0].equals(file)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String get(String file) {
		this.checkReady();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (Util.split(element)[0].equals(file)) {
				return Util.split(element)[1];
			}
		}
		
		throw new GunGameIOException("The file '" + file + "' is not registered!");
	}
	
	@Override
	public List<String> getEntries() {
		this.checkReady();
		
		return super.config.getStringList("files");
	}
	
	public void cleanUp() {
		this.checkReady();
		
		List<String> cleanedEntries = this.getEntries();
		
		for (String element : this.getEntries()) {
			if (!new File(element.split(Util.separator)[0]).exists()) {
				cleanedEntries.remove(element);
			}
		}
		
		this.setEntries(cleanedEntries);
	}
	
	private void setEntries(List<String> entries) {
		super.config.set("files", entries);
		this.save();
	}
	
	
	public static class Util {
		
		private static final String separator = "==";
		
		public static String[] split(String element) {
			return element.split(Util.separator);
		}
		
	}
	
}
