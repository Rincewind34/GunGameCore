package lib.securebit.board;

import java.util.List;

public interface BitInfoBoard extends BitBoard {
	
	public abstract void addLine(String text);
	
	public abstract void addLine(String text, int index);
	
	public abstract void removeLine();
	
	public abstract void removeLine(int index);
	
	public abstract boolean containsLine(String text);
	
	public abstract boolean isFull();
	
	public abstract int getSize();
	
	public abstract List<String> getLines();
	
}
