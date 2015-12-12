package lib.securebit.displays;

import java.util.List;

import lib.securebit.ReflectionUtil;

import org.bukkit.entity.Player;

public abstract class Sendable {
	
	public abstract void send(Player player);
	
	public void send(List<Player> players) {
		for (Player player : players) {
			this.send(player);
		}
	}
	
	protected void sendPacket(Player player, Object packet) {
		Object nmsPlayer = ReflectionUtil.createObject(DisplayReflection.METHOD_GETHANDLE, player, new Object[0]);
		Object playerCon = ReflectionUtil.createObject(DisplayReflection.FIELD_CONNECTION, nmsPlayer);
		
		ReflectionUtil.invokeMethod(DisplayReflection.METHOD_SENDPACKET, playerCon, packet);
	}
	
}
