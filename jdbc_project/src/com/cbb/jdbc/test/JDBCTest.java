package com.cbb.jdbc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cbb.utils.MyDataSource;

public class JDBCTest {
	public static void main(String[] args) throws SQLException{
		Connection connection = MyDataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(" select * from emp");
		ResultSet results = ps.executeQuery();
		while(results.next()){
			Object a1 = results.getObject(1);
			Object a2 = results.getObject(2);
			Object a3 = results.getObject(3);
			Object a4 = results.getObject(4);
			Object a5 = results.getObject(5);
			Object a6 = results.getObject(6);
			Object a7 = results.getObject(7);
			System.out.println(a1+" "+a7);
		
		}
		results.close();
		ps.close();
		connection.close();
	}
}
