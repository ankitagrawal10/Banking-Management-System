package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class Account {
 
	private Connection connection;
	private Scanner scanner;
	public Account(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	
	public long open_account(String email) {
		if(!account_exit(email)) {
			scanner.nextLine();
			System.out.println("Enter your full Name");
			String full_Name = scanner.nextLine();
			System.out.println("Enter your email id");
			String emailid = scanner.nextLine();
			System.out.println("Enter your initial balance:");
			double balanced = scanner.nextDouble();
			scanner.nextLine();
			System.out.println("Enter your security pin:");
			String pin = scanner.nextLine();
			String openaccount = "insert into account(account_no,Full_Name,email,balance,security_pin) values(?,?,?,?,?)";
			try {
				long accountnumber = generateaccount_number();
				PreparedStatement preparedstatement = connection.prepareStatement(openaccount);
				preparedstatement.setLong(1, accountnumber);
				preparedstatement.setString(2, full_Name);
				preparedstatement.setString(3, emailid);
				preparedstatement.setDouble(4, balanced);
				preparedstatement.setString(5, pin);
				
				int affectedrow = preparedstatement.executeUpdate();
				if(affectedrow>0) {
					return accountnumber;
				}
				else {
					  throw new RuntimeException("Account Creation failed!!");
				}	
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		  throw new RuntimeException("Account Already Exist");
	}

	public long getaccountnumber(String email) {
		
		String getquery = "select account_no from account where email = ?";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(getquery);
			preparedstatement.setString(1, email);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return resultset.getLong("account_no");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		throw new RuntimeException("Account Number Doesn't Exist!");
		
	}
	public long generateaccount_number() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("select account_no from account order by account_no desc limit 1");
			if(resultset.next()) {
				long lastaccountnumber = resultset.getLong("account_no");
				return lastaccountnumber+1;
			}
			else {
				return 10000010;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 10000010;
		
	}

	public boolean account_exit(String email) {
		String accountexit = "select account_no from account where email = ? ";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(accountexit);
			preparedstatement.setString(1, email);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return true;
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int account_delete() {
	    scanner.nextLine();
	    System.out.println("Enter your email id:");
	    String email = scanner.nextLine();
	    String deleteaccount = "DELETE FROM account WHERE email = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(deleteaccount);
	        preparedStatement.setString(1, email);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) { 
	            System.out.println("Successfully deleted!");
	        } else {
	            System.out.println("Account not found or failed to delete.");
	        }
	        
	        return rowsAffected; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}

}
