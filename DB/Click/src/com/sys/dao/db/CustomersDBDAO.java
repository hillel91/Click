package com.sys.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.sys.beans.Coupon;
import com.sys.beans.Customer;
import com.sys.categories.Categories;
import com.sys.connections.ConnectionPool;
import com.sys.dao.CustomersDAO;
import com.sys.exceptions.CouponSystemException;

public class CustomersDBDAO implements CustomersDAO {

	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		String sql = "SELECT id FROM customers WHERE email=? AND password=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is Customer exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public boolean isCustomerExists(String customerEmail) throws CouponSystemException {
		String sql = "SELECT id FROM customers WHERE email=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customerEmail);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is Customer exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public boolean isCustomerExists(int customerId) throws CouponSystemException {
		String sql = "SELECT id FROM customers WHERE Id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is Customer exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public int addCustomer(Customer customer) throws CouponSystemException {

		String sql = "INSERT INTO customers(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(?,?,?,?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			customer.setId(id);
			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("'Is Customer exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {

		String sql = "UPDATE customers SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=? WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Update customer whith id " + customer.getId() + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {
		String sql = "DELETE FROM customers WHERE id=?";
		Connection con = connectionPool.getConnection();
		deleteCouponsOfCustomer(customerID, con);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete customer whith id " + customerID + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void deleteCouponsOfCustomer(int customerId, Connection con) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE id=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			deleteCouponsFromCustomerVsCouponTable(rs, con);
		} catch (SQLException e) {
			throw new CouponSystemException("Select details of company number " + customerId + " failed");
		}
	}

	public void deleteCouponsFromCustomerVsCouponTable(ResultSet rs, Connection con) throws CouponSystemException {
		String sql = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			Set<Integer> couponsIdSet = new HashSet<>();
			while (rs.next()) {
				couponsIdSet.add(rs.getInt(1));
			}
			for (int currCoupon : couponsIdSet) {
				pstmt.setInt(1, currCoupon);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Delete failed", e);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		String sql = "SELECT * FROM customers";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			ArrayList<Customer> customerResult = new ArrayList<>();
			while (rs.next()) {
				customerResult.add(new Customer(rs.getInt("id"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
						rs.getString("EMAIL"), rs.getString("PASSWORD")));
			}
			return customerResult;
		} catch (SQLException e) {
			throw new CouponSystemException("Get all customers failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		String sql = "SELECT * FROM customers WHERE id=?";
		Connection con = connectionPool.getConnection();
		Customer customer = new Customer(customerID);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("FIRST_NAME"));
				customer.setLastName(rs.getString("LAST_NAME"));
				customer.setEmail(rs.getString("EMAIL"));
				customer.setPassword(rs.getString("PASSWORD"));
				return customer;
			} else {
				throw new CouponSystemException("There is no customer with id " + customerID);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get customer with id " + customerID + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public ArrayList<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {
		String sql = "select co.* from coupons co JOIN customers_vs_coupons cvc "
				+ "ON co.id=cvc.coupon_id where cvc.customer_id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				couponsList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID") - 1,
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

	// מקבל מטבלת לקוחות את האיי-די של הקופונים שהקטגוריה שלהם היא זו המבוקשת ואז
	// לוקח מטבלת הרכישות את הקופונים שהאיי-די של הלקוח שווה למבוקש והאיי די של
	// הקופון שווה למה שקיבלנו
	/*
	 * public ArrayList<Coupon> getCustomerCoupons(int customerId, Categories
	 * category) throws CouponSystemException { String sqlFromCoupons =
	 * "SELECT * FROM coupons WHERE CATEGORY_ID=?"; String sqlFromCustomersVsCoupons
	 * = "SELECT * FROM customers_vs_coupons WHERE customer_id=? AND coupon_id=?";
	 * Connection con = connectionPool.getConnection(); try (PreparedStatement pstmt
	 * = con.prepareStatement(sqlFromCoupons)) { pstmt.setInt(1,
	 * category.ordinal()); ResultSet rs = pstmt.executeQuery(); ArrayList<Coupon>
	 * couponsList = new ArrayList<>(); ArrayList<Integer> idCouponsList = new
	 * ArrayList<>(); ArrayList<Integer> couponIdList = new ArrayList<>(); while
	 * (rs.next()) { couponsList.add(new Coupon(rs.getInt("id"),
	 * rs.getInt("company_id"), rs.getInt("category_ID"), rs.getString("title"),
	 * rs.getString("description"), rs.getDate("start_date"),
	 * rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"),
	 * rs.getString("image"))); idCouponsList.add(rs.getInt("id")); } for (int i =
	 * 0; i < idCouponsList.size(); i++) { try (PreparedStatement pstmt2 =
	 * con.prepareStatement(sqlFromCustomersVsCoupons)) { pstmt2.setInt(1,
	 * customerId); pstmt2.setInt(2, idCouponsList.get(i)); ResultSet rs2 =
	 * pstmt.executeQuery(); while (rs2.next()) { couponIdList.add(rs.getInt(2)); }
	 * } } for (Coupon currCoupon : couponsList) { for (int i = 0; i <
	 * couponIdList.size(); i++) { if (currCoupon.getId() != couponIdList.get(i)) {
	 * couponsList.remove(currCoupon); } } } return couponsList; } catch
	 * (SQLException e) { throw new
	 * CouponSystemException("'Get list of coupons' failed", e); } finally {
	 * connectionPool.restoreConnection(con); } }
	 */

	public ArrayList<Coupon> getCustomerCoupons(int customerId, Categories category) throws CouponSystemException {
		String sql = "select co.* from coupons co JOIN customers_vs_coupons cvc "
				+ "ON co.id=cvc.coupon_id where cvc.customer_id=? AND co.category_id=?";
		ArrayList<Coupon> couponList = new ArrayList<>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, category.ordinal());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				couponList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID") - 1,
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
		} catch (SQLException e) {
			throw new CouponSystemException(
					"Get coupon from category " + category + " of customer " + customerId + " failed");
		} finally {
			connectionPool.restoreConnection(con);
		}
		return couponList;
	}

	public ArrayList<Coupon> getCustomerCoupons(int customerId, double maxPrice) throws CouponSystemException {

		String sql = "select co.* from coupons co JOIN customers_vs_coupons cvc "
				+ "ON co.id=cvc.coupon_id where cvc.customer_id=? AND co.price<=?";
		Connection con = connectionPool.getConnection();
		ArrayList<Coupon> couponList = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				couponList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), rs.getInt("category_ID") - 1,
						rs.getString("title"), rs.getString("description"), rs.getDate("start_date"),
						rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return couponList;
		} catch (SQLException e) {
			throw new CouponSystemException("Get coupon of customer " + customerId + " with maximum" + " failed");
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	public Customer getCustomerDetails(int customerId) throws CouponSystemException {
		String sql = "SELECT * FROM customers WHERE id=?";
		Connection con = connectionPool.getConnection();
		Customer customer = new Customer();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				customer.setId(customerId);
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setEmail(rs.getString(4));
				customer.setLastName(rs.getString(3));
			}
			return customer;
		} catch (SQLException e) {
			throw new CouponSystemException("'Get list of coupons' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}
}
