package Click.src.com.sys.dao.db;

import java.util.ArrayList;

import Click.src.com.sys.beans.Company;
import Click.src.com.sys.exceptions.CouponSystemException;

public class TestCompanies {
	
	public static String checkExist(CompaniesDBDAO companiesDBDAO,Company company) throws CouponSystemException {
		if(companiesDBDAO.isCompanyExists(company.getEmail(), company.getPassword())==true) {
			return("Company with id number " + company.getId()+" exists");	
		}else {
			return("Company with id number " + company.getId()+" isn't exist");
		}
	}
	
	public static void deleteCompanies(CompaniesDBDAO companiesDBDAO, int a, int b) throws CouponSystemException {
		for (int i=a; i<=b; i++) {
			companiesDBDAO.deleteCompany(i);
		}
	}

	public static void main(String[] args) {
		Company company=new Company();
		company.setId(12);
		company.setName("Tenth company");
		company.setEmail("TenthCompanyEmail@com");
		company.setPassword("Password");
		try {
			CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
			System.out.println("CompanyDBDAO created");
			companiesDBDAO.addCompany(company);
			System.out.println("Company with id number " + company.getId()+" added");
			
			System.out.println(checkExist(companiesDBDAO, company));
			
			company.setEmail("newEmailAddress@com");
			companiesDBDAO.updateCompany(company);
			System.out.println(company.getEmail());
			
			companiesDBDAO.deleteCompany(company.getId());
			System.out.println(checkExist(companiesDBDAO, company));
			ArrayList<Company> compList = new ArrayList<>();
			compList = companiesDBDAO.getAllCompanies();
			for(int i=0;i<compList.size(); i++) {
				System.out.print(compList.get(i).getId() + ", ");
				System.out.print(compList.get(i).getName() + ", ");
				System.out.print(compList.get(i).getEmail() + ", ");
				System.out.println(compList.get(i).getPassword());
			}
			
			Company c2 = companiesDBDAO.getOneCompany(209);//   setEmail("veryNewEmail@com");
			c2.setEmail("PPPPNewEmail@com");
			companiesDBDAO.updateCompany(c2);
			System.out.println(companiesDBDAO.getOneCompany(209).getEmail());
		} catch (CouponSystemException e) {
			System.out.println("Failed to create company DAO DB");
		}
		
		
	}

}
