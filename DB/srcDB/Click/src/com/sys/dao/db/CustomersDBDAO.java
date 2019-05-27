package Click.src.com.sys.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Click.src.com.sys.beans.Customer;
import Click.src.com.sys.connections.ConnectionPool;
import Click.src.com.sys.dao.CustomersDAO;
import Click.src.com.sys.exceptions.CouponSystemException;

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
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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

		String sql = "UPDATE customers SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
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
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete customer whith id " + customerID + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		String sql = "SELECT * FROM customers";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getResultSet();
			ArrayList<Customer> customerResult = new ArrayList<>();
			while(rs.next()) {
				customerResult.add(new Customer(rs.getInt("id"), rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD")));
			}
			return customerResult;
		} catch (SQLException e) {
			throw new CouponSystemException("Get all customers failed", e);
		}finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		String sql = "SELECT * FROM customers WHERE id=?";
		Connection con = connectionPool.getConnection();
		Customer customer = new Customer();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("FIRST_NAME"));
				customer.setLastName(rs.getString("LAST_NAME"));
				customer.setEmail(rs.getString("EMAIL"));
				customer.setPassword(rs.getString("PASSWORD"));
				return customer;
			}else {
				throw new CouponSystemException("There is no customer with id " + customerID);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get customer with id " + customerID + " failed", e);
		}finally {
			connectionPool.restoreConnection(con);
		}
	}

}
