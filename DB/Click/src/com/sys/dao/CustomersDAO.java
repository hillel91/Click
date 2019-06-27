package com.sys.dao;

import java.util.ArrayList;

import com.sys.beans.Customer;
import com.sys.exceptions.CouponSystemException;

public interface CustomersDAO {

	public boolean isCustomerExists(String email, String password) throws CouponSystemException;
	
	public int addCustomer(Customer customer) throws CouponSystemException;
	
	public void updateCustomer(Customer customer) throws CouponSystemException;
	
	public void deleteCustomer(int customerID) throws CouponSystemException;
	
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException;
	
	public Customer getOneCustomer(int customerID) throws CouponSystemException;
	
}
