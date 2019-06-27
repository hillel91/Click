package com.sys.categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import com.sys.connections.ConnectionPool;
import com.sys.exceptions.CouponSystemException;
import com.sys.exceptions.GetConnectionException;
import com.sys.exceptions.LoadDriver;



public class FullfilCategoriesTable {

	private ConnectionPool connectionPool;

	public FullfilCategoriesTable() throws LoadDriver, GetConnectionException {
		connectionPool = ConnectionPool.getInstance();
	}

	public void fullfilCategoriesTable(String name) throws CouponSystemException {
		String sql = "INSERT INTO categories(name) VALUES(?)";
		Connection con = connectionPool.getConnection();
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			//ResultSet rs = pstmt.getGeneratedKeys();
			//rs.next();
			//return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CouponSystemException("Fullfil categories whit the category name " + name + " failed");
		}finally {
			connectionPool.restoreConnection(con);
		}
		
	}
	
	public static void main(String[] args) throws CouponSystemException {
		FullfilCategoriesTable fullfilCategoriesTable = new FullfilCategoriesTable();
			fullfilCategoriesTable.fullfilCategoriesTable("Vacation");
	}

}
