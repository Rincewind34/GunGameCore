package eu.securebit.gungame.errorhandling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lib.securebit.InfoLayout;

import org.bukkit.Bukkit;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.errorhandling.layouts.Layout;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutTemporyError;
import eu.securebit.gungame.errorhandling.layouts.LayoutWarning;
import eu.securebit.gungame.errorhandling.objects.TempError;
import eu.securebit.gungame.errorhandling.objects.ThrowableObject;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.errorhandling.objects.Warning;
import eu.securebit.gungame.exception.GunGameErrorHandlerException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMap;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.util.ColorSet;

public class CraftErrorHandler implements ErrorHandler {
	
	public static final Map<String, Layout> layouts = new HashMap<>();
	
	static {
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_MAIN, RootDirectory.createErrorMain());
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_FILE, RootDirectory.createErrorFile());
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_CREATE, RootDirectory.createErrorCreate());
		CraftErrorHandler.layouts.put(FileConfigRegistry.ERROR_LOAD, FileConfigRegistry.createErrorLoad());
		CraftErrorHandler.layouts.put(FileConfigRegistry.ERROR_FOLDER, FileConfigRegistry.createErrorFolder());
		CraftErrorHandler.layouts.put(FileConfigRegistry.ERROR_CREATE, FileConfigRegistry.createErrorCreate());
		CraftErrorHandler.layouts.put(FileConfigRegistry.ERROR_MALFORMED_STRUCTURE, FileConfigRegistry.createErrorMalformedStructure());
		CraftErrorHandler.layouts.put(FileConfigRegistry.ERROR_MALFORMED_ENTRIES, FileConfigRegistry.createErrorMalformedEntries());
		CraftErrorHandler.layouts.put(FileBootConfig.ERROR_LOAD, FileBootConfig.createErrorLoad());
		CraftErrorHandler.layouts.put(FileBootConfig.ERROR_FOLDER, FileBootConfig.createErrorFolder());
		CraftErrorHandler.layouts.put(FileBootConfig.ERROR_CREATE, FileBootConfig.createErrorCreate());
		CraftErrorHandler.layouts.put(FileBootConfig.ERROR_MALFORMED, FileBootConfig.createErrorMalformed());
		CraftErrorHandler.layouts.put(AddonDirectory.ERROR_MAIN, AddonDirectory.createErrorMain());
		CraftErrorHandler.layouts.put(AddonDirectory.ERROR_FILE, AddonDirectory.createErrorFile());
		CraftErrorHandler.layouts.put(AddonDirectory.ERROR_CREATE, AddonDirectory.createErrorCreate());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_MAIN, BootDirectory.createErrorMain());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_FILE, BootDirectory.createErrorFile());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_CREATE, BootDirectory.createErrorCreate());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_BOOTDATA_FOLDER, BootDirectory.createErrorBootdataFolder());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_BOOTDATA_CREATE, BootDirectory.createErrorBootdataCreate());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_BOOTDATA_MALFORMED, BootDirectory.createErrorBootdataMalformed());
		CraftErrorHandler.layouts.put(BootDirectory.ERROR_BOOTDATA_SAVE, BootDirectory.createErrorBootdataSave());
		CraftErrorHandler.layouts.put(ColorSet.ERROR_MAIN, ColorSet.createErrorMain());
		CraftErrorHandler.layouts.put(ColorSet.ERROR_ENTRY, ColorSet.createErrorEntry());
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_FRAME, RootDirectory.createErrorFrame());
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_FRAME_EXIST, RootDirectory.createErrorFrameExists());
		CraftErrorHandler.layouts.put(RootDirectory.ERROR_FRAME_NOJAR, RootDirectory.createErrorFrameNojar());
		CraftErrorHandler.layouts.put(Frame.ERROR_LOAD, Frame.createErrorLoad());
		CraftErrorHandler.layouts.put(Frame.ERROR_LOAD_MAINCLASS, Frame.createErrorLoadMainclass());
		CraftErrorHandler.layouts.put(Frame.ERROR_ENABLE, Frame.createErrorEnable());
		CraftErrorHandler.layouts.put(Frame.ERROR_ENABLE_ID, Frame.createErrorEnableId());
		CraftErrorHandler.layouts.put(FileGameConfig.ERROR_LOAD, FileGameConfig.createErrorLoad());
		CraftErrorHandler.layouts.put(FileGameConfig.ERROR_FOLDER, FileGameConfig.createErrorFolder());
		CraftErrorHandler.layouts.put(FileGameConfig.ERROR_CREATE, FileGameConfig.createErrorCreate());
		CraftErrorHandler.layouts.put(FileGameConfig.ERROR_MALFORMED, FileGameConfig.createErrorMalformed());
		CraftErrorHandler.layouts.put(FileMessages.ERROR_LOAD, FileMessages.createErrorLoad());
		CraftErrorHandler.layouts.put(FileMessages.ERROR_FOLDER, FileMessages.createErrorFolder());
		CraftErrorHandler.layouts.put(FileMessages.ERROR_CREATE, FileMessages.createErrorCreate());
		CraftErrorHandler.layouts.put(FileMessages.ERROR_MALFORMED, FileMessages.createErrorMalformed());
		CraftErrorHandler.layouts.put(Messanger.ERROR_MAIN, Messanger.createErrorMain());
		CraftErrorHandler.layouts.put(Messanger.ERROR_INTERPRET, Messanger.createErrorInterpret());
		CraftErrorHandler.layouts.put(FileLevels.ERROR_LOAD, FileLevels.createErrorLoad());
		CraftErrorHandler.layouts.put(FileLevels.ERROR_FOLDER, FileLevels.createErrorFolder());
		CraftErrorHandler.layouts.put(FileLevels.ERROR_CREATE, FileLevels.createErrorCreate());
		CraftErrorHandler.layouts.put(FileLevels.ERROR_MALFORMED, FileLevels.createErrorMalformed());
		CraftErrorHandler.layouts.put(LevelManager.ERROR_MAIN, LevelManager.createErrorMain());
		CraftErrorHandler.layouts.put(LevelManager.ERROR_INTERPRET, LevelManager.createErrorInterpret());
		CraftErrorHandler.layouts.put(LevelManager.ERROR_LEVELCOUNT, LevelManager.createErrorLevelCount());
		CraftErrorHandler.layouts.put(FileScoreboard.ERROR_LOAD, FileScoreboard.createErrorLoad());
		CraftErrorHandler.layouts.put(FileScoreboard.ERROR_FOLDER, FileScoreboard.createErrorFolder());
		CraftErrorHandler.layouts.put(FileScoreboard.ERROR_CREATE, FileScoreboard.createErrorCreate());
		CraftErrorHandler.layouts.put(FileScoreboard.ERROR_MALFORMED, FileScoreboard.createErrorMalformed());
		CraftErrorHandler.layouts.put(GunGameScoreboard.ERROR_MAIN, GunGameScoreboard.createErrorMain());
		CraftErrorHandler.layouts.put(GunGameScoreboard.ERROR_INTERPRET, GunGameScoreboard.createErrorInterpret());
		CraftErrorHandler.layouts.put(GunGameScoreboard.ERROR_TITLE, GunGameScoreboard.createErrorTitle());
		CraftErrorHandler.layouts.put(GunGameScoreboard.ERROR_FORMAT, GunGameScoreboard.createErrorFormat());
		CraftErrorHandler.layouts.put(FileOptions.ERROR_LOAD, FileOptions.createErrorLoad());
		CraftErrorHandler.layouts.put(FileOptions.ERROR_FOLDER, FileOptions.createErrorFolder());
		CraftErrorHandler.layouts.put(FileOptions.ERROR_CREATE, FileOptions.createErrorCreate());
		CraftErrorHandler.layouts.put(FileOptions.ERROR_MALFORMED, FileOptions.createErrorMalformed());
		CraftErrorHandler.layouts.put(GameOptions.ERROR_MAIN, GameOptions.createErrorMain());
		CraftErrorHandler.layouts.put(GameOptions.ERROR_INTERPRET, GameOptions.createErrorInterpret());
		CraftErrorHandler.layouts.put(GameOptions.ERROR_LEVELCOUNT_SMALLER, GameOptions.createErrorLevelCountSmaller());
		CraftErrorHandler.layouts.put(GameOptions.ERROR_LEVELCOUNT_GREATER, GameOptions.createErrorLevelCountGreater());
		CraftErrorHandler.layouts.put(FileMap.ERROR_LOAD, FileMap.createErrorLoad());
		CraftErrorHandler.layouts.put(FileMap.ERROR_FOLDER, FileMap.createErrorFolder());
		CraftErrorHandler.layouts.put(FileMap.ERROR_CREATE, FileMap.createErrorCreate());
		CraftErrorHandler.layouts.put(FileMap.ERROR_MALFORMED, FileMap.createErrorMalformed());
		CraftErrorHandler.layouts.put(GunGameMap.ERROR_MAIN, GunGameMap.createErrorMain());
		CraftErrorHandler.layouts.put(GunGameMap.ERROR_INTERPRET, GunGameMap.createErrorInterpret());
		CraftErrorHandler.layouts.put(GunGameMap.ERROR_SPAWNID, GunGameMap.createErrorSpawnId());
		CraftErrorHandler.layouts.put(GunGameMap.ERROR_SPAWNCOUNT, GunGameMap.createErrorSpawnCount());
		CraftErrorHandler.layouts.put(Addon.ERROR_INIT, Addon.createErrorInit());
		CraftErrorHandler.layouts.put(Addon.ERROR_LOAD, Addon.createErrorLoad());
		CraftErrorHandler.layouts.put(Addon.ERROR_ENABLE, Addon.createErrorEnable());
		CraftErrorHandler.layouts.put(Addon.ERROR_ENABLE_DEPENCIES, Addon.createErrorDepencies());
		CraftErrorHandler.layouts.put(Addon.ERROR_ENABLE_FRAME, Addon.createErrorFrame());
		CraftErrorHandler.layouts.put(Addon.ERROR_ENABLE_FRAME_REQUIRED, Addon.createErrorFrameRequired());
		CraftErrorHandler.layouts.put(Addon.ERROR_ENABLE_FRAME_INCOMPATIBLE, Addon.createErrorFrameIncompatible());
	}
	
	private Map<ThrownError, ThrownError> thrownErrors;
	
	public CraftErrorHandler() {
		this.thrownErrors = new HashMap<>();
	}
	
	@Override
	public void throwError(String objectId) {
		this.throwError(objectId, (String) null);
	}
	
	@Override
	public void throwError(String objectId, String causeId) {
		if (!CraftErrorHandler.layouts.containsKey(objectId)) {
			throw GunGameErrorHandlerException.unknownObjectID(objectId);
		}
		
		Layout layout = CraftErrorHandler.layouts.get(objectId);
		ThrowableObject<?> obj = null;
		
		if (layout instanceof LayoutError) {
			obj = new ThrownError(objectId);
		} else if (layout instanceof LayoutTemporyError) {
			obj = new TempError(objectId);
		} else if (layout instanceof LayoutWarning) {
			obj = new Warning(objectId);
		} else {
			throw GunGameErrorHandlerException.layoutType(objectId);
		}
		
		this.throwError(obj, causeId);
	}
	
	@Override
	public void throwError(ThrowableObject<?> object) {
		this.throwError(object, (String) null);
	}
	
	@Override
	public void throwError(ThrowableObject<?> object, String causeId) {
		if (causeId == null) {
			this.throwError(object, (ThrownError) null);
		} else {
			this.throwError(object, this.parseError(causeId));
		}
	}
	
	@Override
	public void throwError(ThrowableObject<?> object, ThrownError cause) {
		this.throwError(object, cause, false);
	}
	
	@Override
	public boolean isErrorPresent(String errorId) {
		return this.isErrorPresent(this.parseError(errorId));
	}
	
	@Override
	public boolean isErrorPresent(ThrownError error) {
		this.checkVars(error);
		
		for (ThrownError target : this.thrownErrors.keySet()) {
			if (target.equals(error)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public ThrownError getCause(String errorId) {
		return this.getCause(this.parseError(errorId));
	}
	
	@Override
	public ThrownError getCause(ThrownError error) {
		if (!this.isErrorPresent(error)) {
			throw GunGameErrorHandlerException.unpresentError(error.getParsedObjectId());
		}
		
		for (ThrownError targetError : this.thrownErrors.keySet()) {
			for (String superError : targetError.getLayout().getSuperErrors()) {
				if (error.equals(new ThrownError(superError, targetError.getVariables()))) {
					return this.getCause(targetError);
				}
			}
		}
		
		if (this.thrownErrors.get(error) != null) {
			return this.getCause(this.thrownErrors.get(error));
		} else {
			return error;
		}
	}
	
	@Override
	public Map<ThrownError, ThrownError> getErrors() {
		return Collections.unmodifiableMap(this.thrownErrors);
	}
	
	private void throwError(ThrowableObject<?> object, ThrownError cause, boolean triggered) {
		this.checkVars(object);
		
		if (!triggered) {
			Main.layout().message(Bukkit.getConsoleSender(), String.format(object.getOccuredFormat(),
					InfoLayout.replaceKeys(object.getParsedObjectId()),
					InfoLayout.replaceKeys(object.getParsedMessage())));
		} else {
			Main.layout().message(Bukkit.getConsoleSender(), String.format(object.getTriggeredFormat(),
					InfoLayout.replaceKeys(object.getParsedObjectId()),
					InfoLayout.replaceKeys(object.getParsedMessage())));
		}
		
		if (cause != null) {
			this.checkVars(cause);
			
			Main.layout().message(Bukkit.getConsoleSender(), String.format(object.getCausesFormat(), 
					InfoLayout.replaceKeys(cause.getParsedObjectId()), InfoLayout.replaceKeys(cause.getParsedMessage())));
		}
		
		if (object instanceof ThrownError) {
			if (!this.thrownErrors.containsKey(object)) {
				this.thrownErrors.put((ThrownError) object, cause);
			}
			
			for (String superId : ((LayoutError) object.getLayout()).getSuperErrors()) {
				this.throwError(new ThrownError(superId, object.getVariables()), null, true);
			}
		}
	}
	
	private void checkVars(ThrowableObject<?> object) {
		if (object.getParsedObjectId().contains("VAR")) {
			throw GunGameErrorHandlerException.variables();
		}
		
		if (object.getParsedMessage().contains("VAR")) {
			throw GunGameErrorHandlerException.variables();
		}
	}
	
	private ThrownError parseError(String errorId) {
		if (CraftErrorHandler.layouts.containsKey(errorId)) {
			if (CraftErrorHandler.layouts.get(errorId) instanceof LayoutError) {
				return new ThrownError(errorId);
			} else {
				throw GunGameErrorHandlerException.layoutType(errorId);
			}
		} else {
			throw GunGameErrorHandlerException.unknownObjectID(errorId);
		}
	}

}
