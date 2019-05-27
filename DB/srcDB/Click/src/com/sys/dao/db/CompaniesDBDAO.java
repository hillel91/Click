package Click.src.com.sys.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Click.src.com.sys.beans.Company;
import Click.src.com.sys.beans.Coupon;
import Click.src.com.sys.connections.ConnectionPool;
import Click.src.com.sys.dao.CompaniesDAO;
import Click.src.com.sys.exceptions.CouponSystemException;
import Click.src.com.sys.exceptions.GetConnectionException;
import Click.src.com.sys.exceptions.LoadDriver;
import Click.src.com.sys.exceptions.TableCreateException;

public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool connectionPool;

	public CompaniesDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/*
	 * @Override public boolean isCompanyExists(String email, String password)
	 * throws CouponSystemException {
	 * 
	 * String sql = "SELECT id from companies where email=? AND password=?";
	 * Connection con = connectionPool.getConnection(); try (PreparedStatement pstmt
	 * = con.prepareStatement(sql)) { pstmt.setString(1, email); pstmt.setString(2,
	 * password); ResultSet rs = pstmt.executeQuery(); return rs.next(); } catch
	 * (SQLException e) { throw new
	 * CouponSystemException("'Is company exist' failed", e); } finally {
	 * connectionPool.restoreConnection(con); } }
	 */

	@Override
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
		/*
		 * try (PreparedStatement pstmt = con.prepareStatement(sql)) { Set<Integer>
		 * couponsIdSet = new HashSet<>(); while (rs.next()) {
		 * couponsIdSet.add(rs.getInt(1)); } for(int currCoupon: couponsIdSet) {
		 * pstmt.setInt(1, currCoupon); pstmt.executeUpdate(); } } catch (SQLException
		 * e) { throw new
		 * CouponSystemException("Delete from 'customers_vs_coupons' failed", e); }
		 */
	}
	
	public void deleteCouponsFromCouponTable(ResultSet rs, Connection con) throws CouponSystemException {
		String sql = "DELETE FROM COUPONS WHERE COUPON_ID=?";
		doStatement(rs, con, sql);
		System.out.println("Succeed to delete from Coupon table");
	}
	
	public void doStatement(ResultSet rs, Connection con,  String sql) throws CouponSystemException {
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			Set<Integer> couponsIdSet = new HashSet<>();
			while (rs.next()) {
				couponsIdSet.add(rs.getInt(1));
			}
			for(int currCoupon: couponsIdSet) {
				pstmt.setInt(1, currCoupon);
				pstmt.executeUpdate();				
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Delete from 'customers_vs_coupons' failed", e);
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

	/*
	 * @Override public ArrayList<Company> getAllCompanies() throws
	 * CouponSystemException { String sql = "SELECT * FROM companies"; Connection
	 * con = connectionPool.getConnection(); try(PreparedStatement pstmt =
	 * con.prepareStatement(sql)){ pstmt.executeQuery(); ResultSet rs =
	 * pstmt.getResultSet();
	 * 
	 * ResultSetMetaData rsmd = rs.getMetaData(); int columnCount =
	 * rsmd.getColumnCount();
	 * 
	 * ArrayList<Object> hotelResultList = new ArrayList<>(); while (rs.next()) {
	 * int i = 1; int j=1; Company company = new Company(); while(i <= columnCount)
	 * { hotelResultList.add(rs.getObject(i)); System.out.println(rs.getObject(i));
	 * i++;
	 * 
	 * 
	 * } }
	 * 
	 * 
	 * return companyList; //Check it! } catch (SQLException e) {
	 * e.printStackTrace(); throw new
	 * CouponSystemException("Get all companies Failed"); } }
	 */

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		String sql = "SELECT * FROM companies WHERE id=?";
		Connection con = connectionPool.getConnection();
		Company company = new Company();
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
