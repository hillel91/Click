package com.sys.facade;

import java.sql.Date;
import java.util.ArrayList;

import com.sys.beans.Company;
import com.sys.beans.Coupon;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientType;
import com.sys.coupon.login.LoginManager;
import com.sys.exceptions.CouponSystemException;

public class TestCompaniesFacade {

	public static void main(String[] args) throws CouponSystemException {

		LoginManager loginManager = LoginManager.getInstance();
		CompanyFacad companyFacad = (CompanyFacad) loginManager.login("NinthCompanyEmail@com", "passCompany204",
				ClientType.Company);

		Company company207 = companyFacad.getCompanyDetails(207);
		Company company206 = companyFacad.getCompanyDetails(206);

		companyFacad.login(company207.getEmail(), company207.getPassword(), ClientType.Company);

		companyFacad.login("207CompanyEmail@com", "passCompany207", ClientType.Company);

		Coupon coupon = new Coupon(311, company207.getId(), (Categories.Design.ordinal()) + 1, "First title",
				"First Description", new Date(17 - 04 - 19), new Date(19 - 05 - 19), 150, 15000.0, "http");

		Coupon couponCar = new Coupon(1001, company206.getId(), (Categories.Cars.ordinal()) + 1, "Cars", "Rent a car",
				new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 15000.0, "http");
		Coupon couponDesign = new Coupon(1001, company206.getId(), (Categories.Design.ordinal()) + 1, "Design",
				"Design Coupon", new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 15000.0, "http");
		Coupon couponElectricity = new Coupon(1001, company206.getId(), (Categories.Electricity.ordinal()) + 1,
				"Electricity", "Electricity Coupon", new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 15000.0,
				"http");
		Coupon couponFood = new Coupon(1001, company206.getId(), (Categories.Food.ordinal()) + 1, "Food", "Food coupon",
				new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 15000.0, "http");
		Coupon couponRestaurant = new Coupon(1001, company206.getId(), (Categories.Restaurant.ordinal()) + 1,
				"Restaurant", "Restaurant coupon", new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 150000.0,
				"http");
		Coupon couponVacation = new Coupon(1001, company206.getId(), (Categories.Vacation.ordinal()) + 1, "Vacation",
				"Vacation coupon", new Date(17 - 06 - 19), new Date(19 / 6 / 19), 150, 150000.0, "http");

		companyFacad.addCoupon(coupon);

		companyFacad.addCoupon(couponCar);
		companyFacad.addCoupon(couponDesign);
		companyFacad.addCoupon(couponElectricity);
		companyFacad.addCoupon(couponFood);
		companyFacad.addCoupon(couponRestaurant);
		companyFacad.addCoupon(couponVacation);

		coupon.setPrice(200.0);
		coupon.setAmount(7);
		coupon.setTitle("ImportantTitle");
		coupon.setDescription("ImportantDescription");
		companyFacad.updateCoupon(coupon);

		ArrayList<Coupon> couponsList = new ArrayList<>();
		System.out.println("Get company's coupons list according the company id");
		couponsList = companyFacad.getCompanyCoupons(company207.getId());
		System.out.println(couponsList);
		System.out.println();

		System.out.println("Get company's coupons list of one category");
		couponsList = companyFacad.getCompanyCoupons(company207.getId(), Categories.Computers);
		System.out.println(couponsList);
		System.out.println();

		System.out.println("Get company's coupons list with price under or equal to X NIS");
		couponsList = companyFacad.getCompanyCoupons(company207.getId(), 260.0);
		System.out.println(couponsList);

		System.out.println();

		System.out.println(companyFacad.getCompanyDetails(company207.getId()));

	}

}
