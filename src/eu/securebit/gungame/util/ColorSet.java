package eu.securebit.gungame.util;

import java.util.function.Consumer;

import eu.securebit.gungame.Main;
import lib.securebit.InfoLayout;

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
	
	private Consumer<InfoLayout> action;
	
	private ColorSet(Consumer<InfoLayout> action) {
		this.action = action;
	}
	
	public void prepare(InfoLayout layout) {
		this.action.accept(layout);
		layout.createPrefix(Main.PREFIX_CORE);
	}
	
}
