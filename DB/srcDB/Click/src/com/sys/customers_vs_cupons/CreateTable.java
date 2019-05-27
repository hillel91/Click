package Click.src.com.sys.customers_vs_cupons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Click.src.com.sys.exceptions.TableCreateException;

public class CreateTable {

	public static void main(String[] args) throws TableCreateException {

		String url = "jdbc:derby://localhost:1527/dbCompany";
		try (Connection con = DriverManager.getConnection(url + ";create=true");
				Statement stmt = con.createStatement();) {
			/*
			 * String sqlCreateTable =
			 * "create table customers_vs_coupons(customer_id INTEGER" +
			 * " REFERENCES customers(id), " + "coupon_id INTEGER REFERENCES coupons(id)," +
			 * " PRIMARY KEY(customer_id,coupon_id)";
			 */
			String sqlCreateTable = "create table customers_vs_coupons(customer_id INTEGER,"
					+ " coupon_id INTEGER, " 
			
			  + " PRIMARY KEY(customer_id,coupon_id)," +
			  " FOREIGN KEY (customer_id) REFERENCES customers(id), " +
			  " FOREIGN KEY (coupon_id) REFERENCES coupons(id))";
			 

			stmt.executeUpdate(sqlCreateTable);
			System.out.println("Table created");
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new TableCreateException("Creating table, named customers_vs_cupons" + " failed. Please try later");
		}
	}

}
