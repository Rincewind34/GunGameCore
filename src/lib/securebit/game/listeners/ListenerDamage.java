package lib.securebit.game.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

public abstract class ListenerDamage implements Listener {
	
	public abstract void onInWall(EntityDamageEvent event);
	
	public abstract void onInVoid(EntityDamageEvent event);
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
			
			if (event.getCause() == DamageCause.FIRE_TICK) {
				event.getEntity().setFireTicks(0);
			} else if (event.getCause() == DamageCause.VOID) {
				this.onInVoid(event);
			} else if (event.getCause() == DamageCause.SUFFOCATION) {
				this.onInWall(event);
			} else if (event.getCause() == DamageCause.WITHER) {
				((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.WITHER);
			} else if (event.getCause() == DamageCause.POISON) {
				((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.POISON);
			}
		}
	}
	
	@EventHandler
	public void onDamageByBlock(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
	
}
