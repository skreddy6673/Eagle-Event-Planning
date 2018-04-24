package guest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import guest.DBConnection;
import guest.createCust;
import guest.event;

public class EventDao {

	public event readEvent(int eventid) {
		Connection con = null;
		event event = new event();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.event where id=" + eventid);
			if (rs.next()) {

				event.setName(rs.getString("name"));
				event.setDate(rs.getString("date"));
				event.setVenue(rs.getString("venue"));
				event.setTablesize(rs.getInt("tablesize"));
				event.setChairspertable(rs.getInt("chairspertable"));

				createCust createCust = new createCust();
				String customername = createCust.getCustomerName(rs.getInt("customerid"));
				event.setCustomername(customername);

			}

			con.close();

			return event;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
