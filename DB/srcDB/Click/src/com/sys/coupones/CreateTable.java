package Click.src.com.sys.coupones;

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
			String sqlCreateTable = "create table coupons(id INTEGER PRIMARY KEY "
					+ "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,"
					+ " company_id INTEGER REFERENCES companies(id), "
					+ "category_ID INTEGER REFERENCES categories(id), "
					+"title VARCHAR(30), description VARCHAR(30), start_date DATE, end_date DATE, "
					+"amount INTEGER, price double, image VARCHAR(30))";
			stmt.executeUpdate(sqlCreateTable);
			System.out.println("Table named 'coupons' created");
		} catch (SQLException e1) {
			throw new TableCreateException("Creating table, named copunes failed. Please try later");
		}
	}

}
