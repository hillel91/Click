package com.sys.coupon.login;

import com.sys.exceptions.CouponSystemException;
import com.sys.facade.AdminFacade;
import com.sys.facade.CompanyFacad;
import com.sys.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance;

	private LoginManager() {

	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {

		ClientFacade thisClientFacade;

		switch (clientType) {
		case Administrator:
			thisClientFacade = new AdminFacade();
			break;

		case Company:
			thisClientFacade = new CompanyFacad();
			break;

		case Customer:
			thisClientFacade = new CustomerFacade();
			break;

		default:
			return null;

		}

		return thisClientFacade;
	}

}
