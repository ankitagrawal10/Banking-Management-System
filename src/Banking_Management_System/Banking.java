package Banking_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Banking {

	private static final String url = "jdbc:mysql://localhost:3306/banking";
	private static final String username = "root";
	private static final String password = "Ankit12345#";

	public static void main(String[] args) {
	     try {
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
	     }
	     catch(ClassNotFoundException e) {
	    	 System.out.println(e.getMessage());
	     }
	     
	     try {
	    	 Connection connection = DriverManager.getConnection(url,username,password);
	    	 Scanner scanner = new Scanner(System.in);
	    	 User user = new User(connection,scanner);
	    	 Account account = new Account(connection,scanner);
	    	 AccountManager accountmanager = new AccountManager(connection,scanner);
	    	 
	    	 String email;
	    	 long account_number;
	    	 
	    	 while(true) {
	    		 System.out.println("Welcome to Banking System");
	    		 System.out.println();
	    		 System.out.println("1. Register");
	    		 System.out.println("2. Login");
	    		 System.out.println("3. Delete already_exit user account");
	    		 System.out.println("4. Exit");
	    		 
	    		 System.out.println("Enter your choice");
	    		 int choice = scanner.nextInt();
	    		 switch(choice) {
	    			 case 1:
	    				 user.register();
	    				 break;
	    			 case 2:
	    				 email = user.login();
	    				 if(email!=null) {
	    					 System.out.println();    				
	    					 System.out.println("User logged in");
	    
	    					 if(!account.account_exit(email)) {
	    						 System.out.println();
		    					 System.out.println("1. open a new account");
		    					 
		    					 System.out.println("2. Exit");
		    					 if(scanner.nextInt() == 1) {
		    						 account_number = account.open_account(email);
		    						 System.out.println("Account created Successfully");
		    						 System.out.println("Your Account Number is: " + account_number);
		    					 }
		    					 
		    					 else {
		    						break;
		    					 }
		    					 
	    					 }
	    					 
	    					 account_number = account.getaccountnumber(email);
	    					 int choice1 = 0;
	    					 while(choice != 6) {
	    						 System.out.println();
	                                System.out.println("1. Debit Money");
	                                System.out.println("2. Credit Money");
	                                System.out.println("3. Transfer Money");
	                                System.out.println("4. Check Balance");
	                                System.out.println("5. Log Out");
	                                System.out.println("6. delete account");
	                                System.out.println("Enter your choice: ");
	                                choice1 = scanner.nextInt();
	                                
	                                switch(choice1) {
	                                
	                                case 1:
	                                	accountmanager.debit_money(account_number);
	                                	break;
	                                
	                                case 2:
	                                	accountmanager.credit_money(account_number);
	                                	break;
	                                	
	                                case 3:
	                                	accountmanager.transfer_money(account_number);
	                                    break;
	                                    
	                                case 4:
	                                	accountmanager.getbalance(account_number);
	                                	break;
	                                	
	                                case 5:
	                                	return;
	                                
	                                case 6:
	                                	account.account_delete();
	                                	break;
	                                	
	                                default:
                                        System.out.println("Enter Valid Choice!");
                                        break;
                                
	                                }   	
	    					 }
	    				 }
	    				 else {
	    					 System.out.println("incorrect email id or password");
	    				 }

	    			 case 3:
	    				 user.user_delete();
	    			
	    			 case 4:
	    				 System.out.println("Thankyou for using banking System");
	    				 return;
	    			
	    		 }
	    	 }
	     }
	     catch(Exception e) {
	    	 e.printStackTrace();
	     }
	}

}
