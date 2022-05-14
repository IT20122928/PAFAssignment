package paymentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class payment {
		
		//Database Connection
		private Connection connect() { 
			Connection con = null; 
			try { 
				Class.forName("com.mysql.cj.jdbc.Driver"); 
			 
				//Provide the correct details: DBServer/DBName, user-name, password 
				String url = "jdbc:mysql://127.0.0.1:3306/pafproject";
				String user = "root";
				String password = "";
				con = DriverManager.getConnection(url, user, password); 
			} 
			catch (Exception e) {
				e.printStackTrace();
			} 
			return con; 
		} 
		
		//display Payment Data
		public String readpayment() { 
			String output = ""; 
			try { 
				Connection con = connect(); 
				if (con == null) {
					return "Error while connecting to the database for reading.";
				} 
					
				// Prepare the HTML table to be displayed
				output = "<table class='table' border='1'><tr>"
						+ "<th>Payment ID</th>"
						+ "<th>Account Number</th>" 
						+ "<th>bill ID</th>" 
						+ "<th>Amount</th>"
						+ "<th>Date</th>"
						+ "<th>Update</th>"
						+ "<th>Remove</th></tr>"; 
				 
				String query = "select * from payment_db"; 
				Statement stmt = con.createStatement(); 
				ResultSet rs = stmt.executeQuery(query); 
				
				// iterate through the rows in the result set
				while (rs.next()) { 
					int payment_id = rs.getInt("payment_id");
					String accountNumber = rs.getString("accountNumber");
					String bill_id = rs.getString("bill_id");
					String amount = Double.toString(rs.getDouble("amount"));
					String date = rs.getString("date");
					
					// Add into the HTML table
					output += "<tr><td>" + payment_id + "</td>";
					output += "<td>" + accountNumber + "</td>";
					output += "<td>" + bill_id + "</td>";
					output += "<td>" + amount+ "</td>";
					output += "<td>" + date + "</td>";
					
					output += "<td><input name='btnUpdate' type='button' value='Update' "
							+ "class='btnUpdate btn btn-secondary' data-itemid='" +payment_id  + "'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' "
							+ "class='btnRemove btn btn-danger' data-itemid='" + payment_id  + "'></td></tr>"; 
				} 
				
				con.close(); 
				
				// Complete the HTML table
				output += "</table>"; 
			} 
			catch (Exception e) { 
				output = "Error while reading.";
				System.err.println(e.getMessage()); 
			} 
			return output; 
		} 
		
		
		
		//Insert Payment
		public String insertpayment(String accountNumber, String bill_id, String amount, String date)
		{
			String output = "";
			
			try { 
				Connection con = connect(); 
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				
				// create a prepared statement
				String query = " insert into payment_db(`payment_id`,`accountNumber`,`bill_id`,`amount`,`date`)"+ " values (?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query); 

				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, accountNumber);
				preparedStmt.setString(3, bill_id);
				preparedStmt.setDouble(4, Double.parseDouble(amount));
				preparedStmt.setString(5, date);
				
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String newPayments = readpayment();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			} 
			catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the Account Number\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		} 
		
		
		//Update Payment
		public String  updatePayment(String payment_id, String accountNumber, String bill_id, String amount, String date){ 
			String output = ""; 
			try { 
				Connection con = connect(); 
				if (con == null) {
					return "Error while connecting to the database for updating.";
				} 
				
				// create a prepared statement
				String query = "UPDATE payment_db SET accountNumber=?,bill_id=?,amount=?,date=? WHERE payment_id=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				// binding values
				preparedStmt.setString(1, accountNumber); 
				preparedStmt.setString(2, bill_id); 
			 	preparedStmt.setString(3, amount); 
			 	preparedStmt.setString(4, date); 
			 	preparedStmt.setInt(5, Integer.parseInt(payment_id)); 
			 					
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String  newPayments = readpayment();
				output = "{\"status\":\"success\", \"data\": \"" +  newPayments + "\"}"; 
			} 
			catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while updating the bill type.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		}
		
		
		//Delete Payment
		public String deletepayment(String payment_id) { 
			String output = ""; 		
			try { 
				Connection con = connect(); 
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				} 
				
				// create a prepared statement
				String query = "delete from payment_db where payment_id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, payment_id);
				
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String newPayments = readpayment();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			}
			catch (Exception e) 
			{ 
				output = "{\"status\":\"error\", \"data\": \"Error while deleting Payment ID.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		} 

	}
