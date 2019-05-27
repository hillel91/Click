
//one Class Exception with methods or some classes 
//commit the project to Git
//look at the connectionPool

package Click.src.com.sys.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

import Click.src.com.sys.exceptions.CouponSystemException;
import Click.src.com.sys.exceptions.GetConnectionException;
import Click.src.com.sys.exceptions.LoadDriver;

/**
 * 
 * @author hbpe9 This class represents a pool of connections to the DB
 */

// this is a singleton class - only one instance of this class can be created
// Steps of singleton class:
// 1. create a private CTOR
// 2. create a private static instance of this class
// 3. create public static method to obtain the instance
public class ConnectionPool {

	private static ConnectionPool instance; // 1st step of singleton
	private static Set<Connection> connections = new HashSet<>();
	private static boolean connectionPoolIsOpen = true;
	public static final int max_Connenctions = 10;
	private String driverName = "org.apache.derby.jdbc.ClientDriver";
	private String url = "jdbc:derby://localhost:1527/dbCompany;create=true;";

	private ConnectionPool() throws GetConnectionException { /* 2nd step of singelton: private constructor */
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			throw new GetConnectionException("Cannot connect to the server. Class not found.");
		}
		for (int i = 0; i < max_Connenctions; i++) {
			try {
				connections.add(DriverManager.getConnection(url));
			} catch (SQLException e) {
				throw new GetConnectionException("Cannot connect to the DB.");
			}
		}
	}

	public static ConnectionPool getInstance() throws LoadDriver, GetConnectionException { // 3th step of singelton
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (GetConnectionException e) {
				throw new GetConnectionException("Creating a connection Pool failed. " + "Please try later");
			}
		}
		return instance;
	}

	public synchronized Connection getConnection() throws CouponSystemException {
		if (connectionPoolIsOpen == true) {
			while (connections.isEmpty()) {
				waiting("Waiting for other connections failed. Please try later");
			}
			Iterator<Connection> it = connections.iterator();
			Connection con = it.next();
			it.remove();
			notify();
			return con;
		} else {
			System.out.println("The service is closed. Please try later");
			return null;
		}
	}

	public synchronized void restoreConnection(Connection con) throws CouponSystemException {
		try {
		connections.add(con);
		notify();
		}catch(NullPointerException e){
			throw new CouponSystemException("The connection is null", e);
		}
		
	}

	public synchronized void closeAllConnections() throws CouponSystemException {
		connectionPoolIsOpen = false;
		Iterator<Connection> it = connections.iterator();
		int numOfConnections = 0;
		while (numOfConnections < max_Connenctions) {
			if (connections.size() > 0) {
				try {
					it.next().close();
				} catch (SQLException e) {
					throw new CouponSystemException("Cannot reach the next connection and to close it");
				}
				it.remove();
				numOfConnections += 1;
			} else {
				waiting("Waiting for other connections failed. Please try later");
			}
		}
		instance = null;
	}

	public void waiting(String massage) throws CouponSystemException {
		try {
			wait();
		} catch (InterruptedException e) {
			throw new CouponSystemException(massage);
		}
	}

}
