package com.sys.facade;

import java.util.ArrayList;

import com.sys.beans.Company;
import com.sys.beans.Coupon;
import com.sys.categories.Categories;
import com.sys.coupon.login.ClientFacade;
import com.sys.coupon.login.ClientType;
import com.sys.dao.db.CompaniesDBDAO;
import com.sys.dao.db.CouponsDBDAO;
import com.sys.exceptions.CouponSystemException;

public class CompanyFacad extends ClientFacade {

	int companyId;

	public CompanyFacad() throws CouponSystemException {
		companiesDBDAO = new CompaniesDBDAO();
		couponsDBDAO = new CouponsDBDAO();
		clientType = ClientType.Company;
	}

	public CompanyFacad(int companyId) throws CouponSystemException {
		companiesDBDAO = new CompaniesDBDAO();
		couponsDBDAO = new CouponsDBDAO();
		clientType = ClientType.Company;
		this.companyId = companyId;
	}

	@Override
	public boolean login(String email, String password, ClientType clientType) throws CouponSystemException {

		if (companiesDBDAO.loginOrIsExists(email, password, "login")) {
			System.out.println("Welcome");
			return true;
		} else {
			throw new CouponSystemException();
		}
	}

	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (couponsDBDAO.isTitleCouponExists(coupon)) {
			throw new CouponSystemException(
					"Can't add two coupons with the same Title " + coupon.getTitle() + " for the same company");
		} else if (couponsDBDAO.isCouponExists(coupon.getId())) {
			throw new CouponSystemException("Coupon with id " + coupon.getId() + " exists allready");
		} else {
			couponsDBDAO.addCoupon(coupon);
//			companiesDBDAO.getOneCompany(coupon.getCompanyID()).getCoupensList().add(coupon);
		}
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		if (couponsDBDAO.isCouponExists(coupon.getId())) {
			couponsDBDAO.updateCoupon(coupon);
			System.out.println("Updating coupon with id " + coupon.getId() + " succeed");
		} else {
			throw new CouponSystemException("Coupon with id " + coupon.getId() + " isn't exists");
		}
		/*
		 * int companyId = coupon.getCompanyID(); Company company =
		 * companiesDBDAO.getOneCompany(companyId);
		 * company.getCoupensList().set(company.getCoupensList().indexOf(coupon),
		 * coupon);
		 */
	}

	public void deleteCoupon(int couponId) throws CouponSystemException {
		couponsDBDAO.deleteCoupon(couponId);
	}

	public ArrayList<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
		return couponsDBDAO.getAllCoupon(companyId);
	}

	public ArrayList<Coupon> getCompanyCoupons(int companyId, Categories category) throws CouponSystemException {
		return couponsDBDAO.getAllCoupon(companyId, category);
	}

	public ArrayList<Coupon> getCompanyCoupons(int companyId, Double maxPrice) throws CouponSystemException {
		return couponsDBDAO.getAllCoupon(companyId, maxPrice);
	}

	public Company getCompanyDetails(int companyId) throws CouponSystemException {
		return companiesDBDAO.getOneCompany(companyId);
	}

	public void updateCompany(Company company) throws CouponSystemException {
		if (companiesDBDAO.isCompanyExists(company.getId())) {
			companiesDBDAO.updateCompany(company);
			System.out.println("Updating company " + company.getName() + " succeed");
		} else {
			throw new CouponSystemException("This company isn't exists");
		}
	}

}
