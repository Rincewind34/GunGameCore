package eu.securebit.gungame.io.util;

import eu.securebit.gungame.Main;

public abstract class AbstractFileConfig extends AbstractFile {
	
	public AbstractFileConfig(String path, String name, String type) {
		super(path, name);
		
		if (!super.file.exists()) {
			if (Main.instance().getFileRegistry().contains(super.file.getAbsolutePath())) {
				Main.instance().getFileRegistry().remove(super.file.getAbsolutePath());
			}
			
			Main.instance().getFileRegistry().add(super.file.getAbsolutePath(), type);
		} else {
			if (!Main.instance().getFileRegistry().contains(super.file.getAbsolutePath())) {
				super.file.delete();
				Main.instance().getFileRegistry().add(super.file.getAbsolutePath(), type);
			} else {
				if (!Main.instance().getFileRegistry().get(super.file.getAbsolutePath()).equals(type)) {
					Main.instance().getFileRegistry().remove(super.file.getAbsolutePath());
					super.file.delete();
					Main.instance().getFileRegistry().add(super.file.getAbsolutePath(), type);
				}
			}
		}
	}
	
}
