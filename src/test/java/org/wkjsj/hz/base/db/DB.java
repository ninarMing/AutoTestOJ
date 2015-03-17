package org.wkjsj.hz.base.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wkjsj.hz.base.webDriver.Webdriver;

public class DB {

	public static String dbtype = "";

	/**********************************************************************************************/
	// 连接跟关闭数据库

	/*
	 * @return 返回数据库连接，Connection对象
	 */
	public Connection getConnection(Webdriver driver) {
		Connection conn = null;
		try {
			String ip =driver.getIp();
			// 连接ODBC数据源

			String sqlDriver = "com.mysql.jdbc.Driver";
			String connectionString = "jdbc:mysql://" + ip
					+ ":3306/jol?useunicode=true&characterEncoding=UTF-8";
			// String connectionString =
			// "jdbc:mysql://localhost:3306/jol?useunicode=true&characterEncoding=UTF-8";
			String username = "root";
			String password = "root";
			Class.forName(sqlDriver).newInstance();
			conn = DriverManager.getConnection(connectionString, username,
					password);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return conn;

	}

	/*
	 * 关闭数据库连接对象
	 */

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}

	/**********************************************************************************************/
	// 增删改查操作

	/*
	 * select,query无参数,,这么写rs和pstm怎么关闭？？？？？？？？？？？？？
	 */
	public ResultSet selResult(Connection con, String query) {
		PreparedStatement pstm =null;
		//
		ResultSet rs = null;
		try {
			pstm = con.prepareStatement(query);
			rs = pstm.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}



}
