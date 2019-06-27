package com.sys.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.sys.exceptions.TableCreateException;

public class CustomerCreateTable {

	public static void main(String[] args) throws TableCreateException {

		String url = "jdbc:derby://localhost:1527/dbCompany";
		try (Connection con = DriverManager.getConnection(url + ";create=true");
				Statement stmt = con.createStatement();) {
			String sqlCreateTable = "create table customers(id INTEGER PRIMARY KEY"
					+ " GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,"
					+ " first_name VARCHAR(30), last_name VARCHAR(30), email VARCHAR(35), "
					+ "password VARCHAR(15))";
			stmt.executeUpdate(sqlCreateTable);
			System.out.println("Table named 'customers' created");
			} catch (SQLException e1) {
				throw new TableCreateException("Creating table, named customers failed. Please try later");

		}
	}

}
