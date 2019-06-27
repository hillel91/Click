package com.sys.test;

import com.sys.connections.ConnectionPool;
import com.sys.dailyJob.CouponExpirationDailyJob;
import com.sys.exceptions.CouponSystemException;
import com.sys.exceptions.GetConnectionException;
import com.sys.exceptions.LoadDriver;
import com.sys.facade.TestAdminFacade;
import com.sys.facade.TestCompaniesFacade;
import com.sys.facade.TestCustomersFacade;

public class Test {

	private static ConnectionPool connectionPool;

	public Test() {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (LoadDriver e) {
			System.out.println("failed to load the driver");
		} catch (GetConnectionException e) {
			System.out.println("failed to get connection");
		}
	}

	public static void testAll(String[] args) {
		TestAdminFacade testAdminFacade = new TestAdminFacade();
		TestCompaniesFacade testCompaniesFacade = new TestCompaniesFacade();
		TestCustomersFacade testCustomersFacade = new TestCustomersFacade();
		try {
			CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
			couponExpirationDailyJob.main(args);

			testAdminFacade.main(args);
			testCompaniesFacade.main(args);
			testCustomersFacade.main(args);

			couponExpirationDailyJob.stop();

			connectionPool = ConnectionPool.getInstance();
			connectionPool.closeAllConnections();
			System.out.println("All the connections closed");
		} catch (CouponSystemException e) {
			System.out.println(("Oops.. It's not you. It us. Please try later"));
		}
	}

	public static void main(String[] args) {
		TestAdminFacade testAdminFacade = new TestAdminFacade();
		TestCompaniesFacade testCompaniesFacade = new TestCompaniesFacade();
		TestCustomersFacade testCustomersFacade = new TestCustomersFacade();
		try {
			CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
			couponExpirationDailyJob.main(args);

			testAdminFacade.main(args);
			testCompaniesFacade.main(args);
			testCustomersFacade.main(args);

			couponExpirationDailyJob.stop();

			connectionPool = ConnectionPool.getInstance();
			connectionPool.closeAllConnections();
			System.out.println("All the connections closed");
		} catch (CouponSystemException e) {
			System.out.println(("Oops.. It's not you. It us. Please try later"));
		}
	}

}
