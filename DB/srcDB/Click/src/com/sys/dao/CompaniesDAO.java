package Click.src.com.sys.dao;

import java.util.ArrayList;

import Click.src.com.sys.beans.Company;
import Click.src.com.sys.exceptions.CouponSystemException;

public interface CompaniesDAO {
	
	public boolean isCompanyExists(String email, String Password) throws CouponSystemException;
	
	public int addCompany(Company company) throws CouponSystemException;
	
	public void updateCompany(Company company) throws CouponSystemException;
	
	public void deleteCompany(int companyID) throws CouponSystemException;
	
	public ArrayList<Company> getAllCompanies() throws CouponSystemException;
	
	public Company getOneCompany(int companyID) throws CouponSystemException;

}
