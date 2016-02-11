package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.ioutil.ItemSerializer;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileLevels extends CraftFileGunGameConfig implements FileLevels {
	
	public CraftFileLevels(File file, CraftErrorHandler handler) {
		super(file, handler, FileLevels.ERROR_LOAD, FileLevels.ERROR_FOLDER, FileLevels.ERROR_CREATE, FileLevels.ERROR_MALFORMED, "levels");
		
		this.getDefaults().add(new ConfigDefault("levels", Arrays.asList(), List.class));
	}
	
	@Override
	public void validate() {
		if (this.getLevels().size()	 < 1) {
			super.handler.throwError(this.createError(FileLevels.ERROR_LEVELCOUNT));
		}
	}
	
	@Override
	public List<ItemStack[]> getLevels() {
		this.checkReady();
		
		List<String> datas = super.config.getStringList("levels");
		List<ItemStack[]> levels = new ArrayList<>();
		
		for (String data : datas) {
			levels.add(ItemSerializer.deserializeInventory(data));
		}
		
		return levels;
	}
	
	@Override
	public void setLevels(List<ItemStack[]> levels) {
		this.checkReady();
		
		List<String> datas = new ArrayList<>();
		
		for (ItemStack[] level : levels) {
			datas.add(ItemSerializer.serializeInventory(level));
		}
		
		super.config.set("levels", datas);
		super.save();
	}

}
