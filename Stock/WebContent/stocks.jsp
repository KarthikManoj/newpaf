<%@page import="model.Stock"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<head>
<meta charset="ISO-8859-1">
<title>Stock Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/stocks.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Stock Service</h1>
				<form id="formStock" name="formStock">
					sname: <input id="sname" name="sname" type="text"
						class="form-control form-control-sm"> <br>
					 quantity: <input id="quantity" name="quantity" type="number"
						class="form-control form-control-sm"> <br>
					 expDate: <input id="expDate" name="expDate" type="text"
						class="form-control form-control-sm"> <br>
				     recDate: 
				     <input id="recDate" name="recDate" type=text" class="form-control form-control-sm"> <br>
					 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary">
					<input type="hidden"id="hidStockIDSave" name="hidStockIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
                <div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divStocksGrid">
					<%
						Stock stockObj = new Stock();
						out.print(stockObj.readStocks());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>