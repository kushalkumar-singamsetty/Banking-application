package com.banking.signin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Getcredentials {
	static Statement st;
	static String query = "SELECT * FROM allaccounts";
	static LinkedHashMap<Integer,ArrayList<String>> lhm = new LinkedHashMap<Integer, ArrayList<String>>();
	public LinkedHashMap<Integer,ArrayList<String>> getMap(Connection con,String mail,String passw)
	{
		try {
			st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next())
			{
				lhm.put(res.getInt("accountno"),(create(res.getString("gmail"),res.getString("password"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lhm;
	}
	private static ArrayList<String> create(String email,String password)
	{
		ArrayList<String> ll = new ArrayList<String>();
		ll.add(email);
		ll.add(password);
		return ll;
	}
	public  Integer getAccountno(Connection con,String email,String passw)
	{
		String Query = "SELECT accountno FROM allaccounts WHERE gmail = ? AND password = ?";
		Integer accno = 0;
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setString(1,email);
			ps.setString(2,passw);
			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				accno = res.getInt("accountno");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accno;
	}
}
