<%@page import="cardService.cardDetails"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/Payment.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>

	<div class="container"><div class="row"><div class="col-sm">
	<div style="margin-bottom: 50px; margin-top: 30px;">
		<div id="aligning-r"><form class="form-inline">
	    	<input class="form-control mr-sm-2" type="search" name="search" id="search" placeholder="Search" aria-label="Search">
	    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	  	</form></div></div>
	  	
		<h1>Card Details</h1>
		<hr>
		<br>
		
		<form id="formItem" name="formItem">
		
				<div class="col-sm">
					Card ID: 
					<input type="text" id="payment_id" name="payment_id" class="form-control form-control-sm">
				</div>
				
				<div class="col-sm">
					Card Number : 
					<input type="text" id="cardNumber" name="cardNumber" class="form-control form-control-sm">
				</div>
		
			
				<div class="col-sm">
					Card Holder Name : 
					<input type="text" id="cardHolderName" name="cardHolderName" class="form-control form-control-sm">
				</div>

				<div class="col-sm">
					Expiration Date : 
					<input type="text" id="ExpirationDate" name="ExpirationDate" class="form-control form-control-sm">
				</div>
				
				<div class="col-sm">
				
					VCode :
					<input type="text" id="vcode" name="vcode" class="form-control form-control-sm">
				</div>
			
			<br>
			<input type="hidden" id="hidItemIDSave" name="hidItemIDSave">
			<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">			
		</form>
		<br>
		
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		
		<br>
		
		<div id="divItemsGrid">
			<%
			 	cardDetails cardObj = new cardDetails(); 
				out.print(cardObj.readcardDetails()); 
			%>
		</div>
	</div></div></div>
</body>
</html>