package Click.src.com.sys.exceptions;

import java.sql.SQLException;
/**
 * @see SQLExcepetion
 */
public class TableCreateException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TableCreateException() {
		
	}
	
	public TableCreateException(String massage) {
		System.out.println(massage);
	}

	/**
	 * 
	 * @param tableName, the name of the table that was not created
	 * @return String which display that the table creating has been failed
	 */
	public String error(String tableName) {
		return "Creating table, named " + tableName + "is failed. Please try later";
	}
	
}
