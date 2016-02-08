package eu.securebit.gungame.errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.securebit.InfoLayout;

import org.bukkit.Bukkit;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.util.ColorSet;

public class ErrorHandler {
	
	public static final Map<String, Error> errors = new HashMap<>();
	
	public static final Map<String, TmpError> tmpErrors = new HashMap<>();
	
	public static final Map<String, TmpError> warnings = new HashMap<>();
	
	static {
		ErrorHandler.errors.put(RootDirectory.ERROR_MAIN, RootDirectory.createErrorMain());
		ErrorHandler.errors.put(RootDirectory.ERROR_FILE, RootDirectory.createErrorFile());
		ErrorHandler.errors.put(RootDirectory.ERROR_CREATE, RootDirectory.createErrorCreate());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_MAIN, FileConfigRegistry.createErrorMain());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_LOAD, FileConfigRegistry.createErrorLoad());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_FOLDER, FileConfigRegistry.createErrorFolder());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_CREATE, FileConfigRegistry.createErrorCreate());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_MALFORMED_STRUCTURE, FileConfigRegistry.createErrorMalformedStructure());
		ErrorHandler.errors.put(FileConfigRegistry.ERROR_MALFORMED_ENTRIES, FileConfigRegistry.createErrorMalformedEntries());
		ErrorHandler.errors.put(FileBootConfig.ERROR_MAIN, FileBootConfig.createErrorMain());
		ErrorHandler.errors.put(FileBootConfig.ERROR_LOAD, FileBootConfig.createErrorLoad());
		ErrorHandler.errors.put(FileBootConfig.ERROR_FOLDER, FileBootConfig.createErrorFolder());
		ErrorHandler.errors.put(FileBootConfig.ERROR_CREATE, FileBootConfig.createErrorCreate());
		ErrorHandler.errors.put(FileBootConfig.ERROR_MALFORMED, FileBootConfig.createErrorMalformed());
		ErrorHandler.errors.put(AddonDirectory.ERROR_MAIN, AddonDirectory.createErrorMain());
		ErrorHandler.errors.put(AddonDirectory.ERROR_FILE, AddonDirectory.createErrorFile());
		ErrorHandler.errors.put(AddonDirectory.ERROR_CREATE, AddonDirectory.createErrorCreate());
		ErrorHandler.errors.put(BootDirectory.ERROR_MAIN, BootDirectory.createErrorMain());
		ErrorHandler.errors.put(BootDirectory.ERROR_FILE, BootDirectory.createErrorFile());
		ErrorHandler.errors.put(BootDirectory.ERROR_CREATE, BootDirectory.createErrorCreate());
		ErrorHandler.errors.put(BootDirectory.ERROR_BOOTDATA_FOLDER, BootDirectory.createErrorBootdataFolder());
		ErrorHandler.errors.put(BootDirectory.ERROR_BOOTDATA_CREATE, BootDirectory.createErrorBootdataCreate());
		ErrorHandler.errors.put(BootDirectory.ERROR_BOOTDATA_MALFORMED, BootDirectory.createErrorBootdataMalformed());
		ErrorHandler.errors.put(BootDirectory.ERROR_BOOTDATA_SAVE, BootDirectory.createErrorBootdataSave());
		ErrorHandler.errors.put(ColorSet.ERROR_MAIN, ColorSet.createErrorMain());
		ErrorHandler.errors.put(ColorSet.ERROR_ENTRY, ColorSet.createErrorEntry());
		ErrorHandler.errors.put(RootDirectory.ERROR_FRAME, RootDirectory.createErrorFrame());
		ErrorHandler.errors.put(RootDirectory.ERROR_FRAME_EXIST, RootDirectory.createErrorFrameExists());
		ErrorHandler.errors.put(RootDirectory.ERROR_FRAME_NOJAR, RootDirectory.createErrorFrameNojar());
		ErrorHandler.errors.put(Frame.ERROR_LOAD, Frame.createErrorLoad());
		ErrorHandler.errors.put(Frame.ERROR_LOAD_MAINCLASS, Frame.createErrorLoadMainclass());
		ErrorHandler.errors.put(Frame.ERROR_ENABLE, Frame.createErrorEnable());
		ErrorHandler.errors.put(Frame.ERROR_ENABLE_ID, Frame.createErrorEnableId());
		ErrorHandler.errors.put(FileMessages.ERROR_MAIN, FileMessages.createErrorMain());
		ErrorHandler.errors.put(FileMessages.ERROR_LOAD, FileMessages.createErrorLoad());
		ErrorHandler.errors.put(FileMessages.ERROR_FOLDER, FileMessages.createErrorFolder());
		ErrorHandler.errors.put(FileMessages.ERROR_CREATE, FileMessages.createErrorCreate());
		ErrorHandler.errors.put(FileMessages.ERROR_MALFORMED, FileMessages.createErrorMalformed());
		ErrorHandler.errors.put(FileGameConfig.ERROR_MAIN, FileGameConfig.createErrorMain());
		ErrorHandler.errors.put(FileGameConfig.ERROR_LOAD, FileGameConfig.createErrorLoad());
		ErrorHandler.errors.put(FileGameConfig.ERROR_FOLDER, FileGameConfig.createErrorFolder());
		ErrorHandler.errors.put(FileGameConfig.ERROR_CREATE, FileGameConfig.createErrorCreate());
		ErrorHandler.errors.put(FileGameConfig.ERROR_MALFORMED, FileGameConfig.createErrorMalformed());
		ErrorHandler.errors.put(FileGameConfig.ERROR_SPAWNID, FileGameConfig.createErrorSpawnId());
		ErrorHandler.errors.put(FileGameConfig.ERROR_LEVELCOUNT, FileGameConfig.createErrorLevelCount());
		ErrorHandler.errors.put(FileGameConfig.ERROR_LEVELCOUNT_SMALLER, FileGameConfig.createErrorLevelCountSmaller());
		ErrorHandler.errors.put(FileGameConfig.ERROR_LEVELCOUNT_GREATER, FileGameConfig.createErrorLevelCountGreater());
		ErrorHandler.errors.put(FileLevels.ERROR_MAIN, FileLevels.createErrorMain());
		ErrorHandler.errors.put(FileLevels.ERROR_LOAD, FileLevels.createErrorLoad());
		ErrorHandler.errors.put(FileLevels.ERROR_FOLDER, FileLevels.createErrorFolder());
		ErrorHandler.errors.put(FileLevels.ERROR_CREATE, FileLevels.createErrorCreate());
		ErrorHandler.errors.put(FileLevels.ERROR_MALFORMED, FileLevels.createErrorMalformed());
		ErrorHandler.errors.put(FileLevels.ERROR_LEVELCOUNT, FileLevels.createErrorLevelCount());
		ErrorHandler.errors.put(FileScoreboard.ERROR_MAIN, FileScoreboard.createErrorMain());
		ErrorHandler.errors.put(FileScoreboard.ERROR_LOAD, FileScoreboard.createErrorLoad());
		ErrorHandler.errors.put(FileScoreboard.ERROR_FOLDER, FileScoreboard.createErrorFolder());
		ErrorHandler.errors.put(FileScoreboard.ERROR_CREATE, FileScoreboard.createErrorCreate());
		ErrorHandler.errors.put(FileScoreboard.ERROR_MALFORMED, FileScoreboard.createErrorMalformed());
		ErrorHandler.errors.put(FileScoreboard.ERROR_TITLE, FileScoreboard.createErrorTitle());
		ErrorHandler.errors.put(FileScoreboard.ERROR_FORMAT, FileScoreboard.createErrorFormat());
	}
	
	private List<String> thrownErrors;
	
	public ErrorHandler() {
		this.thrownErrors = new ArrayList<>();
	}
	
	public void throwError(String id) {
		this.throwError(id, null);
	}
	
	public void throwError(String id, String causedBy) {
		this.throwError(id, causedBy, new String[0]);
	}
	
	public void throwError(String id, String causedBy, String... variables) {
		this.throwError(false, id, causedBy, variables);
	}
	
	private void throwError(boolean trigger, String id, String causedBy, String... variables) {
		if (!ErrorHandler.errors.containsKey(id)) {
			throw new GunGameException("The errorid '" + id + "' is unknown!");
		}
		
		Error error = ErrorHandler.errors.get(id);
		
		String errorId = id;
		String errorMsg = error.getMessage();
		
		for (int i = 0; i < variables.length; i++) {
			String var = "VAR" + Integer.toString(i);
			
			if (errorMsg.contains(var) || errorId.contains(var)) {
				errorId = errorId.replace(var, InfoLayout.replaceKeys(variables[i]));
				errorMsg = errorMsg.replace(var, InfoLayout.replaceKeys(variables[i]));
			}
		}
		
		if (errorMsg.contains("VAR")) {
			throw new GunGameException("All variables should be present!");
		}
		
		if (errorId.contains("VAR")) {
			throw new GunGameException("All variables should be present!");
		}
		
		errorId = InfoLayout.replaceKeys(errorId);
		errorMsg = InfoLayout.replaceKeys(errorMsg);
		
		if (!this.isErrorPresent(id)) {
			this.thrownErrors.add(id);
		}
		
		if (!trigger) {
			Main.layout().message(Bukkit.getConsoleSender(), "-Error$-" + errorId + " occured: " + errorMsg + "-");
		} else {
			Main.layout().message(Bukkit.getConsoleSender(), "-=> triggers: Error$-" + errorId + " (" + errorMsg + ")-");
		}
		
		if (causedBy != null) {
			String msgCausedBy = InfoLayout.replaceKeys(causedBy);
			
			Main.layout().message(Bukkit.getConsoleSender(), "-=> Caused by: Error$-" + msgCausedBy + " (" + ErrorHandler.errors.get(causedBy).getMessage() + ")-");
		}
		
		for (String superId : ErrorHandler.errors.get(id).getSuperErrors()) {
			this.throwError(true, superId, null, variables);
		}
	}
	
	public void ejectTmpError(String id, String causedBy) {
		this.ejectTmpError(id, causedBy, null);
	}
	
	public void ejectTmpError(String id, String causedBy, String variable) {
		if (!ErrorHandler.tmpErrors.containsKey(id)) {
			throw new GunGameException("The tmperrorid '" + id + "' is unknown!");
		}
		
		if (variable == null && id.endsWith("-VAR")) {
			throw new GunGameException("Variable has to be present!");
		}
		
		String msgId = InfoLayout.replaceKeys(variable != null ? id.replace("VAR", variable) : id);
		String errorMsg = variable == null ? ErrorHandler.tmpErrors.get(id).getMessage() : ErrorHandler.tmpErrors.get(id).getMessage().replace("VAR", variable);
		
		Main.layout().message(Bukkit.getConsoleSender(), "*TmpError$-" + msgId + "* occured: " + errorMsg);
		
		if (causedBy != null) {
			String msgCausedBy = InfoLayout.replaceKeys(causedBy);
			
			Main.layout().message(Bukkit.getConsoleSender(), "=> Caused by: -Error$-" + msgCausedBy + "- (" + ErrorHandler.errors.get(causedBy).getMessage() + ")");
		}
	}
	
	public void ejectWarning(String id, String causedBy) {
		this.ejectWarning(id, causedBy, null);
	}
	
	public void ejectWarning(String id, String causedBy, String variable) {
		if (!ErrorHandler.warnings.containsKey(id)) {
			throw new GunGameException("The warning '" + id + "' is unknown!");
		}
		
		if (variable == null && id.endsWith("-VAR")) {
			throw new GunGameException("Variable has to be present!");
		}
		
		String msgId = InfoLayout.replaceKeys(variable != null ? id.replace("VAR", variable) : id);
		String errorMsg = variable == null ? ErrorHandler.warnings.get(id).getMessage() : ErrorHandler.warnings.get(id).getMessage().replace("VAR", variable);
		
		Main.layout().message(Bukkit.getConsoleSender(), "*Warning$-" + msgId + "* occured: " + errorMsg);
		
		if (causedBy != null) {
			String msgCausedBy = InfoLayout.replaceKeys(causedBy);
			
			Main.layout().message(Bukkit.getConsoleSender(), "=> Caused by: -Error$-" + msgCausedBy + "- (" + ErrorHandler.errors.get(causedBy).getMessage() + ")");
		}
	}
	
	public void fixError(String id) {
		if (!ErrorHandler.errors.containsKey(id)) {
			throw new GunGameException("The errorid '" + id + "' is unknown!");
		}
		
		if (!this.isErrorPresent(id)) {
			throw new GunGameException("The error '" + id + "' is not present!");
		}
		
		if (ErrorHandler.errors.get(id) instanceof FixableError) {
			((FixableError) ErrorHandler.errors.get(id)).fix();
		} else {
			throw new GunGameException("The error '" + id + "' is not fixable!");
		}
		
		for (String error : this.thrownErrors) {
			if (ErrorHandler.errors.get(error).getSuperErrors().contains(id)) {
				if (ErrorHandler.errors.get(error) instanceof FixableError) {
					this.fixError(error);
				}
			}
		}
	}
	
	public String getCause(String id) {
		if (!ErrorHandler.errors.containsKey(id)) {
			throw new GunGameException("The errorid '" + id + "' is unknown!");
		}
		
		if (!this.isErrorPresent(id)) {
			throw new GunGameException("The error '" + id + "' is not present!");
		}
		
		String targetError = null;
		
		for (String error : this.thrownErrors) {
			if (ErrorHandler.errors.get(error).getSuperErrors().contains(id)) {
				targetError = error;
			}
		}
		
		if (targetError == null) {
			return id;
		} else {
			return this.getCause(targetError);
		}
	}
	
	public boolean isErrorPresent(String id) {
		return this.thrownErrors.contains(id);
	}
	
}
