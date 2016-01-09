package eu.securebit.gungame.io;

public enum ConfigError {

	MISSING_BOOT_DIRECTORY
	("Cannot find specified boot-folder! Maybe, there is a file on the specified path!"),
	
	
	MISSING_FRAME_FILE
	("Cannot find specified frame! Maybe, there is a directory on the specified path!"),
	
	
	CONFIG_MALFORMED
	("The global configuration file is malformed! Delete it, to reset the config!");
	
	private final String description;
	
	private ConfigError(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
