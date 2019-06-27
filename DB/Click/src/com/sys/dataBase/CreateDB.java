package com.sys.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sys.exceptions.DBCreateException;

public class CreateDB {

	public static void main(String[] args) throws DBCreateException {

		String dbUrl = "jdbc:derby://localhost:1527/dbCompany;create=true;";

		try (Connection con = DriverManager.getConnection(dbUrl);) {
			System.out.println("Connected to " + dbUrl);
		} catch (SQLException e1) {
			throw new DBCreateException("Creating database failed. Please try later");
		}
		System.out.println("Success");

	}

}
