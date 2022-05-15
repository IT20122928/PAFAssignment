package cardService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class cardDetails {
	
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
			public String readcardDetails() { 
				String output = ""; 
				try { 
					Connection con = connect(); 
					if (con == null) {
						return "Error while connecting to the database for reading.";
					} 
						
					// Prepare the HTML table to be displayed
					output = "<table class='table' border='1'><tr>"
							+ "<th>Card ID</th>"
							+ "<th>cardNumber</th>" 
							+ "<th>cardHolderName</th>" 
							+ "<th>ExpirationDate</th>"
							+ "<th>vcode</th>"
							+ "<th>Update</th>"
							+ "<th>Remove</th></tr>"; 
					 
					String query = "select * from carddetails"; 
					Statement stmt = con.createStatement(); 
					ResultSet rs = stmt.executeQuery(query); 
					
					// iterate through the rows in the result set
					while (rs.next()) { 
						int cardID = rs.getInt("cardID");
						String cardNumber = rs.getString("cardNumber");
						String cardHolderName = rs.getString("cardHolderName");
						String ExpirationDate = rs.getString("ExpirationDate");
						String vcode= rs.getString("vcode");
						
						// Add into the HTML table
						output += "<tr><td>" + cardID + "</td>";
						output += "<td>" + cardNumber + "</td>";
						output += "<td>" + cardHolderName + "</td>";
						output += "<td>" + ExpirationDate+ "</td>";
						output += "<td>" + vcode + "</td>";
						
						output += "<td><input name='btnUpdate' type='button' value='Update' "
								+ "class='btnUpdate btn btn-secondary' data-itemid='" +cardID  + "'></td>"
								+ "<td><input name='btnRemove' type='button' value='Remove' "
								+ "class='btnRemove btn btn-danger' data-itemid='" + cardID  + "'></td></tr>"; 
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
			public String insertcardDetails(String cardNumber, String cardHolderName, String ExpirationDate, String vcode)
			{
				String output = "";
				
				try { 
					Connection con = connect(); 
					if (con == null) {
						return "Error while connecting to the database for inserting.";
					}
					
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
					String newCard  = readcardDetails();
					output = "{\"status\":\"success\", \"data\": \"" + newCard  + "\"}"; 
				} 
				catch (Exception e) { 
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the Card Details\"}"; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
			} 
			
			
			//Update Payment
			public String  updatecardDetails(String cardID, String cardNumber, String cardHolderName, String ExpirationDate, String vcode){ 
				String output = ""; 
				try { 
					Connection con = connect(); 
					if (con == null) {
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
					String newCard  = readcardDetails();
					output = "{\"status\":\"success\", \"data\": \"" +  newCard + "\"}"; 
				} 
				catch (Exception e) { 
					output = "{\"status\":\"error\", \"data\": \"Error while updating the card details.\"}"; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
			}
			
			
			//Delete Payment
			public String deletecardDetails(String  cardID) { 
				String output = ""; 		
				try { 
					Connection con = connect(); 
					if (con == null) {
						return "Error while connecting to the database for deleting.";
					} 
					
					// create a prepared statement
					String query = "delete from carddetails where cardID=?";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(cardID));
					
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					String newCard  = readcardDetails();
					output = "{\"status\":\"success\", \"data\": \"" +newCard + "\"}"; 
				}
				catch (Exception e) 
				{ 
					output = "{\"status\":\"error\", \"data\": \"Error while deleting Payment ID.\"}"; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
			} 
}
