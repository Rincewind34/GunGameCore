package lib.securebit.countdown;

public interface TimeListener {
	
	public abstract boolean isAnnounceTime(int secondsLeft);
	
	public abstract void onAnnounceTime(int secondsLeft);

	public static boolean defaultAnnounceTime(int secondsLeft) {
		return secondsLeft % 120 == 0 || secondsLeft <= 60 && secondsLeft % 15 == 0 || secondsLeft == 10 || secondsLeft <= 5;
	}
}
