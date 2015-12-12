package eu.securebit.gungame.io;

import eu.securebit.gungame.exception.MalformedConfigException;

public interface FileValidatable {

	public abstract void validate() throws MalformedConfigException;
}
