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
	}),
	UNICORN((layout) -> {
		layout.colorPrimary = "§7";
		layout.colorSecondary = "§d";
		layout.colorPositiv = "§f";
		layout.colorNegative = "§e";
		layout.colorImportant = "§5";
	}),
	BIO((layout) -> {
		layout.colorPrimary = "§8";
		layout.colorSecondary = "§2";
		layout.colorPositiv = "§e";
		layout.colorNegative = "§7";
		layout.colorImportant = "§a";
	});
	
	
	public static final String ERROR_MAIN = 		"Error-1700";
	
	public static final String ERROR_ENTRY = 		"Error-1710";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("The colorset given from the bootconfig could not be resolved", RootDirectory.ERROR_MAIN);
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
