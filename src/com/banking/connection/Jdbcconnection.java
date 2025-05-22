package com.banking.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Jdbcconnection {

	static String url = "jdbc:mysql://localhost:3306/banking";
	static String user = "root";
	static String password = "Rupesh@123";
	static Connection con = null;
	static PreparedStatement st1 = null;
	static ResultSet res = null;
	String DetailsQuery;
	static ArrayList<String> ar =new ArrayList<String>();
	static int accountno = 10001;
	static String Queryp = "INSERT INTO allaccounts VALUES(" + accountno + ",?,?)";
	static String Queryc = "INSERT INTO customerdetails VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println(custPrep(Queryc));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally
		{
			errors();
		}
	}
	public static void errors() {
		try {
			if(res != null) res.close();
			if(st1 != null)st1.close();
			if(con != null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void customerDetails()throws SQLException
	{
		Scanner c = new Scanner(System.in);
		String s = "";
		for (int i = 0; i < 5; i++) {
			switch(i)
			{
				case 0 : s = accountno+"";
						break;
				case 1 : System.out.println("Enter your fullname");
						 s = c.nextLine();
						 break;
				case 2: System.out.println("Enter your Phone number");
						 s = c.nextLine();
						 break;
				case 3 : System.out.println("Enter your Date Of Birth(YYYY-MM-DD)");
						 s = c.nextLine();
						 break;
				case 4 : s = 0+"";
						 break;
			}
			ar.add(i,s);
		}
		
	}
	private static  boolean custPrep(String s) throws SQLException {
		int c = 0;
		try {
			customerDetails();
			st1 = con.prepareStatement(s);
			for (int i = 0; i <ar.size(); i++) {
				String temp = ar.get(i);
				st1.setString(i + 1, temp);
			}
			c = st1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (c > 0)
			return true;
		return false;
	}
	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
}
