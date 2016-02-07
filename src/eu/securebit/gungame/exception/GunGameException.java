package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameException extends RuntimeException {
	
	private Exception ex;
	
	public GunGameException(String msg) {
		this(msg, null);
	}
	
	public GunGameException(String msg, Exception ex) {
		super(msg);
		
		this.ex = ex;
	}
	
	@Override
	public void printStackTrace() {
		if (ex != null) {
			this.ex.printStackTrace();
		} else {
			super.printStackTrace();
		}
	}
	
}
