package Click.src.com.sys.exceptions;

public class CouponSystemException extends Exception {

	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponSystemException() {
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String massage) {
		super(massage);
		System.out.println(massage);
	}

	public CouponSystemException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
	public String printMassage(String massage) {
		return massage;
	}

}