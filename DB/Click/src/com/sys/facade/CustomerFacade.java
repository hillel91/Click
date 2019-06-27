package com.sys.facade;

import java.util.ArrayList;
import java.util.Date;

import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientFacade;
import com.sys.coupon.login.ClientType;
import com.sys.dao.db.CouponsDBDAO;
import com.sys.dao.db.CustomersDBDAO;
import com.sys.exceptions.CouponSystemException;

public class CustomerFacade extends ClientFacade {

	int customerId;

	public CustomerFacade() throws CouponSystemException {
		customersDBDAO = new CustomersDBDAO();
		couponsDBDAO = new CouponsDBDAO();
		clientType = ClientType.Customer;
	}

	public CustomerFacade(int customerId) throws CouponSystemException {
		this.customersDBDAO = new CustomersDBDAO();
		couponsDBDAO = new CouponsDBDAO();
		clientType = ClientType.Customer;
		this.customerId = customerId;
	}

	@Override
	public boolean login(String email, String password, ClientType clientType) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(email, password)) {
			System.out.println("Welcome");
			return true;
		} else {
			throw new CouponSystemException("This customer isn't exists allready. Check the email and the password");
		}
	}

	public void purchaseCoupon(Coupon coupon, int customerId) throws CouponSystemException {
		Date today = new Date();
		if (couponsDBDAO.isPurchaseExists(coupon, customerId)) {
			throw new CouponSystemException("Can't purchase the same coupon twice");
		} else if (couponsDBDAO.getOneCoupon(coupon.getId()).getAmount() < 1) {
			throw new CouponSystemException("This coupon is out of stock");
		} else if ((couponsDBDAO.getOneCoupon(coupon.getId()).getEndDate()).before(today)) {
			throw new CouponSystemException("This coupon is expired");
		} else {
			couponsDBDAO.addCouponPurchase(customerId, coupon.getId());
			coupon.setAmount(coupon.getAmount() - 1);
			couponsDBDAO.updateCoupon(coupon);
		}
	}

	public ArrayList<Coupon> getCustomerCoupon(int customerId) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			return customersDBDAO.getCustomerCoupons(customerId);
		} else {
			throw new CouponSystemException("There is no customer with id " + customerId);
		}
	}

	public ArrayList<Coupon> getCustomerCoupon(int customerId, Categories category) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			return customersDBDAO.getCustomerCoupons(customerId, category);
		} else {
			throw new CouponSystemException("There is no customer with id " + customerId);
		}
	}

	public ArrayList<Coupon> getCustomerCoupon(int customerId, double maxPrice) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			return customersDBDAO.getCustomerCoupons(customerId, maxPrice);
		} else {
			throw new CouponSystemException("There is no customer with id " + customerId);
		}
	}

	public Customer getCustomerDetails(int customerId) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			return customersDBDAO.getOneCustomer(customerId);
		} else {
			throw new CouponSystemException("There is no customer with id " + customerId);
		}
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customer.getId())) {
			customersDBDAO.updateCustomer(customer);
			System.out.println("Updating customer " + customer.getId() + " succeed");
		} else {
			throw new CouponSystemException("This company isn't exists");
		}
	}

}
