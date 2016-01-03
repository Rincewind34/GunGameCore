package lib.securebit.countdown;


public interface Countdown {
	
	public abstract void start();
	
	public abstract void restart();
	
	public abstract void stop();
	
	public abstract void interrupt();
	
	public abstract void addTimeListener(TimeListener listener);
	
	public abstract void addTickListener(TickListener listener);
	
	public abstract void addCountdownListener(CountdownListener listener);
	
	public abstract void setStartSeconds(int startSeconds);
	
	public abstract int getStartSeconds();
	
	public abstract int getSecondsLeft();
	
	public abstract boolean isRunning();
	
}
