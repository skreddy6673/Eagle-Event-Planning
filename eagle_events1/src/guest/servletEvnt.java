package guest;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class servletEvnt
 */
@WebServlet("/servletEvnt")
public class servletEvnt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletEvnt() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.google.gson.Gson");
			con = DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.event");
			ArrayList<event> envts = new ArrayList<>();
			createCust createCust = new createCust();
			while (rs.next()) {
				event evnt = new event();
				evnt.setId(rs.getInt("id"));
				evnt.setName(rs.getString("name"));
				evnt.setMan(rs.getString("manager"));

				evnt.setDate(rs.getString("date"));
				evnt.setTime(rs.getString("time"));
				evnt.setVenue(rs.getString("venue"));
				evnt.setTablesize(rs.getInt("tablesize"));
				evnt.setChairspertable(rs.getInt("chairspertable"));
				
				evnt.setCustomername(createCust.getCustomerName(rs.getInt("customerid")));

				envts.add(evnt);
			}
			String json = new Gson().toJson(envts);
			response.setContentType("application/json");
			response.getWriter().write(json);
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap<String, String> data = new HashMap<>();
		String line = null;
		try {
			BufferedReader reader = request.getReader();

			if ((line = reader.readLine()) != null) {
				data.put(line.split(",")[0].split(":")[0].replace("\"", "").replace("{", ""),
						line.split(",")[0].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[1].split(":")[0].replace("\"", ""),
						line.split(",")[1].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[2].split(":")[0].replace("\"", ""),
						line.split(",")[2].split(":")[1].replace("\"", ""));

				data.put(line.split(",")[3].split(":")[0].replace("\"", ""),
						line.split(",")[3].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[4].split(":")[0].replace("\"", ""),
						line.split(",")[4].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[5].split(":")[0].replace("\"", ""),
						line.split(",")[5].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[6].split(":")[0].replace("\"", ""),
						line.split(",")[6].split(":")[1].replace("\"", ""));
				data.put(line.split(",")[7].split(":")[0].replace("\"", ""),
						line.split(",")[7].split(":")[1].replace("\"", ""));

				data.put(line.split(",")[8].split(":")[0].replace("\"", ""),
						line.split(",")[8].split(":")[1].replace("\"", ""));

				data.put(line.split(",")[9].split(":")[0].replace("\"", ""),
						line.split(",")[9].split(":")[1].replace("\"", "").replace("}", ""));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBConnection.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(data);
		if (data.get("method").equals("insert")) {
			createEvnt em = new createEvnt(Integer.parseInt(data.get("id")), data.get("name"), data.get("man"), con);

			event event = new event();
			event.setDate(data.get("date"));
			event.setTime(data.get("time"));
			event.setVenue(data.get("venue"));
			event.setTablesize(Integer.parseInt(data.get("tablesize")));
			event.setChairspertable(Integer.parseInt(data.get("chairspertable")));

			event.setCustomerid(Integer.parseInt(data.get("customerid")));

			event.setCustomername(data.get("customername"));
			em.setEvent(event);

			System.out.println(em.insertEvnt());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("update")) {
			createEvnt em = new createEvnt(Integer.parseInt(data.get("id")), data.get("name"), data.get("man"), con);

			event event = new event();
			event.setDate(data.get("date"));
			event.setTime(data.get("time"));
			event.setVenue(data.get("venue"));
			event.setTablesize(Integer.parseInt(data.get("tablesize")));
			event.setChairspertable(Integer.parseInt(data.get("chairspertable")));
			event.setCustomerid(Integer.parseInt(data.get("customerid")));

			em.setEvent(event);

			System.out.println(em.updateEvnt());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("delete")) {
			createEvnt em = new createEvnt(Integer.parseInt(data.get("id")), data.get("name"), data.get("man"), con);
			System.out.println(em.deleteEvnt());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
