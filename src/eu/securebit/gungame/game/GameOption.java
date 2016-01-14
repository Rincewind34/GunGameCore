package eu.securebit.gungame.game;

public enum GameOption {
	
	LEVEL_RESET("reset-level", 0, "resetLevel"),
	AUTO_RESPAWN("autorespawn", 1, "autoRespawn"),
	CARE_NATURAL_DEATH("care-natural-death", 2, "careNaturalDeath");
	
	public static GameOption get(int id) {
		for (GameOption option : GameOption.values()) {
			if (option.id == id) {
				return option;
			}
		}
		
		return null;
	}
	
	public static GameOption get(String name) {
		for (GameOption option : GameOption.values()) {
			if (option.name.equals(name)) {
				return option;
			}
		}
		
		return null;
	}
	
	
	private String name;
	private String cfgPath;
	
	private int id;
	
	private GameOption(String cfgPath, int id, String name) {
		this.cfgPath = "options." + cfgPath;
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getConfigPath() {
		return this.cfgPath;
	}
	
	public int getId() {
		return this.id;
	}
	
}
