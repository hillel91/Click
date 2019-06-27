package com.sys.exceptions;

public class GetConnectionException extends CouponSystemException {

	/**
	 * @return String which display that manage require or return a connection has been failed
	 */
	
	public GetConnectionException() {
		
	}
	
	public GetConnectionException(String massage) {
		System.out.println(error(massage));
	}
	
	public String error() {
		return "Can't connect to the database. Please try later";
	}
	 
	public String error(String massage) {
		return massage;
	}
	private static final long serialVersionUID = 1L;

}
