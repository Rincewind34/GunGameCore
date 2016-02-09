package eu.securebit.gungame.errorhandling.layouts;


public class LayoutErrorFixable extends LayoutError {
	
	private Runnable action;
	
	public LayoutErrorFixable(String message, Runnable action) {
		this(message, action, new String[0]);
	}
	
	public LayoutErrorFixable(String message, String superError, Runnable action) {
		this(message, action, new String[] { superError });
	}
	
	public LayoutErrorFixable(String message, Runnable action, String... superError) {
		super(message, superError);
		
		this.action = action;
	}
	
	public void fix() {
		this.action.run();
	}

}
