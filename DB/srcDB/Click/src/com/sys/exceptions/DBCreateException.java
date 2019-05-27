package Click.src.com.sys.exceptions;

import java.sql.SQLException;

/**
 * 
 */
public class DBCreateException extends CouponSystemException {
	
	public DBCreateException() {
		
	}
	
	public DBCreateException(String massage) {
		System.out.println(massage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String error() {
		return "Creating database is failed. Please try later";
	}

}
