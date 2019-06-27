package com.sys.exceptions;

public class LoadDriver extends CouponSystemException {

	/**
	 * @author hbpe9
	 */
	private static final long serialVersionUID = 1L;

	public LoadDriver() {
		
	}
	
	public LoadDriver(String massage) {
		System.out.println(massage);
	}
	
	public String error() {
		return "Loading driver failed. Please try later";
	}
}
