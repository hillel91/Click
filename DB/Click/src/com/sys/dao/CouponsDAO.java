package com.sys.dao;

import java.util.ArrayList;

import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.exceptions.CouponSystemException;

public interface CouponsDAO {
	
	public boolean isCouponExists(int couponId) throws CouponSystemException;
	
	public int addCoupon(Coupon coupon) throws CouponSystemException;
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	
	public void deleteCoupon(int CouponID) throws CouponSystemException;
	
	public ArrayList<Coupon> getAllCoupon() throws CouponSystemException;
	
	public Coupon getOneCoupon(int couponID) throws CouponSystemException;
	
	public int addCouponPurchase(int customerID, int couponId) throws CouponSystemException;
	
	public void deleteCouponPurchase(int customerID, int couponId) throws CouponSystemException;
			
}
