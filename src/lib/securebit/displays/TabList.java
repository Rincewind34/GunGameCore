package lib.securebit.displays;

import java.util.List;

import lib.securebit.ReflectionUtil;

import org.bukkit.entity.Player;

public class TabList extends Sendable {
	
	private String header;
	private String footer;
	
	public TabList(String header, String footer) {
		this.header = (header != null ? header : "");
		this.footer = (footer != null ? footer : "");
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public String getFooter() {
		return this.footer;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	@Override
	public void send(Player player) {
		super.sendPacket(player, TabList.createPacket(this.header, this.footer));
	}
	
	public void send(List<Player> players) {
		for (Player player : players) {
			this.send(player);
		}
	}
	
	public static Object createPacket(String header, String footer) {
		Object textHeader = ReflectionUtil.createObject(DisplayReflection.METHOD_A, "{\"text\": \"" + header + "\"}");
		Object textFooter = ReflectionUtil.createObject(DisplayReflection.METHOD_A, "{\"text\": \"" + footer + "\"}");
		Object packet = ReflectionUtil.createObject(DisplayReflection.CLASS_PACKET_TABLIST);
		
		ReflectionUtil.setValue(DisplayReflection.FIELD_TABLIST_HEADER, packet, textHeader);
		ReflectionUtil.setValue(DisplayReflection.FIELD_TABLIST_FOOTER, packet, textFooter);
		
		return packet;
	}
	
	
}
