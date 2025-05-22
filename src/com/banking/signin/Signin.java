package com.banking.signin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.banking.connection.Jdbcconnection;
import com.banking.createdacc.Getdetailsuser;

public class Signin {
	static Connection con;
	ArrayList<String> ar;

	public void signIn()throws SQLException
	{
		con = Jdbcconnection.getConnection();
		ar = new Getdetailsuser().userDetails();
		Getcredentials gct = new Getcredentials();
		Integer accno = gct.getAccountno(con, ar.get(0), ar.get(1));
		if(accno != 0){
			System.out.println("Account logged in ------->"+accno);
			LinkedHashMap<Integer, ArrayList<String>> map = gct.getMap(con, ar.get(0), ar.get(1));
			accdetails(accno, map);
		}
		else{
			System.out.println("NO account please Create account");
		}
//		Jdbcconnection.errors();
	}
	public void accdetails(Integer accno,LinkedHashMap<Integer, ArrayList<String>> map)
	{
		ArrayList<String> arl = map.get(accno);
		System.out.println(arl);
	}
}
