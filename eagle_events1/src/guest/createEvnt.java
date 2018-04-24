package guest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createEvnt {
	private int evid;
	private String evname, man;
	private Connection con;

	private event event;

	public event getEvent() {
		return event;
	}

	public void setEvent(event event) {
		this.event = event;
	}

	public createEvnt(int id, String name, String man, Connection con) {
		this.evid = id;
		this.evname = name;
		this.man = man;
		this.con = con;
	}

	public String insertEvnt() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate(
					"INSERT INTO `eagle_events`.`event` (`name`, `manager`,date,time,venue,tablesize,chairspertable,customerid) VALUES ('"
							+ this.evname + "','" + this.man + "','" + event.getDate() + "','" + event.getTime() + "','"
							+ event.getVenue() + "'," + event.getTablesize() + "," + event.getChairspertable() + ","
							+ event.getCustomerid() + ")");
			return "Successfully stored data for " + this.evname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String updateEvnt() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate("UPDATE `eagle_events`.`event` SET `name` = '" + this.evname + "', `manager` = '"
					+ this.man + "',date='" + event.getDate() + "',time='" + event.getTime() + "',venue='"
					+ event.getVenue() + "'," + "tablesize='" + event.getTablesize() + "',chairspertable='"
					+ event.getChairspertable() + "' ,customerid=" + event.getCustomerid() + " WHERE `id` = "
					+ this.evid);
			return "Successfully updated data for " + this.evname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String deleteEvnt() {
		try {
			Statement st = this.con.createStatement();
			st.executeUpdate("DELETE FROM `eagle_events`.`event` WHERE id=" + this.evid);
			return "Successfully deleted data for " + this.evname;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}
