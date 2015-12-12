package lib.securebit.displays;

import lib.securebit.ReflectionUtil;

import org.bukkit.entity.Player;

public class Title extends Sendable {
	
	private String title;
	private String subtitle;
	
	private TitleTimings timings;
	
	public Title() {
		this.title = "";
		this.subtitle = "";
		this.timings = new TitleTimings();
	}
	
	public Title setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public Title setSubitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}
	
	public Title setTimings(TitleTimings timings) {
		this.timings = timings;
		return this;
	}
	
	public Title setFadeIn(int fadeInSec) {
		this.timings.setFadeIn(fadeInSec);
		return this;
	}
	
	public Title setFadeOut(int fadeOutSec) {
		this.timings.setFadeOut(fadeOutSec);
		return this;
	}
	
	public Title setStay(int staySec) {
		this.timings.setStay(staySec);
		return this;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getSubtitle() {
		return this.subtitle;
	}
	
	public TitleTimings getTimings() {
		return this.timings;
	}
	
	public void send(Player player) {
		super.sendPacket(player, Title.createPacketTimes(this.timings.getFadeIn(), this.timings.getStay(), this.timings.getFadeOut()));
		super.sendPacket(player, Title.createPacketTitle(this.title));
		super.sendPacket(player, Title.createPacketSubtitle(this.subtitle));
	}
	
	public static Object createPacketTitle(String title) {
		Object action = DisplayReflection.CLASS_TITLEACTION.getEnumConstants()[0];
		Object text = ReflectionUtil.createStaticObject(DisplayReflection.METHOD_A, "{\"text\": \"" + title + "\"}");
		Object packet = ReflectionUtil.createObject(DisplayReflection.CONSTRUCTOR_PACKET_TITLE_TITLE, action, text);
		
		return packet;
	}
	
	public static Object createPacketSubtitle(String subtitle) {
		Object action = DisplayReflection.CLASS_TITLEACTION.getEnumConstants()[1];
		Object text = ReflectionUtil.createStaticObject(DisplayReflection.METHOD_A, "{\"text\": \"" + subtitle + "\"}");
		Object packet = ReflectionUtil.createObject(DisplayReflection.CONSTRUCTOR_PACKET_TITLE_TITLE, action, text);
		
		return packet;
	}
	
	public static Object createPacketTimes(int fadeIn, int stay, int fadeOut) {
		return ReflectionUtil.createObject(DisplayReflection.CONSTRUCTOR_PACKET_TITLE_TIMES, fadeIn, stay, fadeOut);
	}
	
	public static Object createPacketClear() {
		Object action = DisplayReflection.CLASS_TITLEACTION.getEnumConstants()[3];
		Object text = ReflectionUtil.createObject(DisplayReflection.METHOD_A, "{\"text\": \"\"}");
		Object packet = ReflectionUtil.createObject(DisplayReflection.CONSTRUCTOR_PACKET_TITLE_TITLE, action, text);
		
		return packet;
	}
	
	public static Object createPacketReset() {
		Object action = DisplayReflection.CLASS_TITLEACTION.getEnumConstants()[4];
		Object text = ReflectionUtil.createObject(DisplayReflection.METHOD_A, "{\"text\": \"\"}");
		Object packet = ReflectionUtil.createObject(DisplayReflection.CONSTRUCTOR_PACKET_TITLE_TITLE, action, text);
		
		return packet;
	}
	
	public static class TitleTimings {
		
		private int fadeIn;
		private int fadeOut;
		private int stay;
		
		public TitleTimings() {
			
		}
		
		public TitleTimings(int fadeIn, int fadeOut, int stay) {
			this.fadeIn = fadeIn;
			this.fadeOut = fadeOut;
			this.stay = stay;
		}
		
		public TitleTimings setFadeIn(int fadeInSec) {
			this.fadeIn = fadeInSec * 20;
			return this;
		}
		
		public TitleTimings setFadeOut(int fadeOutSec) {
			this.fadeOut = fadeOutSec * 20;
			return this;
		}
		
		public TitleTimings setStay(int staySec) {
			this.stay = staySec * 20;
			return this;
		}
		
		public int getFadeIn() {
			return this.fadeIn;
		}
		
		public int getStay() {
			return this.stay;
		}
		
		public int getFadeOut() {
			return this.fadeOut;
		}
		
	}
	
}
