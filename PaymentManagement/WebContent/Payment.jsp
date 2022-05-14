<%@page import="paymentService.payment"%>

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
	  	
		<h1>Payment Management..</h1>
		<hr>
		<br>
		
		<form id="formItem" name="formItem">
		
				<div class="col-sm">
					Payment ID: 
					<input type="text" id="payment_id" name="payment_id" class="form-control form-control-sm">
				</div>
				
				<div class="col-sm">
					Account Number: 
					<input type="text" id="accountNumber" name="accountNumber" class="form-control form-control-sm">
				</div>
		
			
				<div class="col-sm">
					Bill Id: 
					<input type="text" id="bill_id" name="bill_id" class="form-control form-control-sm">
				</div>

				<div class="col-sm">
					Amount
					<input type="text" id="amount" name="amount" class="form-control form-control-sm">
				</div>
				
				<div class="col-sm">
				
					Date:
					<input type="text" id="date" name="date" class="form-control form-control-sm">
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
				payment payObj = new payment(); 
				out.print(payObj.readpayment()); 
			%>
		</div>
	</div></div></div>
</body>
</html>