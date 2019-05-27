package Click.src.com.sys.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale.Category;

import Click.src.com.sys.beans.Coupon;
import Click.src.com.sys.beans.Customer;
import Click.src.com.sys.connections.ConnectionPool;
import Click.src.com.sys.dao.CouponsDAO;
import Click.src.com.sys.dao.CustomersDAO;
import Click.src.com.sys.exceptions.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {

	private ConnectionPool connectionPool;

	public CouponsDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean isCouponExists(int couponID) throws CouponSystemException {
		String sql = "SELECT id FROM copunes WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, couponID);
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
		String sql = "INSERT INTO copunes(companyID, category, title, "
				+ "description, startDate, endDate, amount, price, image, id)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			prepareStmt(pstmt, coupon);
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

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		String sql = "UPDATE copunes SET companyID=?, category=?, title=?, description=?, startDate=?, endDate=?, amount=?, price=?, image=? WHERE id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			prepareStmt(pstmt, coupon);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Update coupon with id " + coupon.getId() + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {

		String sql = "DELETE FROM copunes WHERE id=?";
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
		String sql = "SELECT * FROM copunes";
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

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		String sql = "SELECT * FROM copunes WHERE id=?";
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
					throw new CouponSystemException("There is no coupon with id " + couponID + "failed");
				} else {
					throw new CouponSystemException("'Get coupon' with id " + couponID + "failed");
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
		
		String sql ="INSERT INTO customers_vs_cupons(customer_id, cupon_id) VALUES(?,?)";
		Connection con = connectionPool.getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();	
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("'Add coupon purchase' with customer id"
					+ " " + customerID +" and coupon id "+ couponId + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponId) throws CouponSystemException {

		String sql = "DELETE FROM customers_vs_cupons WHERE customer_id=? AND cupon_id=?";
		Connection con = connectionPool.getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new CouponSystemException("'Delete coupon purchase' with customer id"
					+ " " + customerID +" and coupon id "+ couponId + " failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public PreparedStatement prepareStmt(PreparedStatement pstmt, Coupon coupon) throws CouponSystemException {
		
		try {
			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, coupon.getCategory().ordinal());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());

			return pstmt;
		} catch (SQLException e) {
			throw new CouponSystemException("Prepare the statement failed");
		}
	}

public Coupon setCoupon(ResultSet rs, Coupon coupon) throws CouponSystemException {
	try{
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
	}catch (SQLException e) {
		throw new CouponSystemException("Set details for coupon failed");
	}
}

}
