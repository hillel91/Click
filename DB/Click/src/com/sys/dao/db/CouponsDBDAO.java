package com.sys.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sys.beans.Coupon;
import com.sys.categories.Categories;
import com.sys.connections.ConnectionPool;
import com.sys.dao.CouponsDAO;
import com.sys.exceptions.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {

	private ConnectionPool connectionPool;

	public CouponsDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	public boolean isTitleCouponExists(Coupon coupon) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, coupon.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(2) == coupon.getCompanyID()) {
					if (rs.getString(4) == coupon.getTitle()) {
						return true;
					}
				}
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("'Is coupon exists' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public boolean isCouponExists(int couponId) throws CouponSystemException {
		String sql = "SELECT id FROM coupons WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, couponId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is coupon exists' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		String sql = "INSERT INTO coupons(company_id, category_id, title, "
				+ "description, start_date, end_Date, amount, price, image)" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, coupon.getCategory().ordinal() + 1);
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			coupon.setId(id);

			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("Add coupon with id " + coupon.getId() + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void ChooseFromTheList(Categories category) throws CouponSystemException {
		if (category.toString() == "Choose_from_the_list") {
			throw new CouponSystemException("Choise an option from the categories list");
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		String sql = "UPDATE coupons SET company_id=?, category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, coupon.getCategory().ordinal() + 1);
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Update coupon with id " + coupon.getId() + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		deleteCouponPurchase(couponID);
		String sql = "DELETE FROM coupons WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, couponID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete coupon with id " + couponID + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupon() throws CouponSystemException {
		String sql = "SELECT * FROM coupons";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				couponsList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID"),
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return couponsList;
		} catch (SQLException e) {
			throw new CouponSystemException("'Get list of coupons' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method return list of coupons of company according the company id
	 * 
	 * @param companyId
	 * @return
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCoupon(int companyId) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE COMPANY_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				couponsList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID"),
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return couponsList;
		} catch (SQLException e) {
			throw new CouponSystemException("'Get list of coupons' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method return list of coupons of company according the company id, and
	 * the category required
	 * 
	 * @param companyId
	 * @param category
	 * @return
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCoupon(int companyId, Categories category) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE COMPANY_ID=? AND CATEGORY_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			pstmt.setInt(2, category.ordinal() + 1);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				couponsList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID"),
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return couponsList;
		} catch (SQLException e) {
			throw new CouponSystemException("'Get list of coupons' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method return list of coupons of company according the company id. The
	 * list contains only coupons with price under or equal the maxPrice parameter
	 * 
	 * @param companyId
	 * @param maxPrice
	 * @return
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCoupon(int companyId, Double maxPrice) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE COMPANY_ID=? AND PRICE<=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				couponsList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID"),
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return couponsList;
		} catch (SQLException e) {
			throw new CouponSystemException("'Get list of coupons' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE id=?";
		Connection con = connectionPool.getConnection();
		Coupon coupon = new Coupon();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				setCoupon(rs, coupon);
				return coupon;
			} else {
				if (isCouponExists(couponID)) {
					throw new CouponSystemException("'Get coupon' with id " + couponID + " failed");
				} else {
					throw new CouponSystemException("There is no coupon with id " + couponID + "failed");
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("'Get coupon' with id " + couponID + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public int addCouponPurchase(int customerID, int couponId) throws CouponSystemException {

		String sql = "INSERT INTO CUSTOMERS_VS_COUPONS(customer_id, coupon_id) VALUES(?,?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("'Add coupon purchase' with customer id" + " " + customerID
					+ " and coupon id " + couponId + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public boolean isPurchaseExists(Coupon coupon, int customerId) throws CouponSystemException {

		String sql = "SELECT cvc.COUPON_ID, cvc.CUSTOMER_ID FROM CUSTOMERS_VS_COUPONS cvc "
				+ "INNER JOIN coupons c ON cvc.COUPON_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, coupon.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to select from CUSTOMERS_VS_COUPONS teble ", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return false;
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponId) throws CouponSystemException {

		String sql = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMER_ID=? AND COUPON_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("'Delete coupon purchase' with customer id" + " " + customerID
					+ " and coupon id " + couponId + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void deleteCouponPurchase(int couponId) throws CouponSystemException {

		String sql = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException(
					"'Delete coupon purchase, with coupon id " + couponId + " from customers_vs_cupons table failed",
					e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method created to improve the readability of "getOneCoupon" method
	 * 
	 * @param rs
	 * @param coupon
	 * @return Coupon
	 * @throws CouponSystemException
	 */
	public Coupon setCoupon(ResultSet rs, Coupon coupon) throws CouponSystemException {
		try {
			coupon.setId(rs.getInt("id"));
			coupon.setCompanyID(rs.getInt("company_id"));
			coupon.setCategoryId(rs.getInt("category_ID"));
			coupon.setTitle(rs.getString("title"));
			coupon.setDescription(rs.getString("description"));
			coupon.setStartDate(rs.getDate("start_date"));
			coupon.setEndDate(rs.getDate("end_date"));
			coupon.setAmount(rs.getInt("amount"));
			coupon.setPrice(rs.getDouble("price"));
			coupon.setImage(rs.getString("image"));
			return coupon;
		} catch (SQLException e) {
			throw new CouponSystemException("Set details for coupon failed");
		}
	}

}
