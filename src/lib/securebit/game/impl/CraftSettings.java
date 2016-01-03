package lib.securebit.game.impl;

import java.util.HashMap;
import java.util.Map;

import lib.securebit.game.Settings;

public class CraftSettings implements Settings {
	
	private Map<Option<Object>, Object> values;
	
	public CraftSettings() {
		this.values = new HashMap<>();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void setValue(Option<T> option, T value) {
		this.values.put((Option<Object>) option, value);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getValue(Option<T> option) {
		return (T) this.values.get(option);
	}

}
