package lib.securebit.game.impl;

import java.util.ArrayList;
import java.util.List;

import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.GameState;
import lib.securebit.game.Settings;
import lib.securebit.game.Settings.StateSettings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public abstract class CraftGameState implements GameState {
	
	private List<Listener> listeners;
	
	private Settings settings;
	
	private Game<? extends GamePlayer> game;
	
	public CraftGameState(Game<? extends GamePlayer> game) {
		this.listeners = new ArrayList<>();
		this.settings = new CraftSettings();
		this.game = game;
	}
	
	@Override
	public void load() {
		this.game.getWorlds().forEach(world -> {
			world.setStorm((this.settings.getValue(StateSettings.WEATHER) & 0x01) == 0);
			world.setThundering((this.settings.getValue(StateSettings.WEATHER) & 0x02) == 0);
			world.setFullTime((long) this.settings.getValue(StateSettings.TIME));
			world.setGameRuleValue("doDaylightCycle", "false");
		});
	}
	
	@Override
	public void unload() {
		this.game.getWorlds().forEach(world -> {
			world.setGameRuleValue("doDaylightCycle", "true");
		});
	}
	
	@Override
	public final void registerListener(Plugin plugin) {
		this.listeners.forEach((listener) -> {
			Bukkit.getPluginManager().registerEvents(listener, plugin);
		});
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public final void unregisterListener(Plugin plugin) {
		this.listeners.forEach((listener) -> {
			HandlerList.unregisterAll(listener);
		});
		
		HandlerList.unregisterAll(this);
	}

	@Override
	public final List<Listener> getListeners() {
		return this.listeners;
	}
	
	@Override
	public Settings getSettings() {
		return this.settings;
	}
	
	@EventHandler
	private final void onFoodLevelChange(FoodLevelChangeEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getEntity())) {
				event.setCancelled(!this.settings.getValue(StateSettings.PLAYER_FOODLEVEL_CHANGE));
				return;
			}
		});
	}
	
	@EventHandler
	private final void onBlockPlace(BlockPlaceEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				List<Material> list = this.settings.getValue(StateSettings.BLOCK_PLACE);
				
				if (list.contains(event.getBlock().getType())) {
					event.setCancelled(true);
					return;
				}
			}
		});
	}
	
	@EventHandler
	private final void onBreakPlace(BlockBreakEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				List<Material> list = this.settings.getValue(StateSettings.BLOCK_BREAK);
				
				if (list.contains(event.getBlock().getType())) {
					event.setCancelled(true);
					return;
				}
			}
		});
	}
	
	@EventHandler
	private final void onWeatherChange(WeatherChangeEvent event) {
		this.game.getWorlds().forEach((world) -> {
			if (world.equals(event.getWorld())) {
				event.setCancelled(true);
				return;
			}
		});
	}
	
	@EventHandler
	private final void onThunderChange(ThunderChangeEvent event) {
		this.game.getWorlds().forEach((world) -> {
			if (world.equals(event.getWorld())) {
				event.setCancelled(true);
				return;
			}
		});
	}
	
	@EventHandler
	private final void onItemDrop(PlayerDropItemEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				event.setCancelled(!this.settings.getValue(StateSettings.ITEM_DROP));
				return;
			}
		});
	}
	
	@EventHandler
	private final void onItemPickup(PlayerPickupItemEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				event.setCancelled(!this.settings.getValue(StateSettings.ITEM_PICKUP));
				return;
			}
		});
	}
	
	@EventHandler
	private final void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			this.game.getPlayers().forEach((player) -> {
				if (player.getHandle().equals(event.getEntity())) {
					event.setCancelled(!this.settings.getValue(StateSettings.PLAYER_DAMAGE_FIGHT));
					return;
				}
			});
		}
	}
	
	@EventHandler
	private final void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			this.game.getPlayers().forEach((player) -> {
				if (player.getHandle().equals(event.getEntity())) {
					if (event.getCause() == DamageCause.FALL) {
						event.setCancelled(!this.settings.getValue(StateSettings.PLAYER_DAMAGE_FALL));
						return;
					} else if (event.getCause() != DamageCause.ENTITY_ATTACK) {
						if (!this.settings.getValue(StateSettings.PLAYER_DAMAGE_NATURAL)) {
							event.setCancelled(true);
							
							if (event.getCause() == DamageCause.POISON) {
								player.getHandle().removePotionEffect(PotionEffectType.POISON);
							} else if (event.getCause() == DamageCause.WITHER) {
								player.getHandle().removePotionEffect(PotionEffectType.WITHER);
							} else if (event.getCause() == DamageCause.VOID ||
									event.getCause() == DamageCause.SUFFOCATION) {
								this.teleportPlayer(player.getHandle());
							} else if (event.getCause() == DamageCause.FIRE ||
									event.getCause() == DamageCause.FIRE_TICK) {
								player.getHandle().setFireTicks(0);
							}
						}
						
						return;
					}
				}
			});
		}
	}
	
	@EventHandler
	private final void onJoin(PlayerJoinEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				event.setJoinMessage("");
				
				if (this.settings.getValue(StateSettings.MESSAGE_JOIN) != null) {
					Bukkit.broadcastMessage(this.settings.getValue(StateSettings.MESSAGE_JOIN).replace("${player}",
							event.getPlayer().getDisplayName()));
				}
				
				this.onJoin(event.getPlayer());
				
				return;
			}
		});
	}
	
	@EventHandler
	private final void onQuit(PlayerQuitEvent event) {
		this.game.getPlayers().forEach((player) -> {
			if (player.getHandle().equals(event.getPlayer())) {
				event.setQuitMessage("");
				
				if (this.settings.getValue(StateSettings.MESSAGE_QUIT) != null) {
					Bukkit.broadcastMessage(this.settings.getValue(StateSettings.MESSAGE_QUIT).replace("${player}",
							event.getPlayer().getDisplayName()));
				}
				
				this.onJoin(event.getPlayer());
				
				return;
			}
		});
	}
	
	public Game<? extends GamePlayer> getGame() {
		return this.game;
	}
	
	protected void teleportPlayer(Player player) {
		
	}
	
	protected void onJoin(Player player) {
		
	}
	
	protected void onQuit(Player player) {
		
	}
	
}
