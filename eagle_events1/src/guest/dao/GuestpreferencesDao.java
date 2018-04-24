package guest.dao;

import java.sql.Connection;
import java.sql.Statement;

import guest.DBConnection;

public class GuestpreferencesDao {

	public void deletePreferences(int eventid, int guestid) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("DELETE FROM `eagle_events`.`guestpreferences` WHERE (gid=" + guestid + " or ogid = "
					+ guestid + ") and eventid=" + eventid);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
