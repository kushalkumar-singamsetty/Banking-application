package com.banking.createdacc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Banking implements banksinterface {
	static String url = "jdbc:mysql://localhost:3306/banking";
	static String user = "root";
	static String password = "root";
	static Statement st = null;
	static ResultSet res = null;
	private int accountno = 0;
	Connection con;
	PreparedStatement st1;
	Integer accno = 0;
	Scanner s1 = new Scanner(System.in);
	ArrayList<String> ar = new ArrayList<String>();
	static String Queryc = "INSERT INTO customerdetails VALUES(?,?,?,?,?,?,?)";

	@Override
	public String createAccount() {
		getConnection();
		userDetails();
		if (ar.size() == 0) {
			return "Invalid Email ,Account not created , Try again";
		}
		if (ar.size() == 1) {
			return "Invalid Password ,Account not created , Try again";
		}
		try {
			if (preparestm()) {
				System.out.println("Account created Successfully \n Your accno : " + accountno);
			}
			if (custPrep()) {
				System.out.println("Details added successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			errors();
		}
		return "Account created Successfully and Details are added";
	}

	private void getConnection() {
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<String> userDetails() {
		int count = 0;
		s1.nextLine();
		do {
			System.out.println("Enter Your email account");
			String email = s1.nextLine();
			if (!(checkEmail(email))) {
				System.out.println("INVALID EMAIL,Tryagain");
				count++;
			} else {
				int count1 = 0;
				ar.add(email);
				do {
					System.out.println("Enter the Password");
					String password = s1.nextLine();
					if (!(checkPassword(password))) {
						System.out.println("INVALID PASSWORD,Tryagain");
						count1++;
					} else {
						ar.add(password);
						count1 = 3;
					}
				} while (count1 < 3);
				count = 3;
			}
		} while (count < 3);
		return ar;
	}

	private boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean checkPassword(String password) {
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	private boolean preparestm() throws SQLException {
		int c = 0;
		try {
			accountno();
			String Queryp = "INSERT INTO allaccounts VALUES(" + accountno + ",?,?)";
			st1 = con.prepareStatement(Queryp);
			for (int i = 0; i < ar.size(); i++) {
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

	private int accountno() throws SQLException {
		String query = "SELECT MAX(accountno) FROM allaccounts";
		Statement s = con.createStatement();
		ResultSet res = s.executeQuery(query);
		while (res.next()) {
			accountno = res.getInt(1);
		}
		if (accountno == 0)
			accountno = 10000;
		return accountno++;
	}

	private boolean custPrep() {
		int c = 0;
		try {
			customerDetails();
			st1 = con.prepareStatement(Queryc);
			for (int i = 0; i < ar.size(); i++) {
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

	private void customerDetails() throws SQLException {
		Scanner c = new Scanner(System.in);
		String s = "";
		for (int i = 0; i < 5; i++) {
			switch (i) {
			case 0:
				s = accountno + "";
				break;
			case 1:
				System.out.println("Enter your fullname");
				s = c.nextLine();
				break;
			case 2:
				System.out.println("Enter your Phone number");
				s = c.nextLine();
				break;
			case 3:
				System.out.println("Enter your Date Of Birth(YYYY-MM-DD)");
				s = c.nextLine();
				break;
			case 4:
				s = 0 + "";
				break;
			}
			ar.add(i, s);
		}

	}

	private Integer getAccountno(String email, String passw) {
		String Query = "SELECT accountno FROM allaccounts WHERE gmail = ? AND password = ?";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setString(1, email);
			ps.setString(2, passw);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				accno = res.getInt("accountno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accno;
	}

	@Override
	public void signIn() {
		try {
			userDetails();
			accno = getAccountno(ar.get(0), ar.get(1));
			if (accno == 0) {
				System.out.println("WRONG EMAIL OR PASSWORD PLEASE VERIFY");
			} else {
				System.out.println("Account logged in ------->" + accno);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("WRONG EMAIL OR PASSWORD PLEASE VERIFY");
		}
	}

	@Override
	public void accDetails() {
		try {
			LinkedHashMap<Integer, ArrayList<String>> map = getMap();
			ArrayList<String> arl = map.get(accno);
			System.out.println("Account No : " + accno);
			for (int i = 0; i < arl.size(); i++) {
				System.out.println(arl.get(i));
			}
		} catch (NullPointerException E) {
			System.out.println("NO ACCOUNT ----> PLEASE VERIFY YOUR ACCOUNT NUMBER ");
		}
	}

	private LinkedHashMap<Integer, ArrayList<String>> getMap() {
		LinkedHashMap<Integer, ArrayList<String>> lhm = new LinkedHashMap<Integer, ArrayList<String>>();
		try {
			String query = "SELECT * FROM customerdetails WHERE accid = " + accno + "";
			st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while (res.next()) {
				lhm.put(res.getInt("accid"),
						(createMap(res.getString("fullname"), res.getBigDecimal("phonenumber"),
								res.getString("Dateofbirth"), res.getString("balance"), res.getString("email"),
								res.getString("password"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lhm;
	}

	private static ArrayList<String> createMap(String fullname, BigDecimal phonenumber, String Dateofbirth,
			String balance, String email, String password) {
		ArrayList<String> ll = new ArrayList<String>();
		ll.add("FullName    -->" + fullname);
		ll.add("phonenumber -->" + phonenumber);
		ll.add("Dateofbirth -->" + Dateofbirth);
		ll.add("balance     -->" + balance);
		ll.add("email       -->" + email);
		ll.add("password    -->" + password);
		return ll;
	}

	@Override
	public void checkBalance() {
		int c = 0;
		try {
			String query = "SELECT balance FROM customerdetails WHERE accid = " + accno + "";
			st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while (res.next()) {
				System.out.println("Current Account Balance : " + res.getInt(1));
			}
//			if () {
//				System.out.println("NO ACCOUNT ----> PLEASE VERIFY YOUR ACCOUNT NUMBER ");
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBalance() {
		int c = 0;
		System.out.println("Enter the Balance :");
		int balance = s1.nextInt();
		try {
			String query = "UPDATE `customerdetails` SET `balance` = " + balance + " WHERE accid = " + accno + "";
			st = con.createStatement();
			c = st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException E) {
			System.out.println("NO ACCOUNT ----> PLEASE VERIFY YOUR ACCOUNT NUMBER ");
		}
		if (c > 0) {
			System.out.println("<-------------Balance Updated ---------->");
		} else {
			System.out.println("<-------------Balance NOT Updated ---------->");
		}
	}

	public void options() {
		try {
			getConnection();
			int o1 = s1.nextInt();
			if (o1 == 1)
				System.out.println(createAccount());
			if (o1 == 2)
				signIn();
			if (o1 == 3 || o1 == 4 || o1 == 5) {
				System.out.println("Enter your Account no");
				accno = s1.nextInt();
				if (o1 == 3) {
					accDetails();
				}
				if (o1 == 4) {
					checkBalance();
				}
				if (o1 == 5) {
					updateBalance();
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("Enter correct options");
		} finally {
			errors();
		}
	}

	private void errors() {
		try {
			if (res != null)
				res.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
