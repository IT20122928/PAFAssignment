package paymentModel;

import java.sql.Connection;
import paymentModel.payment;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

public class payment {
	
	//A common method to connect to the DB
		private Connection connect()
		{
			Connection con = null;
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafproject", "root", "");
			}
			catch (Exception e)
			{e.printStackTrace();}
			
			return con;
		}
		
		public String insertpayment(String accountNumber, String bill_id, String amount, String date)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{return "Error while connecting to the database for inserting."; }
				
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
				
				output = "Inserted successfully";
			}
			catch (Exception e)
			{
				output = "Error while inserting .";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//display data
		public String readpayment()
		{
			String output = "";
			
			try
			{
					Connection con = connect();
					
					if (con == null)
					{return "Error while connecting to the database for reading."; }
		
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>Payment ID</th><th>Account Number</th>" +
							"<th>Amount</th>" +
							"<th>bill ID</th>" +"<th>Date</th>" +
							"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from payment_db";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					// iterate through the rows in the result set
					while (rs.next())
					{
						int payment_id = rs.getInt("payment_id");
						String accountNumber = rs.getString("accountNumber");
						String bill_id = rs.getString("bill_id");
						String amount = Double.toString(rs.getDouble("amount"));
						String date = rs.getString("date");
						
						// Add into the html table
						output += "<tr><td>" + payment_id + "</td>";
						output += "<td>" + accountNumber + "</td>";
						output += "<td>" + bill_id + "</td>";
						output += "<td>" + amount+ "</td>";
						output += "<td>" + date + "</td>";
		
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" 
								+ "<td><form method='post' action='items.jsp'>" 
								+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
								+ "<input name='itemID' type='hidden' value='" + payment_id
								+ "'>" + "</form></td></tr>";
					}
					
					con.close();
					
					// Complete the html table
					output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//Method to update user payment details
		public String updatePayment(String payment_id, String accountNumber, String bill_id, String amount, String date){ 
					String output = ""; 
					try
					{ 
						Connection con = connect(); 
						if (con == null) 
						{
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
					 	output = "Updated successfully"; 
					} 
					catch (Exception e) 
					{ 
						output = "Error while updating."; 
						System.err.println(e.getMessage()); 
					} 
				 		return output; 
				 }

		
			//Method to delete
			public String deletepayment(String  payment_id)
				{
					String output = "";
			
					try
					{
						Connection con = connect();
						
						if (con == null)
						{return "Error while connecting to the database for deleting."; }
						
						// create a prepared statement
						String query = "delete from payment_db where payment_id=?";
						
						PreparedStatement preparedStmt = con.prepareStatement(query);
						
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(payment_id));
						
						// execute the statement
						preparedStmt.execute();
						con.close();
						
						output = "Deleted successfully";
					}
					catch (Exception e)
					{
						output = "Error while deleting.";
						System.err.println(e.getMessage());
					}
			
			return output;
		}

}



