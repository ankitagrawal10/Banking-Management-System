package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

	private Connection connection;
	private Scanner scanner;
	
	public User(Connection connection ,Scanner scanner){
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void  register() {
		scanner.nextLine();
		System.out.println("please Enter your Full Name:");
		String fullName = scanner.nextLine();
		System.out.println("Please Enter your email id:");
		String email = scanner.nextLine();
		System.out.println("Please Enter your Password:");
		String password = scanner.nextLine();
		if(user_exit(email)) {
			System.out.println("Already have a account");
			return;
		}
		
		
		String register_query = "insert into user (Full_Name,email,password) values(?,?,?)";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(register_query);
			preparedstatement.setString(1, fullName);
			preparedstatement.setString(2, email);
			preparedstatement.setString(3, password);
			int affectedrow = preparedstatement.executeUpdate();
			if(affectedrow>0) {
				System.out.println("User Register Successfully");
			}
			else
			{
				System.out.println("faild to register please try again");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public String login() {
		scanner.nextLine();
		System.out.println("Enter your email:");
		String email = scanner.nextLine();
		System.out.println("Enter your Password:");
		String password = scanner.nextLine();
		
		String login_query = "select *from user where email = ? and password = ?";
		try {
			
			PreparedStatement preparedstatement = connection.prepareStatement(login_query);
			preparedstatement.setString(1, email);
			preparedstatement.setString(2, password);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return email;
			}
			return null;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean user_exit(String email) {
		String exit = "select *from user where email = ?";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(exit);
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
	
	public int user_delete() {
		scanner.nextLine();
		System.out.println("Enter your email");
		String email = scanner.nextLine();
		System.out.println("Enter your Password");
		String password = scanner.nextLine();
		String deletequery = "delete from user where email = ? and password = ?";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(deletequery);
			preparedstatement.setString(1, email);
			preparedstatement.setString(2, password);
			int rowaffected = preparedstatement.executeUpdate();
			if(rowaffected>0) {
				System.out.println("Successfully deleted");
			}
			else {
				System.out.println("user account does not exist");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
