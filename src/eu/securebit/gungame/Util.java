package eu.securebit.gungame;

public class Util {
	
	public static boolean isInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static String getGameStateName() {
		return Main.instance().getGameStateManager().getCurrent().getClass().getSimpleName().
				replace("GameState", "").
				replace("DisabledState", "");
	}
	
}
