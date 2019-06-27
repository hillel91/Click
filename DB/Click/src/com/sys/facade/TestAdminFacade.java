package com.sys.facade;

import java.sql.Date;
import java.util.ArrayList;

import com.sys.beans.Company;
import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientType;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;

public class TestAdminFacade {

	public static void main(String[] args) throws CouponSystemException {

		LoginManager loginManager = LoginManager.getInstance();
		AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin",
				ClientType.Administrator);
		adminFacade.login("admin@admin.com", "admin");
		System.out.println("Success");
		Company comp2 = adminFacade.getOneCompany(206);
		comp2.setEmail("secondTestEmail@secondTest.com");
		adminFacade.updateCompany(comp2);
		Company company = new Company(101, "AvrahamT", "Avi_Vaknin@gmail.comT", "aviPasswordT");
		adminFacade.addCompany(company);

		company.setEmail("AviVaknin1@gmail.comT");
		adminFacade.updateCompany(company);
		ArrayList<Coupon> couponsList = new ArrayList<>();
		company.setCoupensList(couponsList);

		company.getCoupensList()
				.add(new Coupon(1001, company.getId(), Categories.Cars.ordinal(), "Holiday", "Low price",
						new Date(07 - 06 - 19), new Date(10 / 6 / 19), 15, 350.0,
						"http//localhost:8080/c/java/image1"));
		company.getCoupensList()
				.add(new Coupon(1002, company.getId(), Categories.Cars.ordinal(), "5 days", "Low price for rent 5 days",
						new Date(10 - 06 - 19), new Date(15 / 6 / 19), 15, 550.0,
						"http//localhost:8080/c/java/image2"));
		company.getCoupensList()
				.add(new Coupon(1003, company.getId(), Categories.Cars.ordinal(), "Vecation",
						"Low price for rent for a week", new Date(07 - 06 - 19), new Date(17 / 6 / 19), 15, 650.0,
						"http//localhost:8080/c/java/image3"));

		for (int i = 0; i < company.getCoupensList().size(); i++) {
			System.out.println("The id of coupon '" + company.getCoupensList().get(i).getTitle() + "' is "
					+ company.getCoupensList().get(i).getId());
		}

		System.out.println();
		System.out.println("The all companies are: ");
		System.out.println(adminFacade.getAllCompanies());
		System.out.println();

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
		Company company2 = adminFacade.getOneCompany(207);
		System.out.println(company2.getId());
		System.out.println(company2.getName());
		System.out.println(company2.getEmail());
		System.out.println();

		Customer customer = new Customer(101, "Avi", "Vaknin", "Avitost1@gmail.com", "password");
		adminFacade.addCustomer(customer);
		customer.setPassword("newPassword");
		adminFacade.updateCustomer(customer);
		System.out
				.println("Updated password of customer with id " + adminFacade.getOneCustomer(customer.getId()).getId()
						+ " to " + adminFacade.getOneCustomer(customer.getId()).getPassword());
		System.out.println("Updated password");

		adminFacade.deleteCustomer(customer.getId());
		System.out.println("Deleted customer with id " + customer.getId());

		ArrayList<Customer> customersList = adminFacade.getAllCustomers();
		for (int i = 0; i < customersList.size(); i++) {
			System.out.println(customersList.get(i).getId() + "    " + customersList.get(i).getFirstName() + "    "
					+ customersList.get(i).getLastName() + "    " + customersList.get(i).getEmail() + "    "
					+ customersList.get(i).getPassword());
		}

		Customer secondCustomer = adminFacade.getOneCustomer(13);
		System.out.println(secondCustomer.getFirstName());
		secondCustomer.setFirstName("Avraham");
		adminFacade.updateCustomer(secondCustomer);
		System.out.println(
				secondCustomer.getId() + ", " + secondCustomer.getFirstName() + ", " + secondCustomer.getLastName()
						+ ", " + secondCustomer.getEmail() + ", " + secondCustomer.getPassword());

	}

}
