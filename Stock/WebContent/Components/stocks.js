$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateStockForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidStockIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "StocksAPI",
		type : type,
		data : $("#formStock").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onStockSaveComplete(response.responseText, status);
		}
	});
});

function onStockSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divStocksGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidStockIDSave").val("");
	$("#formStock")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidStockIDSave").val(
					$(this).closest("tr").find('#hidStockIDUpdate').val());
			$("#sname").val($(this).closest("tr").find('td:eq(0)').text());
			$("#quantity").val($(this).closest("tr").find('td:eq(1)').text());
			$("#expDate").val($(this).closest("tr").find('td:eq(2)').text());
			$("#recDate").val($(this).closest("tr").find('td:eq(3)').text());
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "StocksAPI",
		type : "DELETE",
		data : "stockID=" + $(this).data("stockid"),
		dataType : "text",
		complete : function(response, status) {
			onStockDeleteComplete(response.responseText, status);
		}
	});
});
function onStockDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divStocksGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validateStockForm() {
	// name
	if ($("#sname").val().trim() == "") {
		return "Insert Stock Name.";
	}
	
	// is numerical value
	var tmpquantity = $("#quantity").val().trim();
	if (!$.isNumeric(tmpquantity)) {
		return "Insert a numerical value for quantity.";
	}
	
	// expDate
	if ($("#expDate").val().trim() == "") {
		return "Insert Expiry Date.";
	}
	// received Date-------------------------------
	if ($("#recDate").val().trim() == "") {
		return "Insert Recieved Date.";
	}
	
	
	return true;

}
