package com.banking.createdacc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class Getdetailsuser {
	Scanner s = new Scanner(System.in);
	ArrayList<String> ar = new ArrayList<String>();

	public ArrayList<String> userDetails() {
		int count = 0;
		do {
			System.out.println("Enter Your email account");
			String email = s.nextLine();
			if (!(checkEmail(email))) {
				System.out.println("INVALID EMAIL,Tryagain");
				count++;
			} else {
				int count1 = 0;
				ar.add(email);
				do {
					System.out.println("Enter the Password");
					String password = s.nextLine();
					if (!(checkPassword(password))) {
						System.out.println("INVALID PASSWORD,Tryagain");
						count1++;
					} else {
						ar.add(password);
						count1 = 3;
					}
				} while (count1< 3);
				count = 3;
			}
		} while (count < 3);
		return ar;
	}

	static boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	public static boolean checkPassword(String password) {
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
