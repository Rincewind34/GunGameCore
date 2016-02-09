package eu.securebit.gungame.errorhandling.objects;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;


public class ThrownError extends ThrowableObject<LayoutError> {
	
	public ThrownError(String errorId) {
		this(errorId, new String[0]);
	}
	
	public ThrownError(String errorId, String... variables) {
		super(errorId, variables);
	}

	@Override
	public ThrowableObjectType getObjectType() {
		return ThrowableObjectType.ERROR;
	}
	
	@Override
	public String getOccuredFormat() {
		return "-%s occured (%s)-";
	}
	
	@Override
	public String getTriggeredFormat() {
		return "-=> triggers: %s (%s)-";
	}
	
	@Override
	public String getCausesFormat() {
		return "-=> caused by: %s (%s)-";
	}
	
}
