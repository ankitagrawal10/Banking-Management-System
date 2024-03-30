package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

	private Connection connection;
	private Scanner scanner;
	public AccountManager(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void credit_money(long account_number) throws SQLException {
		scanner.nextLine();
		System.out.println("Enter your amount");
		double amount = scanner.nextDouble();
		scanner.nextLine();
		System.out.println("Enter your security_pin");
		String securitypin = scanner.nextLine();
		try {
			connection.setAutoCommit(false);
			if(account_number!=0) {
				PreparedStatement preparedstatement = connection.prepareStatement("select *from account where account_no = ? and security_pin = ?");
				preparedstatement.setLong(1, account_number);
				preparedstatement.setString(2, securitypin);
				ResultSet resultset = preparedstatement.executeQuery();
				if(resultset.next()) {
					String credit_query = "update account set balance = balance + ? where account_no = ?";
					PreparedStatement preparedstatement1 = connection.prepareStatement(credit_query);
					preparedstatement1.setDouble(1,amount);
					preparedstatement1.setLong(2, account_number);
					int rowaffected = preparedstatement1.executeUpdate();
					if(rowaffected>0) {
						System.out.println("Rs."+amount+"successfully credited");
						connection.commit();
						connection.setAutoCommit(true);
						
					}
					else {
						System.out.println("transcation faild");
						connection.rollback();
						connection.setAutoCommit(true);
					}
				}
				else {
					System.out.println("invalid security pin");
				}
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
  }
	
	public void debit_money(long account_number) throws SQLException {
		scanner.nextLine();
		System.out.println("Enter your amount");
		double amount = scanner.nextDouble();
        scanner.nextLine();
		System.out.println("Enter your security pin");
		String securitypin = scanner.nextLine();
		try {
			connection.setAutoCommit(false);
			if(account_number!=0) {
				PreparedStatement preparedstatement = connection.prepareStatement("select *from account where account_no = ? and security_pin = ?");
				preparedstatement.setLong(1, account_number);
				preparedstatement.setString(2, securitypin);
				ResultSet resultset = preparedstatement.executeQuery();
				if(resultset.next()) {
					double current_balance = resultset.getDouble("balance");
					if(amount<=current_balance) {
						String debit_query = "update account set balance = balance - ? where account_no = ?";
						PreparedStatement preparedstatement1 = connection.prepareStatement(debit_query);
						preparedstatement1.setDouble(1, amount);
						preparedstatement1.setLong(2, account_number);
						int rowaffected = preparedstatement1.executeUpdate();
						if(rowaffected>0) {
							System.out.println("Rs."+amount+"debit successfully");
							connection.commit();
							connection.setAutoCommit(true);
						}
						else {
			                 System.out.println("transcation faild");
			                 connection.rollback();
			                 connection.setAutoCommit(true);
						}
					}
					else {
						System.out.println("insufficient balance");
					}
					
				}
				else {
					System.out.println("invalid security pin");
				}
				
			}
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
	}
	
	
	public void transfer_money(Long sender_account_number) throws SQLException {
		scanner.nextLine();
		System.out.println("Enter your reciver account number");
		long  receiver_account_number = scanner.nextLong();
		System.out.println("Enter your amount");
	    double amount = scanner.nextDouble();
	    scanner.nextLine();
	    System.out.println("Enter your security pin");
	    
	    String securitypin = scanner.nextLine();
	    
	    try {
	    	connection.setAutoCommit(false);
	    	if(sender_account_number!=0 &&  receiver_account_number!=0) {
	    		PreparedStatement preparedstatement = connection.prepareStatement("select *from account where account_no  = ? and security_pin = ?");
	    		preparedstatement.setLong(1, sender_account_number);
	    		preparedstatement.setString(2, securitypin);
	    		ResultSet resultset = preparedstatement.executeQuery();
	    		if(resultset.next()) {
	    			double current_balance = resultset.getDouble("balance");
	    			if(amount<=current_balance) {
	    				 String debit_query = "update account set balance = balance - ? where account_no = ?";
	                     String credit_query = "update account set balance = balance + ? where account_no = ?";
	                     PreparedStatement prepared_debit = connection.prepareStatement(debit_query);
	                     PreparedStatement prepared_credit = connection.prepareStatement(credit_query);
	                     prepared_debit.setDouble(1, amount);
	                     prepared_debit.setLong(2, sender_account_number);
	                     prepared_credit.setDouble(1, amount);
	                     prepared_credit.setLong(2, receiver_account_number);
	                     int rowaffected1 = prepared_debit.executeUpdate();
	                     int rowaffected2 = prepared_credit.executeUpdate();
	                     if(rowaffected1 > 0 && rowaffected2 >0) {
	                    	 System.out.println("successfully transfer");
	                    	 System.out.println("Rs."+amount+" Transferred Successfully");
	                    	 connection.commit();
	                    	 connection.setAutoCommit(true);
	                     }
	                     else {
	                    	 System.out.println("Transcation failed");
	                    	 connection.rollback();
	                    	 connection.setAutoCommit(true);
	                     }
	                     
	    			}
	    			else {
	    				System.out.println("insufficient balance");
	    			}
	    		}
	    		else {
	    			System.out.println("invalid security pin");
	    		}
	    	}
	    	else {
	    		System.out.println("invalid account number");
	    	}
	    }
	    catch(SQLException e) {
	    	e.printStackTrace();
	    }
	    connection.setAutoCommit(true);
	}
	
	
	public void getbalance(long account_number) {
		scanner.nextLine();
		System.out.println("Enter your security pin");
		String securitypin = scanner.nextLine();
		try {
			PreparedStatement preparedstatement = connection.prepareStatement("select balance from account where account_no = ? and security_pin = ?");
			preparedstatement.setLong(1, account_number);
			preparedstatement.setString(2, securitypin);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				double balance = resultset.getDouble("balance");
				System.out.println("your balance is "+balance);
			}
			
			else {
				System.out.println("invalid pin");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
