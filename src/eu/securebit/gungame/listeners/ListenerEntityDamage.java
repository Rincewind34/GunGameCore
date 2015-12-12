package eu.securebit.gungame.listeners;

import java.util.List;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.states.GameStateGrace;

public class ListenerEntityDamage extends DefaultListener {
	
	public ListenerEntityDamage() {
		super(ListenerEntityDamage.class, EntityDamageEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.end" })
	private static void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
			
			if (event.getCause() == DamageCause.FIRE_TICK) {
				event.getEntity().setFireTicks(0);
			} else if (event.getCause() == DamageCause.VOID || event.getCause() == DamageCause.SUFFOCATION) {
				if (Main.instance().getGameStateManager().getCurrent() instanceof GameStateGrace) {
					List<Location> spawns = Main.instance().getFileConfig().getSpawns();
					event.getEntity().teleport(spawns.get(Main.random().nextInt(spawns.size())));
				} else {
					event.getEntity().teleport(Main.instance().getFileConfig().getLocationLobby());
				}
			} else if (event.getCause() == DamageCause.WITHER) {
				((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.WITHER);
			} else if (event.getCause() == DamageCause.POISON) {
				((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.POISON);
			}
		}
	}
	
}
