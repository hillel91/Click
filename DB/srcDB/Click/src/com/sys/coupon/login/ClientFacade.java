package Click.src.com.sys.coupon.login;

import Click.src.com.sys.dao.CompaniesDAO;
import Click.src.com.sys.dao.CouponsDAO;
import Click.src.com.sys.dao.CustomersDAO;
import Click.src.com.sys.dao.db.CompaniesDBDAO;
import Click.src.com.sys.dao.db.CouponsDBDAO;
import Click.src.com.sys.dao.db.CustomersDBDAO;
import Click.src.com.sys.exceptions.CouponSystemException;

public abstract class ClientFacade {

	protected CompaniesDBDAO companiesDBDAO;
	protected CustomersDBDAO customersDBDAO;
	protected CouponsDBDAO couponsDBDAO;
	
	protected ClientType clientType;
	
	public boolean login(String email, String password) throws CouponSystemException {
		
		
		
		return false;
		
	}

	
}
