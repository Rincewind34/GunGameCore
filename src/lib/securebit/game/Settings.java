package lib.securebit.game;

import java.util.List;

import org.bukkit.Difficulty;
import org.bukkit.Material;

public interface Settings {
	
	public abstract <T> void setValue(Option<T> option, T value);
	
	public abstract <T> T getValue(Option<T> option);
	
	
	public static class Option<R> {};
	
	public static class StateSettings {
		
		public static final Option<Boolean> PLAYER_FOODLEVEL_CHANGE = new Option<>();
		
		public static final Option<Boolean> PLAYER_DAMAGE_FALL = new Option<>();
		
		public static final Option<Boolean> PLAYER_DAMAGE_FIGHT = new Option<>();
		
		public static final Option<Boolean> PLAYER_DAMAGE_NATURAL = new Option<>();
		
		public static final Option<List<Material>> BLOCK_PLACE = new Option<>();
		
		public static final Option<List<Material>> BLOCK_BREAK = new Option<>();
		
		public static final Option<Boolean> ITEM_DROP = new Option<>();
		
		public static final Option<Boolean> ITEM_PICKUP = new Option<>();
		
		public static final Option<Boolean> ITEM_MOVE = new Option<>();
		
		public static final Option<Integer> WEATHER = new Option<>();
		
		public static final Option<Integer> TIME = new Option<>();
		
		public static final Option<Boolean> DAY_CYCLE = new Option<>();
		
		public static final Option<String> MESSAGE_JOIN = new Option<>();
		
		public static final Option<String> MESSAGE_QUIT = new Option<>();
		
		public static final Option<Difficulty> DIFFICULTY = new Option<>();
		
		public static final Option<Boolean> FIRE_SPREAD = new Option<>();
		
		public static final Option<Boolean> MAP_RESET = new Option<>();
		
	}
	
}
