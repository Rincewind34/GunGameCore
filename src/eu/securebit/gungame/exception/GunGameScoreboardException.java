package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameScoreboardException extends GunGameException {
	
	public static GunGameScoreboardException boardIsDisabled() {
		return new GunGameScoreboardException("The scoreboard is disabled!");
	}
	
	public static GunGameScoreboardException noObjective() {
		return new GunGameScoreboardException("The objective does not exist!");
	}
	
	public static GunGameScoreboardException existingObjective() {
		return new GunGameScoreboardException("The objective does already exist!");
	}
	
	protected GunGameScoreboardException(String msg) {
		super(msg);
	}
	
}
