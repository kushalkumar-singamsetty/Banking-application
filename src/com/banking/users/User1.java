package com.banking.users;
import com.banking.createdacc.Banking;
public class User1 {
	public static void main(String[] args) {
		Banking b1 = new Banking();
		display(b1);
	}
	private static void display(Banking b1)
	{
		System.out.println("1. CREATE ACCOUNT");
		System.out.println("2. SIGNIN TO YOUR ACCOUNT");
		System.out.println("3. YOUR ACCOUNT DETAILS");
		System.out.println("4. CHECK YOUR BALANCE");
		System.out.println("5. UPDATE YOUR BALANCE");
		
		b1.options();
	}
}
