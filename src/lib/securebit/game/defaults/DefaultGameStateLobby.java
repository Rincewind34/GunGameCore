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

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class DefaultGameStateLobby<G extends Game<? extends GamePlayer>> extends CraftGameStateLobby<G> {
	
	private String permPremium;
	private String permStaff;
	
	private int minPl;
	private int maxPl;
	private int premiumSlots;
	
	private boolean joinable;
	private boolean premiumKick;
	
	private Countdown countdown;
	
	private List<HotbarItem> items;
	
	public DefaultGameStateLobby(G game, Location lobby, String permPremium,
			String permStaff, int maxPl, int minPl, int countdownLength, int premiumSlots, boolean joinable, boolean premiumKick) {
		super(game, lobby);
		
		this.items = new ArrayList<>();
		
		this.minPl = minPl;
		this.maxPl = maxPl;
		this.premiumSlots = premiumSlots;
		this.permPremium = permPremium;
		this.permStaff = permStaff;
		this.joinable = joinable;
		this.premiumKick = premiumKick;
		
		this.countdown = new DefaultCountdown(this.getGame().getPlugin(), countdownLength) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				String msg = DefaultGameStateLobby.this.getMessageCountdown(secondsLeft);
				
				if (msg != null) {
					DefaultGameStateLobby.this.getGame().broadcastMessage(msg);
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
	public void start() {
		
	}

	@Override
	public void stop() {
		if (this.countdown.isRunning()) {
			this.countdown.stop(false);
		}
	}
	
	@Override
	public String getName() {
		return "lobby";
	}
	
	@Override
	public void load() {
		super.load();
		
		for (GamePlayer player : this.getGame().getPlayers()) {
			this.getGame().resetPlayer(player.getHandle());
			player.getHandle().teleport(this.getLobby());
		}
	}
	
	public Countdown getCountdown() {
		return this.countdown;
	}
	
	public boolean canUserJoin() {
		return this.getGame().getPlayers().size() < (this.maxPl - this.premiumSlots);
	}
	
	public boolean canPremiumJoin() {
		if (this.getGame().getPlayers().size() < this.maxPl) {
			return true;
		} else {
			return this.premiumKick;
		}
	}
	
	@Override
	protected String onLogin(Player player) {
		if (!this.joinable) {
			return this.getMessageNotJoinable();
		}
		
		if (this.canUserJoin()) {
			return null;
		}
		
		if (player.hasPermission(this.permStaff)) {
			if (this.kickPlayer(this.getPermissionLevel(player))) {
				return null;
			} else {
				return this.getMessageServerFull();
			}
		} else if (player.hasPermission(this.permPremium)) {
			if (this.getGame().getPlayers().size() < this.maxPl) {
				return null;
			} else if (this.premiumKick) {
				if (this.kickPlayer(this.getPermissionLevel(player))) {
					return null;
				} else {
					return this.getMessageServerFull();
				}
			} else {
				return this.getMessageServerFull();
			}
		} else {
			return this.getMessageServerFull();
		}
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
			this.getGame().broadcastMessage(this.getMessageCountdownCancle());
		} else {
			this.getGame().getManager().next();
		}
	}
	
	protected abstract String getKickMessage(int levelKicked);
	
	protected abstract String getMessageServerFull();
	
	protected abstract String getMessageNotJoinable();

	protected abstract String getMessageCountdown(int secondsLeft);
	
	protected abstract String getMessageCountdownCancle();
	
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
		
		if (p.hasPermission(this.permStaff)) {
			level = 2;
		}
		
		if (p.hasPermission(this.permPremium)) {
			level = 1;
		}
		
		return level;
	}
	
	private boolean kickPlayer(int levelPermitted) {
		List<Player> kickable = new ArrayList<>();
		
		for (GamePlayer target : this.getGame().getPlayers()) {
			int levelVictim = this.getPermissionLevel(target.getHandle());
			
			if (levelPermitted > levelVictim) {
				kickable.add(target.getHandle());
			}
		}
		
		Collections.shuffle(kickable);
		
		if (kickable.size() == 0) {
			return false;
		}
		
		this.getGame().kickPlayer(kickable.get(0), this.getKickMessage(levelPermitted));
		return true;
	}
}
