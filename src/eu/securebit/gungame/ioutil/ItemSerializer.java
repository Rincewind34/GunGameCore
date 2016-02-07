package eu.securebit.gungame.ioutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class ItemSerializer {

	public static String serialize(ItemStack item) {
		if (item == null) {
			return null;
		}
		
		String base64 = null;
		
		try {
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			BukkitObjectOutputStream out = new BukkitObjectOutputStream(bytesOut);
			out.writeObject(item);
			out.flush();
			out.close();
			base64 = Base64.getEncoder().encodeToString(bytesOut.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return base64;
	}
	
	public static ItemStack deserialize(String data) {
		if (data == null || data.isEmpty() || data.equals("null")) {
			return null;
		}
		
		ItemStack item = null;
		
		try {
			ByteArrayInputStream bytesIn = new ByteArrayInputStream(Base64.getDecoder().decode(data));
			BukkitObjectInputStream in = new BukkitObjectInputStream(bytesIn);
			item = (ItemStack) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return item;
	}
	
	public static String serializeInventory(ItemStack[] content) {
		StringBuilder resultBuilder = new StringBuilder();
		for (int i = 0; i < content.length; i++) {
			resultBuilder.append(ItemSerializer.serialize(content[i]) + "|");
		}
		
		resultBuilder.setLength(resultBuilder.length() - 1);
		return resultBuilder.toString();
	}
	
	public static ItemStack[] deserializeInventory(String data) {
		if (data == null) {
			throw new NullPointerException("The data cannot be null!");
		}
		
		String[] items = data.split("[|]");
		ItemStack[] content = new ItemStack[40];
		
		for (int i = 0; i < 36; i++) {
			content[i] = ItemSerializer.deserialize(items[i]);
		}
		
		for (int i = 36; i < 40; i++) {
			content[i] = ItemSerializer.deserialize(items[i]);
		}
		
		return content;
	}
}
