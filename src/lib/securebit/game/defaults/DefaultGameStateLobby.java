package lib.securebit.game.defaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.countdown.Countdown;
import lib.securebit.countdown.DefaultCountdown;
import lib.securebit.countdown.TimeListener;
import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.impl.CraftGameStateLobby;
import lib.securebit.game.util.HotbarItem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Main;

public abstract class DefaultGameStateLobby extends CraftGameStateLobby {
	
	private String permPremium;
	private String permStaff;
	
	private int minPl;
	private int maxPl;
	
	private boolean joinable;
	
	private Countdown countdown;
	
	private List<HotbarItem> items;
	
	public DefaultGameStateLobby(Game<? extends GamePlayer> game, Location lobby, String permPremium,
			String permStaff, int maxPl, int minPl, int countdownLength, boolean joinable) {
		super(game, lobby);
		
		this.items = new ArrayList<>();
		
		this.minPl = minPl;
		this.maxPl = maxPl;
		this.permPremium = permPremium;
		this.permStaff = permStaff;
		this.joinable = joinable;
		
		this.countdown = new DefaultCountdown(this.getGame().getPlugin(), countdownLength) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				String msg = DefaultGameStateLobby.this.getMessageCountdown(secondsLeft);
				
				if (msg != null) {
					Bukkit.broadcastMessage(msg);
				}
			}
			
			@Override
			public void onStop(int secondsSkipped) {
				DefaultGameStateLobby.this.onCountdownStop();
			}
			
			@Override
			public boolean isAnnounceTime(int secondsLeft) {
				return DefaultGameStateLobby.this.isCountdownAnnounceTime(secondsLeft);
			}
		};
	}
	
	@Override
	public void load() {
		super.load();
		
		for (GamePlayer player : this.getGame().getPlayers()) {
			Main.instance().getGame().resetPlayer(player.getHandle());
			player.getHandle().teleport(Main.instance().getFileConfig().getLocationLobby());
		}
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		if (this.countdown.isRunning()) {
			this.countdown.stop();
		}
	}
	
	public Countdown getCountdown() {
		return this.countdown;
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
		
		for (HotbarItem item : this.items) {
			player.getInventory().setItem(item.getSlot(), item.getHandle());
		}
		
		if (this.minPl <= this.getGame().getPlayers().size()) {
			if (!this.countdown.isRunning()) {
				this.countdown.restart();
			}
		}
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
	}
	
	protected boolean isCountdownAnnounceTime(int secondsLeft) {
		return TimeListener.defaultAnnounceTime(secondsLeft);
	}
	
	protected void onCountdownStop() {
		if (this.minPl > this.getGame().getPlayers().size()) {
			//TODO Message
		} else {
			this.getGame().getManager().next();
		}
	}
	
	protected abstract String getKickMessage(int levelKicked);
	
	protected abstract String getMessageServerFull();
	
	protected abstract String getMessageNotJoinable();

	protected abstract String getMessageCountdown(int secondsLeft);
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		this.getGame().getPlayers().forEach((gameplayer) -> {
			if (!this.joinable) {
				event.disallow(Result.KICK_OTHER, this.getMessageNotJoinable());
				return;
			}
			
			Player player = event.getPlayer();
			
			if (player.equals(gameplayer.getHandle())) {
				int onlinePlayers = this.getGame().getPlayers().size();
				
				if (onlinePlayers < this.maxPl) {
					event.allow();
				}
				
				if (player.hasPermission(this.permPremium) || player.hasPermission(this.permStaff)) {
					List<Player> kickable = new ArrayList<>();
					
					int levelPermitted = this.getPermissionLevel(player);
					
					for (GamePlayer target : this.getGame().getPlayers()) {
						int levelVictim = this.getPermissionLevel(target.getHandle());
						
						if (levelPermitted > levelVictim) {
							kickable.add(target.getHandle());
						}
					}
					
					Collections.shuffle(kickable);
					
					if (kickable.size() == 0) {
						event.disallow(Result.KICK_FULL, this.getMessageServerFull());
						return;
					}
					
					kickable.get(0).kickPlayer(this.getKickMessage(levelPermitted));
					event.allow();
				} else {
					event.disallow(Result.KICK_FULL, this.getMessageServerFull());
				}
				
				return;
			}
		});
	}
	
	@EventHandler
	public final void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			this.getGame().getPlayers().forEach((gameplayer) -> {
				Player player = event.getPlayer();
				
				if (player.equals(gameplayer.getHandle())) {
					for (HotbarItem item : this.items) {
						if (item.getSlot() == player.getInventory().getHeldItemSlot()) {
							event.setCancelled(true);
							item.getAction().accept(player);
							return;
						}
					}
					
					return;
				}
			});
		}
	}
	
	private int getPermissionLevel(Player p) {
		int level = 0;
		
		if (p.hasPermission(this.permPremium)) {
			level = 1;
		}
		
		if (p.hasPermission(this.permStaff)) {
			level = 2;
		}
		
		if (p.isOp()) {
			level = 3;
		}
		
		return level;
	}
}
