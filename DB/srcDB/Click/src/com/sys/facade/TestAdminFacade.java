package Click.src.com.sys.facade;

import java.util.ArrayList;

import Click.src.com.sys.beans.Company;
import Click.src.com.sys.exceptions.CouponSystemException;

public class TestAdminFacade {

	public static void main(String[] args) throws CouponSystemException {

		AdminFacade adminFacade = new AdminFacade();
		adminFacade.login("admin@admin.com", "admin");
		System.out.println("Success");

		Company company = new Company(101, "Avi", "AviVaknin@gmail.com", "secreetPassword");
		adminFacade.addCompany(company);

		company.setEmail("AviVaknin1@gmail.com");
		adminFacade.updateCompany(company);

		System.out.println("From update to delete");

		adminFacade.deleteCompany(company.getId());
		

		ArrayList<Company> companyList = adminFacade.getAllCompanies();
		for (int i = 0; i < companyList.size(); i++) {
			System.out.print(companyList.get(i).getId() + "   ");
			System.out.print(companyList.get(i).getName() + "   ");
			System.out.print(companyList.get(i).getEmail() + "   ");
			System.out.print(companyList.get(i).getPassword() + "   ");
			System.out.println();
		}
		System.out.println();
		Company company2 = adminFacade.getOneCompany(209);
		System.out.println(company2.getId());
		System.out.println(company2.getName());

	}

}
