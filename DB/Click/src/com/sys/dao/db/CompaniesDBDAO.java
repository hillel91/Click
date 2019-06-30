package com.sys.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.sys.beans.Company;
import com.sys.connections.ConnectionPool;
import com.sys.dao.CompaniesDAO;
import com.sys.exceptions.CouponSystemException;

public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool connectionPool;

	public CompaniesDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	/**
	 * This method checks if email or password of company exists, according the
	 * company id.
	 */
	public boolean isCompanyExists(String data, String check) throws CouponSystemException {

		String sql = "SELECT id from companies where " + data + "=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, check);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is company exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method gives the ability to check if a company is exists according the
	 * company's id
	 * 
	 * @param companyId
	 * @return boolean
	 * @throws CouponSystemException
	 */
	public boolean isCompanyExists(int companyId) throws CouponSystemException {

		String sql = "SELECT id from companies where id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("'Is company exist' failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method created to improve the efficient by giving the opportunity to get
	 * answer for login/exist without the need of send parametrs to another method
	 * 
	 * @param data
	 * @param attribute
	 * @param check     //check="login" or "something else"
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean checkLoginOrIsExists(String data, String attribute, String check) throws CouponSystemException {
		boolean loginTrue = false;
		String sql = "SELECT * FROM companies WHERE " + attribute + "=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, data);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (check == "login") {
					loginTrue = true;
				}
			} else {
				throw new CouponSystemException("check the " + data);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("'Check login' failed when " + data + " sent");
		}
		return loginTrue;
	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @param check
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean loginOrIsExists(String email, String password, String check) throws CouponSystemException {
		boolean loginTrue = false;
		loginTrue = checkLoginOrIsExists(email, "email", "login");
		loginTrue = checkLoginOrIsExists(password, "password", "login");
		return loginTrue;
	}

	@Override
	public int addCompany(Company company) throws CouponSystemException {

		String sql = "INSERT INTO companies(name, email, password) VALUES(?,?,?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			company.setId(id);
			// companyList.add(companyList.size(), company);
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("Insert new company to DB failed");
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {

		String sql = "UPDATE companies SET name=?, email=?, password=? WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setLong(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Update company " + company.getName() + " failed");
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		Connection con = connectionPool.getConnection();
		deleteCouponsOfCompany(companyID, con);
		String sql = "DELETE FROM companies WHERE id=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponSystemException("Delete company with ID " + companyID + " failed");
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/**
	 * This method makes the preparation of deletes coupons from customer_vs_coupon
	 * Table and coupons table. It is possible to use "join" require, instead of do
	 * the long way I did here, but in this way I showed my understanding how the
	 * process goes. In other places of the system (like
	 * com.sys.dao.db.CouponsDBDAO.isPurchaseExists), I used join require.
	 * 
	 * @param companyId
	 * @param con
	 * @throws CouponSystemException
	 */
	public void deleteCouponsOfCompany(int companyId, Connection con) throws CouponSystemException {
		String sql = "SELECT * FROM coupons WHERE id=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			deleteCouponsFromCustomerVsCouponTable(rs, con);
			deleteCouponsFromCouponTable(rs, con);
		} catch (SQLException e) {
			throw new CouponSystemException("Select details of company number " + companyId + " failed");
		}
	}

	public void deleteCouponsFromCustomerVsCouponTable(ResultSet rs, Connection con) throws CouponSystemException {
		String sql = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID=?";
		doStatement(rs, con, sql);
		System.out.println("Succeed to delete from customer_vs_coupon table");
	}

	public void deleteCouponsFromCouponTable(ResultSet rs, Connection con) throws CouponSystemException {
		String sql = "DELETE FROM COUPONS WHERE ID=?";
		doStatement(rs, con, sql);
		System.out.println("Succeed to delete from Coupon table");
	}

	/**
	 * This method created in order to improve the readability and make the code
	 * shorter of the methods "deleteCouponsFromCustomerVsCouponTable" and
	 * "deleteCouponsFromCouponTable", which have the same purpose.
	 * 
	 * @param rs
	 * @param con
	 * @param sql
	 * @throws CouponSystemException
	 */
	public void doStatement(ResultSet rs, Connection con, String sql) throws CouponSystemException {
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
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		String sql = "SELECT * FROM companies";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			ArrayList<Company> companyResult = new ArrayList<>();
			while (rs.next()) {
				companyResult.add(new Company(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getString("password")));
			}
			return companyResult;
		} catch (SQLException e) {
			throw new CouponSystemException("Get all companies Failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		String sql = "SELECT * FROM companies WHERE id=?";
		Connection con = connectionPool.getConnection();
		Company company = new Company(companyID);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				return company;
			} else {
				throw new CouponSystemException("There is no company with id " + companyID);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get company with ID " + companyID + " failed");
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

}
