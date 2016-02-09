package eu.securebit.gungame.errorhandling.objects;

import eu.securebit.gungame.errorhandling.layouts.LayoutWarning;

public class Warning extends ThrowableObject<LayoutWarning> {
	
	public Warning(String errorId) {
		this(errorId, new String[0]);
	}
	
	public Warning(String errorId, String... variables) {
		super(errorId, variables);
	}

	@Override
	public ThrowableObjectType getObjectType() {
		return ThrowableObjectType.WARNING;
	}
	
	@Override
	public String getOccuredFormat() {
		return "*%s* occured (%s)";
	}
	
	@Override
	public String getTriggeredFormat() {
		return "=> triggers: *%s* (%s)";
	}
	
	@Override
	public String getCausesFormat() {
		return "=> caused by: *%s* (%s)";
	}
	
}

