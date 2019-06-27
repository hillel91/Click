package com.sys.facade;

import java.util.ArrayList;

import com.sys.beans.Company;
import com.sys.beans.Customer;
import com.sys.coupon.login.ClientFacade;
import com.sys.coupon.login.ClientType;
import com.sys.dao.db.CompaniesDBDAO;
import com.sys.dao.db.CouponsDBDAO;
import com.sys.dao.db.CustomersDBDAO;
import com.sys.exceptions.CouponSystemException;

public class AdminFacade extends ClientFacade {

	public AdminFacade() throws CouponSystemException {
		companiesDBDAO = new CompaniesDBDAO();
		customersDBDAO = new CustomersDBDAO();
		couponsDBDAO = new CouponsDBDAO();

		clientType = ClientType.Administrator;
	}

	public boolean checkLogin(String data, String condition) throws CouponSystemException {
		boolean loginTrue = false;
		if (data == condition) {
			loginTrue = true;
		} else {
			throw new CouponSystemException("The " + data + " is wrong. Please try again");
		}
		return loginTrue;
	}

	public boolean login(String email, String password) throws CouponSystemException {
		boolean loginTrue = false;
		loginTrue = checkLogin(email, "admin@admin.com");
		loginTrue = checkLogin(password, "admin");
		return loginTrue;
	}

	public void addCompany(Company company) throws CouponSystemException {
		if (companiesDBDAO.isCompanyExists("EMAIL", company.getEmail())) {
			throw new CouponSystemException("This email exists allready");
		} else if (companiesDBDAO.isCompanyExists("PASSWORD", company.getPassword())) {
			throw new CouponSystemException("This password exists allready");
		} else {
			companiesDBDAO.addCompany(company);
			System.out.println("Adding company " + company.getName() + " succeed");
		}
		/*
		 * 
		 * 
		 * if (companiesDBDAO.isCompanyExists(company.getEmail(),
		 * company.getPassword())) { throw new
		 * CouponSystemException("This company exists allready"); } else {
		 * companiesDBDAO.addCompany(company); System.out.println("Adding company " +
		 * company.getName() + " succeed"); }
		 */
	}

	public void updateCompany(Company company) throws CouponSystemException {
		if (companiesDBDAO.isCompanyExists(company.getId())) {
			companiesDBDAO.updateCompany(company);
			System.out.println("Updating company " + company.getName() + " succeed");
		} else {
			throw new CouponSystemException("This company isn't exists");
		}
	}

	public void deleteCompany(int companyId) throws CouponSystemException {
		if (companiesDBDAO.isCompanyExists(companyId)) {
			if (companiesDBDAO.getOneCompany(companyId).getCoupensList() != null) {
				companiesDBDAO.getOneCompany(companyId).getCoupensList().clear();
			}
			companiesDBDAO.deleteCompany(companyId);
			System.out.println("Company number " + companyId + " isn't exists allready");
		} else {
			throw new CouponSystemException("Company with id " + companyId + " isn't exists. check again the id");
		}
	}

	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		return companiesDBDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyId) throws CouponSystemException {
		if (companiesDBDAO.isCompanyExists(companyId)) {
			return companiesDBDAO.getOneCompany(companyId);
		} else {
			throw new CouponSystemException("Company with id " + companyId + " isn't exists. check again the id");
		}
	}

	public void addCustomer(Customer customer) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customer.getId())) {
			throw new CouponSystemException(
					"Customer with id " + customer.getId() + " exists allready. check again the id");
		} else {
			customersDBDAO.addCustomer(customer);
			System.out.println(
					"Adding customer, name " + customer.getFirstName() + " and id " + customer.getId() + " succeed");
		}
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customer.getId())) {
			customersDBDAO.updateCustomer(customer);
			System.out.println("Updating customer " + customer.getId() + " succeed");
		} else {
			throw new CouponSystemException(
					"Customer with id " + customer.getId() + " isn't exists. check again the id");
		}
	}

	public void deleteCustomer(int customerId) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			customersDBDAO.deleteCustomer(customerId);
		} else {
			throw new CouponSystemException("Customer with id " + customerId + " isn't exists. check again the id");
		}
	}

	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		return customersDBDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		if (customersDBDAO.isCustomerExists(customerId)) {
			return customersDBDAO.getOneCustomer(customerId);
		} else {
			throw new CouponSystemException("Customer with id " + customerId + " isn't exists. check again the id");
		}
	}

}
