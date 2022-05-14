package cardService;

import cardModel.cardDetails;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/card")

public class cardService {
	cardDetails cardObj = new cardDetails();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readcardDetails()
	{

		return cardObj.readcardDetails();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertcardDetails(@FormParam("cardNumber") String cardNumber,
							 @FormParam("cardHolderName") String cardHolderName,
							 @FormParam("ExpirationDate") String ExpirationDate,
							 @FormParam("vcode") String vcode)
	{
		String output = cardObj.insertcardDetails(cardNumber, cardHolderName, ExpirationDate, vcode);
		return output;
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatecardDetails(String cardData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject cardObject = new JsonParser().parse(cardData).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String cardID = cardObject.get("cardID").getAsString(); 
		String cardNumber = cardObject.get("cardNumber").getAsString(); 
		String cardHolderName = cardObject.get("cardHolderName").getAsString(); 
		String ExpirationDate = cardObject.get("ExpirationDate").getAsString(); 
		String vcode = cardObject.get("vcode").getAsString(); 
		
		String output = cardObj.updatecardDetails(cardID, cardNumber, cardHolderName,ExpirationDate, vcode); 
		
		return output; 
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletecardDetails(String cardData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(cardData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String cardID = doc.select("cardID").text();
		
		String output = cardObj.deletecardDetails(cardID);
		
		return output;
	}

}

