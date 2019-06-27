package com.sys.dailyJob;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
//import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.sys.connections.ConnectionPool;
import com.sys.dao.CouponsDAO;
import com.sys.exceptions.CouponSystemException;
import com.sys.exceptions.GetConnectionException;

public class CouponExpirationDailyJob extends TimerTask implements Runnable {

	private ConnectionPool connectionPool;
	private CouponsDAO couponsDAO;
	private boolean quit;

	public CouponExpirationDailyJob() throws CouponSystemException, GetConnectionException {
		connectionPool = ConnectionPool.getInstance();
		quit = false;
	}

	@Override
	public void run() {
		while (quit == false) {
			long currennTime = System.currentTimeMillis();
			Date currentDate = new Date(currennTime);
			long stopTime = currennTime;
			try {
				deleteJoinTwoTables(currentDate);
				deleteFromCouponsTable(currentDate);
			} catch (CouponSystemException e) {
				System.out.println("SqlException");
			}
			System.out.println("Start Job " + stopTime);
			System.out.println("End Job " + System.currentTimeMillis());

			try {
				/*
				 * There was to options to determinate one day: 1. Count 1000 * 60 * 60 *24 ms
				 * of one day (The delete action will occur once a day, from the hour of the
				 * system upload) 2. Compare with 00:00 hour. For now I used the 1st option
				 */
				long day = 1000 * 60 * 60 * 24;
				Thread.sleep(day);
			} catch (InterruptedException e) {
				System.out.println("Failed to sleep for one day");
			}
		}
		System.out.println("The service is closed. Please try later");
	}

	public void deleteJoinTwoTables(Date currentDate) throws CouponSystemException {

		String sql = "DELETE FROM customers_vs_coupons c_v_c WHERE c_v_c.coupon_id IN"
				+ "(SELECT c_v_c.coupon_id FROM customers_vs_coupons c_v_c"
				+ " INNER JOIN coupons c ON (c_v_c.coupon_id=c.id) where end_date<CURRENT_DATE)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete Expiration coupons failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void deleteFromCouponsTable(Date currentDate) throws CouponSystemException {
		String sql = "DELETE FROM coupons WHERE end_date<CURRENT_DATE";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete Expiration coupons failed", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void stop() {
		quit = true;
	}

	public static void startTask() throws GetConnectionException, CouponSystemException {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Timer timer = new Timer();
		timer.schedule(new CouponExpirationDailyJob(), today.getTime(),
				TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

	}

	public boolean isQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	public static void main(String[] args) {
		try {
			startTask();
		} catch (GetConnectionException e) {
			System.out.println("GetConnectionException");
		} catch (CouponSystemException e) {
			System.out.println("CouponSystemException");
		}
	}

}
