package lib.securebit.game.events;

import lib.securebit.game.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerGameJoinEvent extends PlayerEvent {
	
	private static HandlerList handlerList = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return PlayerGameJoinEvent.handlerList;
	}
	
	
	private Game<?> game;
	
	public PlayerGameJoinEvent(Player who, Game<?> game) {
		super(who);
		
		this.game = game;
	}
	
	@Override
	public HandlerList getHandlers() {
		return PlayerGameJoinEvent.handlerList;
	}
	
	public Game<?> getGame() {
		return this.game;
	}
	
	
}
