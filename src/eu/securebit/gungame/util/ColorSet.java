package eu.securebit.gungame.util;

import java.util.function.Consumer;

import lib.securebit.InfoLayout;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.io.directories.RootDirectory;

public enum ColorSet {
	
	DEFAULT((layout) -> {
		layout.colorPrimary = "§8";
		layout.colorSecondary = "§r";
		layout.colorPositiv = "§2";
		layout.colorNegative = "§4";
		layout.colorImportant = "§6";
	}),
	RETRO((layout) -> {
		layout.colorPrimary = "§8";
		layout.colorSecondary = "§7";
		layout.colorPositiv = "§a";
		layout.colorNegative = "§4";
		layout.colorImportant = "§f";
	}),
	OCEAN((layout) -> {
		layout.colorPrimary = "§8";
		layout.colorSecondary = "§3";
		layout.colorPositiv = "§f";
		layout.colorNegative = "§c";
		layout.colorImportant = "§b";
	});
	
	
	public static final String ERROR_MAIN = 		"1|007|000|000";
	
	public static final String ERROR_ENTRY = 		"1|007|001|000";
	
	public static LayoutError createErrorMain() {
		return new SimpleErrorLayout("The colorset given from the bootconfig could not be resolved", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorEntry() {
		return new LayoutErrorFixable("The colorset given from the bootconfig is invalid", ColorSet.ERROR_MAIN, () -> {
			// TODO set color-set to ColorSet.DEFAULT
		});
	}
	
	
	private Consumer<InfoLayout> action;
	
	private ColorSet(Consumer<InfoLayout> action) {
		this.action = action;
	}
	
	public void prepare(InfoLayout layout) {
		this.action.accept(layout);
		layout.createPrefix(Main.PREFIX_CORE);
	}
	
}
