package eu.securebit.gungame.ioimpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;

public class CraftFileConfigRegistry extends AbstractConfig implements FileConfigRegistry {
	
	private Util util;
	
	public CraftFileConfigRegistry(String path, CraftErrorHandler handler) {
		super(new File(path, ".fileregistry"), handler,
				FileConfigRegistry.ERROR_MAIN, FileConfigRegistry.ERROR_LOAD, FileConfigRegistry.ERROR_FOLDER,
				FileConfigRegistry.ERROR_CREATE, FileConfigRegistry.ERROR_MALFORMED_STRUCTURE);
		
		this.util = this.new Util();
	}
	
	@Override
	public void addDefaults() {
		super.config.addDefault("files", Arrays.asList());
	}
	
	@Override
	public void add(String file, String type) {
		this.checkAccessability();
		
		List<String> entries = this.getEntries();
		entries.add(file + Util.separator + type);
		
		this.setEntries(entries);
	}

	@Override
	public void remove(String file) {
		this.checkAccessability();
		
		List<String> entries = this.getEntries();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (this.util.split(element)[0].equals(file)) {
				entries.remove(element);
				break;
			}
		}
		
		this.setEntries(entries);
	}
	
	@Override
	public boolean contains(String file) {
		this.checkAccessability();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (this.util.split(element)[0].equals(file)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String get(String file) {
		this.checkAccessability();
		
		for (String element : CraftFileConfigRegistry.this.getEntries()) {
			if (this.util.split(element)[0].equals(file)) {
				return this.util.split(element)[1];
			}
		}
		
		throw new GunGameIOException("The file '" + file + "' is not registered!");
	}
	
	@Override
	public List<String> getEntries() {
		this.checkAccessability();
		
		return super.config.getStringList("files");
	}
	
	@Override
	public void validate() {
		if (!super.config.isList("files")) {
			this.throwError(FileConfigRegistry.ERROR_MALFORMED_STRUCTURE);
		}
		
		for (String element : this.getEntries()) {
			if (this.util.split(element).length != 2) {
				this.throwError(FileConfigRegistry.ERROR_MALFORMED_ENTRIES);
			}
		}
	}
	
	public void cleanUp() {
		this.checkAccessability();
		
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
	
	
	private class Util {
		
		private static final String separator = "==";
		
		public String[] split(String element) {
			return element.split(Util.separator);
		}
		
	}
	
}
