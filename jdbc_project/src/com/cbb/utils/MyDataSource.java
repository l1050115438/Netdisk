package com.cbb.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * ���ݿ����ӳ� ��.ʹ��DBCP���ݿ����ӳء�
 * 
 * @author Administrator
 * @version v3.0
 * @date 2018.7
 * @see ��Ҫ�Զ��� jdbc.properties�����ļ���
 */
public class MyDataSource {
	
	//�޸� ���ݿ����Ӳ���
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
	// �����ļ�·�� src��Ŀ¼�£�
	private final static String FILENAME = "jdbc.properties";
	
	//��������
	static {
		try {
			//��ȡ�����ļ�������JDBC�Ĵ����
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
			//����������
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBCP���ݿ��������
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
