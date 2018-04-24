package guest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guest.DBConnection;
import guest.model.Guest;
import guest.model.GuestDetails;
import guest.model.GuestInfo;
import guest.model.Guestpreferences;

public class GuestDao {

	public void deleteGuest(int eventid, int guestid) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			st.executeUpdate(
					"DELETE FROM `eagle_events`.`guest` WHERE guestid=" + guestid + " and eventid=" + eventid);
			con.close();

			GuestpreferencesDao guestpreferencesDao = new GuestpreferencesDao();
			guestpreferencesDao.deletePreferences(eventid, guestid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int findnextGuestid(int eventid) {

		Connection con = null;
		int guestid = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM `guest` WHERE eventid = " + eventid + " order by guestid desc limit 1");
			if (rs.next()) {
				guestid = rs.getInt("guestid");
			}
			con.close();
			return (guestid + 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;

	}

	public void updateGuest(Guest guest) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DBConnection.getConnection();

			Statement st = con.createStatement();
			st.executeUpdate("UPDATE `eagle_events`.`guest` SET `firstname` = '" + guest.getFirstname()
					+ "', `lastname` = '" + guest.getLastname() + "' WHERE `guestid` = " + guest.getGuestid()
					+ " and eventid=" + guest.getEventid());

			GuestpreferencesDao guestpreferencesDao = new GuestpreferencesDao();
			guestpreferencesDao.deletePreferences(guest.getEventid(), guest.getGuestid());

			List<Guestpreferences> guestpreferences = guest.getGuestpreferences();
			for (Guestpreferences guestpreference : guestpreferences) {
				String insertQuery1 = "INSERT INTO guestpreferences(gid,ogid,preferred,eventid)  VALUES (?, ?,?,?)";
				PreparedStatement preparedStatement1 = con.prepareStatement(insertQuery1);
				preparedStatement1.setLong(1, guestpreference.getGid());
				preparedStatement1.setLong(2, guestpreference.getOgid());
				preparedStatement1.setShort(3, guestpreference.getPreferred());
				preparedStatement1.setInt(4, guestpreference.getEventid());
				preparedStatement1.executeUpdate();
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addGuest(Guest guest) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DBConnection.getConnection();
			String insertQuery = "INSERT INTO guest(guestid,firstname,lastname,eventid)  VALUES (?, ?,?,?)";
			PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setInt(1, guest.getGuestid());
			preparedStatement.setString(2, guest.getFirstname());
			preparedStatement.setString(3, guest.getLastname());
			preparedStatement.setInt(4, guest.getEventid());

			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {

				List<Guestpreferences> guestpreferences = guest.getGuestpreferences();
				for (Guestpreferences guestpreference : guestpreferences) {
					String insertQuery1 = "INSERT INTO guestpreferences(gid,ogid,preferred,eventid)  VALUES (?, ?,?,?)";
					PreparedStatement preparedStatement1 = con.prepareStatement(insertQuery1);
					preparedStatement1.setLong(1, guestpreference.getGid());
					preparedStatement1.setLong(2, guestpreference.getOgid());
					preparedStatement1.setShort(3, guestpreference.getPreferred());
					preparedStatement1.setInt(4, guestpreference.getEventid());
					preparedStatement1.executeUpdate();
				}
			}

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	public GuestInfo readbyguestidandeventid(int eventid, int guestid) {

		GuestInfo guestInfo = new GuestInfo();
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM eagle_events.guest where eventid=" + eventid + " and guestid=" + guestid);
			if (rs.next()) {

				Guest guest = new Guest();
				guest.setEventid(eventid);
				guest.setFirstname(rs.getString("firstname"));
				guest.setLastname(rs.getString("lastname"));
				guest.setGuestid(rs.getInt("guestid"));
				guest.setName(rs.getString("firstname") + " " + rs.getString("lastname"));

				Statement st1 = con.createStatement();

				ResultSet rs1 = st1.executeQuery(
						"SELECT guest.guestid,guest.firstname,guest.lastname,guestpreferences.preferred FROM eagle_events.guestpreferences,guest where guestpreferences.eventid=guest.eventid and guestpreferences.ogid=guest.guestid and  guestpreferences.eventid="
								+ eventid + " and guestpreferences.gid=" + guestid);

				List<Guest> sitwithGuests = new ArrayList<>();
				List<Guest> notsitwithGuests = new ArrayList<>();

				while (rs1.next()) {
					short preferred = rs1.getShort("preferred");
					Guest guestpreference = new Guest();
					guestpreference.setGuestid(rs1.getInt("guestid"));
					guestpreference.setFirstname(rs1.getString("firstname"));
					guestpreference.setLastname(rs1.getString("lastname"));

					guestpreference.setName(rs1.getString("firstname") + " " + rs1.getString("lastname"));

					if (preferred == 1) {
						sitwithGuests.add(guestpreference);

					} else {
						notsitwithGuests.add(guestpreference);

					}
				}

				guestInfo.setGuest(guest);
				guestInfo.setSitWith(sitwithGuests);
				guestInfo.setNotsitWith(notsitwithGuests);
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guestInfo;

	}

	public Map<Integer, GuestDetails> readGuestswithPreferences(int eventid) {

		Map<Integer, GuestDetails> map = new HashMap<>();
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.guest where eventid=" + eventid);
			while (rs.next()) {

				Guest guest = new Guest();
				guest.setEventid(eventid);
				guest.setFirstname(rs.getString("firstname"));
				guest.setLastname(rs.getString("lastname"));
				guest.setGuestid(rs.getInt("guestid"));
				int guestid = rs.getInt("guestid");

				Statement st1 = con.createStatement();

				GuestDetails guestDetails = new GuestDetails();
				ResultSet rs1 = st1.executeQuery(
						"SELECT * FROM eagle_events.guestpreferences where eventid=" + eventid + " and gid=" + guestid);
				List<Integer> hatedsittig = new ArrayList<>();
				List<Integer> lovesitting = new ArrayList<>();

				while (rs1.next()) {
					short preferred = rs1.getShort("preferred");
					if (preferred == 1) {
						lovesitting.add(rs1.getInt("ogid"));

					} else {
						hatedsittig.add(rs1.getInt("ogid"));
					}
				}

				guestDetails.setHatedsittig(hatedsittig);
				guestDetails.setLovesitting(lovesitting);
				guestDetails.setGuest(guest);

				map.put(guestid, guestDetails);
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}

	public List<Guest> readGuests(int eventid) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.guest where eventid=" + eventid);
			ArrayList<Guest> guests = new ArrayList<>();
			while (rs.next()) {
				Guest guest = new Guest();
				guest.setFirstname(rs.getString("firstname"));
				guest.setLastname(rs.getString("lastname"));
				guest.setId(rs.getInt("id"));
				guest.setGuestid(rs.getInt("guestid"));
				guest.setName(rs.getString("firstname") + " " + rs.getString("lastname"));
				guests.add(guest);
			}

			con.close();

			return guests;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int addGuests(List<Guest> guests) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO guest(id,guestid,firstname,lastname,eventid)  VALUES (?, ?,?,?,?)");
			for (Guest guest : guests) {
				ps.setLong(1, guest.getId());
				ps.setInt(2, guest.getGuestid());
				ps.setString(3, guest.getFirstname());
				ps.setString(4, guest.getLastname());
				ps.setInt(5, guest.getEventid());

				ps.addBatch();
			}
			ps.executeBatch();

			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}
		return 1;
	}

}
