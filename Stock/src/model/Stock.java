package model;

import java.sql.*;

public class Stock {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pafnew", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertStock(String sname, String quantity, String expDate, String recDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into stocks (`stockID`,`sname`,`quantity`,`expDate`,`recDate`)" 
			+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, sname);
			preparedStmt.setString(3, quantity);
			preparedStmt.setString(4, expDate);
			preparedStmt.setString(5, recDate);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newStocks = readStocks();
			output = "{\"status\":\"success\", \"data\": \"" + newStocks + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the stock.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readStocks() {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th> sname</th> <th>quantity</th><th>expDate</th>"
					+ "<th>recDate</th> <th>Update</th><th>Remove</th></tr>";
			String query = "select * from stocks";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String stockID = Integer.toString(rs.getInt("stockID"));
				String sname = rs.getString("sname");
				String quantity = rs.getString("quantity");
				String expDate = rs.getString("expDate");
				String recDate = rs.getString("recDate");
				
				// Add into the html table
				output += "<tr><td><input id='hidStockIDUpdate' name='hidStockIDUpdate' type='hidden' value='" + stockID
						+ "'>" + sname + "</td>";
				output += "<td>" + quantity + "</td>";
				output += "<td>" + expDate + "</td>";
				output += "<td>" + recDate + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-stockid='"
						+ stockID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the stocks.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateStock(String ID, String sname, String quantity, String expDate, String recDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE stocks SET sname=?,quantity=?,expDate=?,recDate=? WHERE stockID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, sname);
			preparedStmt.setString(2, quantity);
			preparedStmt.setString(3, expDate);
			preparedStmt.setString(4, recDate);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newStocks = readStocks();
			output = "{\"status\":\"success\", \"data\": \"" + newStocks + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Stocks.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteStock(String stockID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from stocks where stockID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(stockID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newStocks = readStocks();
			output = "{\"status\":\"success\", \"data\": \"" + newStocks + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the stocks.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}