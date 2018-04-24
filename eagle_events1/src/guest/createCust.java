package guest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class createCust {
	private int cid;
	private String cname;
	private Connection con;

	private String email;
	private String phone;
	
	public createCust(){}

	public createCust(int id, String name, String email,String phone, Connection con) {
		this.cid = id;
		this.cname = name;
		this.email = email;
		this.phone = phone;
		this.con = con;
	}

	public String getCustomerName(int id) {

		Connection con = null;
		String customername = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.google.gson.Gson");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.customer where id=" + id);
			ArrayList<Customer> custs = new ArrayList<>();
			if (rs.next()) {
				customername = rs.getString("name");
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customername;
	}

	public String insertCust() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate("INSERT INTO `eagle_events`.`customer` (`name`,`email`,phone) VALUES ('" + this.cname + "'"
					+ ",'"+ this.email + "','"+ this.phone + "')");
			return "Successfully stored data for " + this.cname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String updateCust() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate("UPDATE `eagle_events`.`customer` SET `name` = '" + this.cname + "', `email` = '"
					+ this.email + "',`phone` = '" + this.phone + "' WHERE `id` = " + this.cid);
			return "Successfully updated data for " + this.cname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String deleteCust() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate("DELETE FROM `eagle_events`.`customer` WHERE id=" + this.cid);
			return "Successfully deleted data for " + this.cname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}
