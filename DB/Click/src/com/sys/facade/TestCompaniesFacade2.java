package com.sys.facade;

import java.sql.Date;
import java.util.ArrayList;

import com.sys.beans.Company;
import com.sys.beans.Coupon;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientType;
import com.sys.exceptions.CouponSystemException;

public class TestCompaniesFacade2 {

	public static void main(String[] args) throws CouponSystemException {

		CompanyFacad companyFacad = new CompanyFacad(205);

		Company company205 = companyFacad.getCompanyDetails(205);

		// getCompanyDetails(company204.getId()).setEmail("passwordOfCompany204");
		ArrayList<Coupon> couponsList = new ArrayList<>();
		couponsList.add(new Coupon(1001, company205.getId(), Categories.Cars.ordinal(), "T1", "D1",
				new Date(1 / 6 / 19), new Date(2 / 6 / 19), 10, 9.0, "H1"));
		couponsList.add(new Coupon(1002, company205.getId(), Categories.Design.ordinal(), "T1", "D1",
				new Date(1 / 6 / 19), new Date(2 / 6 / 19), 10, 9.0, "H1"));
		couponsList.add(new Coupon(1003, company205.getId(), Categories.Restaurant.ordinal(), "T1", "D1",
				new Date(1 / 6 / 19), new Date(2 / 6 / 19), 10, 9.0, "H1"));
		couponsList.add(new Coupon(1004, company205.getId(), Categories.Electricity.ordinal(), "T1", "D1",
				new Date(1 / 6 / 19), new Date(2 / 6 / 19), 10, 9.0, "H1"));
		couponsList.get(0).setCategory(Categories.Computers);
		couponsList.get(0).setPrice(11.0);
		couponsList.get(1).setCategory(Categories.Food);
		couponsList.get(1).setPrice(1.0);
		couponsList.get(2).setCategory(Categories.Vacation);
		couponsList.get(2).setPrice(15.0);
		couponsList.get(3).setCategory(Categories.Cars);
		couponsList.get(3).setPrice(7.0);
		company205.setCoupensList(couponsList);
		System.out.println(couponsList);
		company205.setPassword("passComp205TCF");
		companyFacad.updateCompany(company205);
		companyFacad.login(company205.getEmail(), company205.getPassword(), ClientType.Company);
		/*
		 * Coupon coupon1 = new Coupon(1001,
		 * company205.getId(),Categories.Cars.ordinal(),"SFGHHoliday",
		 * "DFFLow price",new Date(17-06-19), new Date(19/6/19), 15, 350.0,
		 * "http//localhost:8080/c/image13"); companyFacad.addCoupon(coupon1);
		 */
		ArrayList<Coupon> couponsList2 = new ArrayList<>();
		System.out.println("Get company's coupons list according the company id");
		couponsList = companyFacad.getCompanyCoupons(company205.getId());
		System.out.println(couponsList);

		System.out.println("Get company's coupons list of one category");
		couponsList = companyFacad.getCompanyCoupons(company205.getId(),
				company205.getCoupensList().get(0).getCategory());
		System.out.println(couponsList);

		System.out.println("Get company's coupons list with price under X NIS");
		couponsList = companyFacad.getCompanyCoupons(company205.getId(), 10.0);
		System.out.println(couponsList);

	}

}
