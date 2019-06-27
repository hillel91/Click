package com.sys.facade;

import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientType;
import com.sys.coupon.login.LoginManager;
import com.sys.dao.db.CouponsDBDAO;
import com.sys.exceptions.CouponSystemException;

public class TestCustomersFacade {

	public static void main(String[] args) throws CouponSystemException {
		LoginManager loginManager = LoginManager.getInstance();
		CustomerFacade customerFacade = (CustomerFacade) loginManager.login("emailCust4@com", "passCust4@com",
				ClientType.Customer);

		Customer customer = customerFacade.getCustomerDetails(12);
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		customerFacade.login(customer.getEmail(), customer.getPassword(), ClientType.Customer);
		System.out.println();

		customer.setEmail("mailCust4@com");
		customer.setPassword("pass_Cust4@com");
		customerFacade.updateCustomer(customer);
		System.out.println();

		Coupon coupon = new Coupon(218, 207, 6, "Vacation", "Vacation Description", java.sql.Date.valueOf("2019-06-01"),
				java.sql.Date.valueOf("2019-06-30"), 10, 40.0, "http");
		couponsDBDAO.addCoupon(coupon);
		customerFacade.purchaseCoupon(coupon, customer.getId());

		Coupon coupon2 = new Coupon(219, 207, 5, "Food  Title", "Food Description", java.sql.Date.valueOf("2019-06-01"),
				java.sql.Date.valueOf("2019-06-30"), 15, 350.0, "http");
		couponsDBDAO.addCoupon(coupon2);
		customerFacade.purchaseCoupon(coupon2, customer.getId());
		Coupon coupon3 = new Coupon(219, 207, 3, "Design T", "Design  D", java.sql.Date.valueOf("2019-06-01"),
				java.sql.Date.valueOf("2019-06-30"), 20, 3000.0, "http");
		couponsDBDAO.addCoupon(coupon3);
		customerFacade.purchaseCoupon(coupon3, customer.getId());
		Coupon coupon4 = new Coupon(219, 207, 2, "Cars T", "Cars  D", java.sql.Date.valueOf("2019-06-01"),
				java.sql.Date.valueOf("2019-06-30"), 10, 9000.0, "http");
		couponsDBDAO.addCoupon(coupon4);
		customerFacade.purchaseCoupon(coupon4, customer.getId());
		Coupon coupon5 = new Coupon(219, 207, 0, "Computers T", "Computers D", java.sql.Date.valueOf("2019-06-01"),
				java.sql.Date.valueOf("2019-06-30"), 10, 2500.0, "http");
		couponsDBDAO.addCoupon(coupon5);
		customerFacade.purchaseCoupon(coupon5, customer.getId());

		System.out.println("The list coupons of " + customer.getFirstName() + " with id " + customer.getId());
		System.out.println(customerFacade.getCustomerCoupon(customer.getId()));

		System.out.println("The list coupons of " + customer.getFirstName() + " with id " + customer.getId()
				+ " of specify category");
		System.out.println(customerFacade.getCustomerCoupon(customer.getId(), Categories.Cars));

		System.out.println("The list coupons of " + customer.getFirstName() + " with id " + customer.getId()
				+ " under or equal X NIS");
		System.out.println(customerFacade.getCustomerCoupon(customer.getId(), 2000.0));

		System.out.println("Details of customer " + customer.getFirstName() + " with id " + customer.getId());
		System.out.println(customerFacade.getCustomerDetails(customer.getId()));
	}

}
