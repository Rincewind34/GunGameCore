package lib.securebit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class InfoLayout {

	public String colorPrimary;
	public String colorSecondary;
	public String colorImportant;
	public String colorPositiv;
	public String colorNegative;
	public String prefix;
	
	protected List<String> transaction;
	
	public InfoLayout() {
		this("");
	}
	
	public InfoLayout(String prefix) {
		this.colorPrimary = "§8";
		this.colorSecondary = "§r";
		this.colorImportant = "§r";
		this.colorPositiv = "§r";
		this.colorNegative = "§4";
		this.createPrefix(prefix);
		
		this.transaction = new ArrayList<String>();
	}
	
	public void createPrefix(String prefix) {
		this.prefix = this.colorPrimary + "[" + this.colorSecondary + prefix + this.colorPrimary + "] " + this.colorSecondary;
	}
	
	public void message(CommandSender sender, String text) {
		sender.sendMessage(this.format("\\pre" + text));
	}
	
	public void broadcast(String text) {
		Bukkit.broadcastMessage(this.format("\\pre" + text));
	}

	public synchronized void begin() {
		this.transaction.clear();
	}

	public synchronized void commit(CommandSender cs) {
		while (this.transaction.size() > 0) {
			cs.sendMessage(this.format(this.transaction.remove(0))); // FIFO (first in, first out)
		}
	}

	public void rollback() {
		this.transaction.clear();
	}
	
	public void category(String name) {
		this.barrier();
		this.line("*" + name + "*");
	}
	
	public void barrier() {
		this.barrier(45);
	}
	
	public void barrier(int count) {
		String barrier = "";
		
		for (int i = 0; i < count; i++) {
			barrier = barrier + "=";
		}
		
		this.line(this.colorPrimary + barrier);
	}
	
	public void line(String text) {
		this.transaction.add("\\pre" + text);
	}
	
	public void suggestion(String commandLine, String description) {
		if (description != null) {
			description = this.format(description);
		}
		
		this.line("*/" + commandLine + (description == null || description.isEmpty() || description.trim().isEmpty() ? "" :
			this.colorPrimary + "* $-$- " + description));
	}


	public void prefix(String newPrefix) {
		this.prefix = newPrefix;
	}

	public boolean hasPrefix() {
		return this.prefix != null && !this.prefix.isEmpty();
	}

	public String prefix() {
		return this.prefix;
	}
	
	public String format(String text) {
		text = this.colorSecondary + text;
		
		text = text.replace("$+", "$1");
		text = text.replace("$-", "$2");
		text = text.replace("$*", "$3");
		
		return this.format(text, 0);
	}
	
	private String format(String text, int status) {
		if (text.contains("+")) {
			int index = text.indexOf("+");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorPositiv), 1);
			} else if (status == 1) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (text.contains("-")) {
			int index = text.indexOf("-");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorNegative), 2);
			} else if (status == 2) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (text.contains("*")) {
			int index = text.indexOf("*");
			
			if (status == 0) {
				return this.format(this.replace(text, index, this.colorImportant), 3);
			} else if (status == 3) {
				return this.format(this.replace(text, index, this.colorSecondary), 0);
			}
		}
		
		if (status != 0) {
			String s = "";
			
			if (status == 1) {
				s = "+";
			} else if (status == 2) {
				s = "-";
			} else if (status == 3) {
				s = "*";
			} else {
				s = null;
			}
			
			if (s != null) {
				throw new FormatException("The statment '" + s + "' is uncomplet!");
			} else {
				throw new FormatException("An expected error!");
			}
		}
		
		text = text.replace("$1", "+");
		text = text.replace("$2", "-");
		text = text.replace("$3", "*");
		text = text.replace("\\pre", this.prefix);
		return text;
	}
	
	private String replace(String string, int index, String replace) {
		return string.substring(0, index) + replace + string.substring(index + 1);
	}
	
	public static String replaceKeys(String text) {
		text = text.replace("+", "$1");
		text = text.replace("-", "$2");
		text = text.replace("*", "$3");
		
		return text;
	}
	
	public static String undoKeys(String text) {
		text = text.replace("$1", "+");
		text = text.replace("$2", "-");
		text = text.replace("$3", "*");
		
		return text;
	}
	
	@SuppressWarnings("serial")
	public static class FormatException extends RuntimeException {
		
		public FormatException(String string) {
			super(string);
		}
		
	}
	
}
