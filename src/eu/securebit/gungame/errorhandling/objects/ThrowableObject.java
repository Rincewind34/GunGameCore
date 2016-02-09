package eu.securebit.gungame.errorhandling.objects;

import lib.securebit.InfoLayout;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.layouts.Layout;
import eu.securebit.gungame.exception.GunGameException;

public abstract class ThrowableObject<T extends Layout> {
	
	private String objectId;
	
	private String[] variables;
	
	private T layout;
	
	public ThrowableObject(String objectId) {
		this(objectId, new String[0]);
	}
	
	@SuppressWarnings("unchecked")
	public ThrowableObject(String objectId, String... variables) {
		if (!CraftErrorHandler.layouts.containsKey(objectId)) {
			throw new GunGameException("The objectId '" + objectId + "' is unknown!");
		}
		
		this.objectId = objectId;
		this.variables = variables;
		this.layout = (T) CraftErrorHandler.layouts.get(objectId);
	}
	
	public String getObjectId() {
		return this.objectId;
	}
	
	public String getParsedMessage() {
		return InfoLayout.replaceKeys(this.integrateVariables(this.layout.getMessage(), this.variables));
	}
	
	public String getParsedObjectId() {
		return InfoLayout.replaceKeys(this.integrateVariables(this.objectId, this.variables));
	}
	
	public T getLayout() {
		return this.layout;
	}
	
	public String[] getVariables() {
		return this.variables;
	}
	
	public abstract String getOccuredFormat();
	
	public abstract String getTriggeredFormat();
	
	public abstract String getCausesFormat();
	
	public abstract ThrowableObjectType getObjectType();
	
	private String integrateVariables(String input, String[] variables) {
		for (int i = 0; i < variables.length; i++) {
			String var = "VAR" + Integer.toString(i);
			
			if (input.contains(var)) {
				input = input.replace(var, InfoLayout.replaceKeys(variables[i]));
			}
		}
		
		return input;
	}
	
}