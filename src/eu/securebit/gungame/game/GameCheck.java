package eu.securebit.gungame.game;

import lib.securebit.InfoLayout;
import eu.securebit.gungame.util.Util;

public abstract class GameCheck {
	
	private String name;
	
	protected GunGame gungame;
	
	public GameCheck(GunGame gungame, String name) {
		this.name = name;
		this.gungame = gungame;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void stageStatus(InfoLayout layout) {
		boolean check = this.check();
		
		layout.line(InfoLayout.replaceKeys(this.name) + ": " + Util.parseBoolean(check, layout));
		
		if (!check) {
			if (this.getFailCause() != null) {
				layout.line("  *Cause*: " + this.getFailCause());
			}
			
			if (this.getFixPosibility() != null) {
				layout.line("  *Fix*: "  + this.getFixPosibility());
			}
		}
	}
	
	public abstract boolean check();
	
	public abstract String getFailCause();
	
	public abstract String getFixPosibility();
	
}
