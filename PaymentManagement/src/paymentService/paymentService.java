package paymentService;

import paymentModel.payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/payment")

public class paymentService {
	payment payObj = new payment();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readpayment()
	{

		return payObj.readpayment();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertpayment(@FormParam("accountNumber") String accountNumber,
							 @FormParam("bill_id") String bill_id,
							 @FormParam("amount") String amount,
							 @FormParam("date") String date)
	{
		String output = payObj.insertpayment(accountNumber, bill_id, amount, date);
		return output;
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatePayment(String paymentData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject payObject = new JsonParser().parse(paymentData).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String payment_id = payObject.get("payment_id").getAsString(); 
		String accountNumber = payObject.get("accountNumber").getAsString(); 
		String bill_id = payObject.get("bill_id").getAsString(); 
		String amount = payObject.get("amount").getAsString(); 
		String date = payObject.get("date").getAsString(); 
		
		String output = payObj.updatePayment(payment_id, accountNumber, bill_id, amount, date); 
		
		return output; 
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletepayment(String paymentData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String payment_id = doc.select("payment_id").text();
		
		String output = payObj.deletepayment(payment_id);
		
		return output;
	}

}
