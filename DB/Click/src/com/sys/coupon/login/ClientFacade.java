package com.sys.coupon.login;

import com.sys.dao.db.CompaniesDBDAO;
import com.sys.dao.db.CouponsDBDAO;
import com.sys.dao.db.CustomersDBDAO;
import com.sys.exceptions.CouponSystemException;

public abstract class ClientFacade {

	protected CompaniesDBDAO companiesDBDAO;
	protected CustomersDBDAO customersDBDAO;
	protected CouponsDBDAO couponsDBDAO;

	protected ClientType clientType;

	public boolean login(String email, String password, ClientType clientType) throws CouponSystemException {
		/*
		 * switch (clientType) { case Administrator: AdminFacade adminFacade = new
		 * AdminFacade(); return adminFacade.login(email, password,
		 * ClientType.Administrator);
		 * 
		 * case Company: CompanyFacad companyFacad = new CompanyFacad(); return
		 * companyFacad.login(email, password, ClientType.Company);
		 * 
		 * case Customer: CustomerFacade customerFacade = new CustomerFacade(); return
		 * customerFacade.login(email, password, ClientType.Customer);
		 * 
		 * default: return false; }
		 */
		return false;
	}

	public ClientType checkLogin(String email, String password, ClientType clientType) throws CouponSystemException {
		/*
		 * ClientType CheckClientType = null; switch (clientType) { case Administrator:
		 * case Company: case Customer: if (login(email, password, clientType)) {
		 * CheckClientType = clientType; } break; } return CheckClientType;
		 */
		return clientType;
	}

}
