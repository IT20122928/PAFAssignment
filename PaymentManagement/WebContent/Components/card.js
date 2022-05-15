function onItemSaveComplete(response, status) 
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully saved."); 
			$("#alertSuccess").show(); 
			$("#divItemsGrid").html(resultSet.data); 
	 	} 
		else if (resultSet.status.trim() == "error") 
	 	{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} 
	else if (status == "error") 
	{ 
		$("#alertError").text("Error while saving."); 
		$("#alertError").show(); 
	} 
	else
	{ 
		$("#alertError").text("Unknown error while saving.."); 
		$("#alertError").show(); 
	}
	$("#hidItemIDSave").val(""); 
	$("#formItem")[0].reset(); 
}


function onItemDeleteComplete(response, status) 
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully deleted."); 
			$("#alertSuccess").show(); 
			$("#divItemsGrid").html(resultSet.data); 
		} else if (resultSet.status.trim() == "error") 
		{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} else if (status == "error") 
	{ 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	} 
}


$(document).ready(function() 
{ 
	if ($("#alertSuccess").text().trim() == "") 
	{ 
		$("#alertSuccess").hide(); 
	} 
	$("#alertError").hide(); 
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
	// Clear alerts---------------------
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide(); 
	// Form validation-------------------
	var status = validateItemForm(); 
	if (status != true) 
	{ 
		$("#alertError").text(status); 
		$("#alertError").show(); 
		return; 
	} 
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
	$.ajax( 
	{ 
		url : "PaymentAPI", 
		type : type, 
		data : $("#formItem").serialize(), 
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onItemSaveComplete(response.responseText, status); 
		} 
	});  
}); 


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 		
	$("#cardID").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#cardNumber").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#cardHolderName").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#ExpirationDate").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#vcode").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#hidItemIDSave").val($(this).closest("tr").find('td:eq(0)').text()); 
}); 


// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event) 
{ 
	$.ajax( 
	{ 
		url : "CardAPI", 
		type : "DELETE", 
		data : "type=" + $(this).data("cardID"),
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onItemDeleteComplete(response.responseText, status); 
		} 
	}); 
});


// CLIENT-MODEL================================================================
function validateItemForm() 
{ 
	// Card Number
	if ($("#cardNumber").val().trim() == "") 
	{ 
		return "Insert Card Number."; 
	} 


	// Card Holder Name
	if ($("#cardHolderName").val().trim() == "") 
	{ 
		return "Insert Card Holder Name."; 
	} 
	
	// Expiration Date
	if ($("#ExpirationDate").val().trim() == "") 
	{ 
		return "Insert Expiration Date."; 
	} 
	
	// Verification Code
	if ($("#vcode").val().trim() == "") 
	{ 
		return "Insert Verification Code."; 
	} 

	return true; 
}





