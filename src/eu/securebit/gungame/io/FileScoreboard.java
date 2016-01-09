package eu.securebit.gungame.io;

import eu.securebit.gungame.util.FileValidatable;

public interface FileScoreboard extends FileValidatable {
	
	public static FileScoreboard loadFile(String path, String name) {
		return new CraftFileScoreboard(path, name);
	}
	
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
}
