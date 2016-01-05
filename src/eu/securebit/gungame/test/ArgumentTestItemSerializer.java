package eu.securebit.gungame.test;

import java.io.File;
import java.util.Arrays;

import lib.securebit.command.Argument;

import org.apache.commons.io.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.io.util.ItemSerializer;

public class ArgumentTestItemSerializer extends Argument<Main> {

	public ArgumentTestItemSerializer() {
		super(Main.instance());
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		try {
			
			if (args.length != 2) {
				sender.sendMessage("§7/ggtest itemserializer <save | load>");
				return true;
			}
			
			File file = new File(super.plugin.getDataFolder(), "test.bin");
			
			if (args[1].equalsIgnoreCase("save")) {
				file.createNewFile();
				FileUtils.write(file, ItemSerializer.serializeInventory(player.getInventory()));
			}
			
			if (args[1].equalsIgnoreCase("load")) {
				player.getInventory().clear();
				ItemStack[] items = ItemSerializer.deserializeInventory(FileUtils.readFileToString(file));

				player.getInventory().setContents(Arrays.copyOfRange(items, 0, 36));
				player.getInventory().setArmorContents(Arrays.copyOfRange(items, 36, 40));
			}
			
			sender.sendMessage("�7Successful.");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
		return true;
	}

	// Really? Of course.
	@Override public String getSyntax() {return null;}
	@Override public String getPermission() {return null;}
	@Override public boolean isOnlyForPlayer() {return true;}
}
