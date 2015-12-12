package lib.securebit.displays;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lib.securebit.ReflectionUtil;

public final class DisplayReflection {
	
	public static final Class<?> CLASS_TITLEACTION = ReflectionUtil.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
	public static final Class<?> CLASS_CHATBASE = ReflectionUtil.getNMSClass("IChatBaseComponent");
	public static final Class<?> CLASS_CHATSERIALIZER = ReflectionUtil.getNMSClass("IChatBaseComponent$ChatSerializer");
	public static final Class<?> CLASS_CRAFTPLAYER = ReflectionUtil.getCraftBukkitClass("entity.CraftPlayer");
	public static final Class<?> CLASS_ENTITYPLAYER = ReflectionUtil.getNMSClass("EntityPlayer");
	public static final Class<?> CLASS_PLAYERCONNECTION = ReflectionUtil.getNMSClass("PlayerConnection");
	public static final Class<?> CLASS_PACKET = ReflectionUtil.getNMSClass("Packet");
	public static final Class<?> CLASS_PACKET_CHAT = ReflectionUtil.getNMSClass("PacketPlayOutChat");
	public static final Class<?> CLASS_PACKET_TABLIST = ReflectionUtil.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
	public static final Class<?> CLASS_PACKET_TITLE = ReflectionUtil.getNMSClass("PacketPlayOutTitle");
	
	public static final Constructor<?> CONSTRUCTOR_PACKET_TITLE_TITLE = ReflectionUtil.getConstructor(DisplayReflection.CLASS_PACKET_TITLE,
			new Class<?>[] { DisplayReflection.CLASS_TITLEACTION, DisplayReflection.CLASS_CHATBASE });
	public static final Constructor<?> CONSTRUCTOR_PACKET_TITLE_TIMES = ReflectionUtil.getConstructor(DisplayReflection.CLASS_PACKET_TITLE,
			new Class<?>[] { int.class, int.class, int.class });
	public static final Constructor<?> CONSTRUCTOR_PACKET_CHAT = ReflectionUtil.getConstructor(DisplayReflection.CLASS_PACKET_CHAT,
			new Class<?>[] { DisplayReflection.CLASS_CHATBASE, byte.class });
	
	public static final Field FIELD_CONNECTION = ReflectionUtil.getDeclaredField(DisplayReflection.CLASS_ENTITYPLAYER, "playerConnection");
	public static final Field FIELD_TABLIST_HEADER = ReflectionUtil.getDeclaredField(DisplayReflection.CLASS_PACKET_TABLIST, "a");
	public static final Field FIELD_TABLIST_FOOTER = ReflectionUtil.getDeclaredField(DisplayReflection.CLASS_PACKET_TABLIST, "b");
	
	public static final Method METHOD_A = ReflectionUtil.getMethod(DisplayReflection.CLASS_CHATSERIALIZER, "a", new Class<?>[] { String.class });
	public static final Method METHOD_GETHANDLE = ReflectionUtil.getMethod(DisplayReflection.CLASS_CRAFTPLAYER, "getHandle", ReflectionUtil.emtyClassArray());
	public static final Method METHOD_SENDPACKET = ReflectionUtil.getMethod(DisplayReflection.CLASS_PLAYERCONNECTION, "sendPacket",
			new Class<?>[] { DisplayReflection.CLASS_PACKET });
}
