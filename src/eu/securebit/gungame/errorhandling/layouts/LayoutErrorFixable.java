package eu.securebit.gungame.errorhandling.layouts;

import java.util.function.Consumer;

public class LayoutErrorFixable extends LayoutError {
	
	private Consumer<String[]> action;
	
	private String description;
	
	private boolean confirm;
	
	public LayoutErrorFixable(String message, Consumer<String[]> action) {
		this(message, action, false, null, new String[0]);
	}
	
	public LayoutErrorFixable(String message, String superError, Consumer<String[]> action, boolean confirm, String description) {
		this(message, action, confirm, description, new String[] { superError });
	}
	
	public LayoutErrorFixable(String message, Consumer<String[]> action, boolean confirm, String description, String... superError) {
		super(message, superError);
		
		this.action = action;
		this.confirm = confirm;
		this.description = description;
	}
	
	public void fix(String[] variables) {
		this.action.accept(variables);
	}
	
	public boolean withConfirm() {
		return this.confirm;
	}
	
	public String getDescription() {
		return this.description;
	}
		
}
