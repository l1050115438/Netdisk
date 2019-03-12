package com.cbb.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 数据库连接池 类.使用DBCP数据库连接池。
 * 
 * @author Administrator
 * @version v3.0
 * @date 2018.7
 * @see 需要自定义 jdbc.properties配置文件。
 */
public class MyDataSource {
	
	//修改 数据库连接参数
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/employee?useUnicode=true&characterEncoding=utf-8";
	private static String USER = "root";
	private static String PWD = "123456";

	private static int initialSize = 30;
	private static int minIdle = 10;
	private static int maxIdle = 10;
	private static int maxWait = 5000;
	private static int maxActive = 30;	
	
	private static DataSource dataSource;
	// 配置文件路径 src根目录下：
	private final static String FILENAME = "jdbc.properties";
	
	//加载驱动
	static {
		try {
			//读取配置文件，加载JDBC四大参数
			Properties config = new Properties();
			System.out.println(MyDataSource.class.getClassLoader().getResource(FILENAME).getPath());
			config.load(new FileReader(MyDataSource.class.getClassLoader().getResource(FILENAME).getPath()));
//			DRIVER = config.getProperty("drivername");
//			URL = config.getProperty("url");
//			USER = config.getProperty("username");
//			PWD	= config.getProperty("password");
//
//			initialSize	= Integer.parseInt(config.getProperty("initialSize"));
//			minIdle	= Integer.parseInt(config.getProperty("minIdle"));
//			maxIdle	= Integer.parseInt(config.getProperty("maxIdle"));
//			maxWait	= Integer.parseInt(config.getProperty("maxWait"));
//			maxActive = Integer.parseInt(config.getProperty("maxActive"));
			//加载驱动类
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBCP数据库连接入口
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (dataSource == null) {
			BasicDataSource bds = new BasicDataSource();

			bds.setUrl(URL);
			bds.setDriverClassName(DRIVER);
			bds.setUsername(USER);
			bds.setPassword(PWD);
			bds.setInitialSize(initialSize);
			bds.setMaxActive(maxActive);
			bds.setMinIdle(minIdle);
			bds.setMaxIdle(maxIdle);
			bds.setMaxWait(maxWait);

			dataSource = bds;
		}
		Connection conn = null;
		if (dataSource != null) {
			conn = dataSource.getConnection();
		}
		return conn;
	}

	 

}
