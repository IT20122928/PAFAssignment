package cardModel;

import java.sql.Connection;

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

public class cardDetails {
	
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
		
		public String insertcardDetails(String cardNumber, String cardHolderName, String ExpirationDate, String vcode)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{return "Error while connecting to the database for inserting."; }
				
				// create a prepared statement
				String query = " insert into carddetails(`cardID`,`cardNumber`,`cardHolderName`,`ExpirationDate`,`vcode`)"+ " values (?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, cardNumber);
				preparedStmt.setString(3, cardHolderName);
				preparedStmt.setString(4, ExpirationDate);
				preparedStmt.setString(5, vcode);
				
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
		public String readcardDetails()
		{
			String output = "";
			
			try
			{
					Connection con = connect();
					
					if (con == null)
					{return "Error while connecting to the database for reading."; }
		
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>card ID</th><th>card Number</th>" +
							"<th>cardHolder Name</th>" +
							"<th>Expiration Date</th>" +
							"<th>Verification Code</th>" +
							"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from carddetails";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					// iterate through the rows in the result set
					while (rs.next())
					{
						int cardID = rs.getInt("cardID");
						String cardNumber = rs.getString("cardNumber");
						String cardHolderName = rs.getString("cardHolderName");
						String ExpirationDate = rs.getString("ExpirationDate");
						String vcode= rs.getString("vcode");
						
						// Add into the html table
						output += "<tr><td>" + cardID + "</td>";
						output += "<td>" + cardNumber + "</td>";
						output += "<td>" + cardHolderName + "</td>";
						output += "<td>" + ExpirationDate+ "</td>";
						output += "<td>" + vcode + "</td>";
		
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" 
								+ "<td><form method='post' action='items.jsp'>" 
								+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
								+ "<input name='cardID' type='hidden' value='" + cardID
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
		
		//Method to update card details
				public String updatecardDetails(String cardID, String cardNumber, String cardHolderName, String ExpirationDate, String vcode){ 
					String output = ""; 
					try
					{ 
						Connection con = connect(); 
						if (con == null) 
						{
							return "Error while connecting to the database for updating.";
						} 
						// create a prepared statement
						String query = "UPDATE carddetails SET cardNumber=?,cardHolderName=?,ExpirationDate=?,vcode=? WHERE cardID=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						
						// binding values
						preparedStmt.setString(1, cardNumber); 
						preparedStmt.setString(2, cardHolderName); 
					 	preparedStmt.setString(3, ExpirationDate); 
					 	preparedStmt.setString(4, vcode); 
					 	preparedStmt.setInt(5, Integer.parseInt(cardID)); 
					 	
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
				public String deletecardDetails(String  cardID)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{return "Error while connecting to the database for deleting."; }
				
				// create a prepared statement
				String query = "delete from carddetails where cardID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(cardID));
				
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



