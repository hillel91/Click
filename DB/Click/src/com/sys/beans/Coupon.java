package com.sys.beans;

import java.io.Serializable;
//import java.util.Date;
import java.sql.Date;

import com.sys.categories.Categories;
import com.sys.exceptions.CouponSystemException;
import com.sys.facade.CompanyFacad;

public class Coupon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CompanyFacad companyFacade;

	private int id;
	private int companyID;
	private String categoryName;
	private Categories category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String image;

	public Coupon() throws CouponSystemException {
		companyFacade = new CompanyFacad(companyID);
	}

	public Coupon(int companyID) throws CouponSystemException {
		this.companyID = companyID;
		/*
		 * companyFacade = new CompanyFacad(companyID); Company company =
		 * companyFacade.getCompanyDetails(companyID);
		 */
	}

	public Coupon(int id, int categoryId) {
		super();
		this.id = id;
		this.category = getThisCategory(categoryId);
		this.categoryName = this.category.toString();
		// coupon.setCategory(Category.values()[rs.getInt("category_ID")]);
		// this.category = this.category.values()[categoryId];
		// this.category = category;
	}

	public Coupon(int id, int categoryId, String title) {
		super();
		this.id = id;
		this.category = getThisCategory(categoryId);
		this.categoryName = this.category.toString();
//		this.category = category;
		this.title = title;
	}

	public Coupon(int id, int categoryId, String title, String description) {
		super();
		this.id = id;
		this.category = getThisCategory(categoryId);
		this.categoryName = this.category.toString();
		// this.category = category;
		this.title = title;
		this.description = description;
	}

	public Coupon(int id, int categoryId, String title, String description, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.setCategoryId(categoryId);
		this.category = getThisCategory(categoryId);
		this.categoryName = this.category.toString();
		// this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Coupon(int id, int companyID, int categoryId, String title, String description, int amount, double price,
			String image) {
		super();
		this.id = id;
		this.companyID = companyID;
		this.category = getThisCategory(categoryId);
		this.categoryName = this.category.toString();
		// this.category = category;
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(int id, int companyID, int categoryId, String title, String description, Date startDate, Date endDate,
			int amount, double price, String image) {
		super();
		this.id = id;
		this.companyID = companyID;
		this.category = getThisCategory(categoryId);
		// this.categoryName = this.category.toString();
		// this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Categories getThisCategory(int categoryId) {

		Categories newCategory;

		switch (categoryId) {
		case 0:
			newCategory = Categories.values()[0];
			break;
		case 1:
			newCategory = Categories.values()[1];
			break;
		case 2:
			newCategory = Categories.values()[2];
			break;
		case 3:
			newCategory = Categories.values()[3];
			break;
		case 4:
			newCategory = Categories.values()[4];
			break;
		case 5:
			newCategory = Categories.values()[5];
			break;
		case 6:
			newCategory = Categories.values()[6];
			break;

		case 7:
			newCategory = Categories.values()[6];
			break;

		default:
			newCategory = null;

		}

		return newCategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategoryId(Categories category) {
		this.category = category;

	}

	public void setCategoryId(int categoryId) {
		// this.category =

	}

	public void setCategory(Categories category) {
		this.category = category;
		this.categoryName = category.toString();
		this.category = Categories.valueOf(categoryName);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CouponesJavaBeans [id=" + id + ", companyID=" + companyID + ", category=" + category + ", title="
				+ title + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", price=" + price + ", image=" + image + "]";
	}

}
