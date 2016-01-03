package lib.securebit.countdown;

public interface CountdownListener {
	
	public abstract void onStart(int secoundsLeft);
	
	public abstract void onStop(int skippedSecounds);
	
	public abstract void onRestart(int startSecounds);
	
	public abstract void onInterrupt(int secoundsLeft);
	
}
