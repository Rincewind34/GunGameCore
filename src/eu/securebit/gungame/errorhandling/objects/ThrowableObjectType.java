package eu.securebit.gungame.errorhandling.objects;

public enum ThrowableObjectType {
	
	ERROR("Error"),
	TEMP_ERROR("TempError"),
	WARNING("Warning");
	
	private String name;
	
	private ThrowableObjectType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}