package com.banking.createdacc;

import java.sql.SQLException;

public interface banksinterface {
	
	public String createAccount();
	public void signIn();
	public void accDetails();
	public void checkBalance()throws SQLException;
	public void updateBalance() throws SQLException;
}
