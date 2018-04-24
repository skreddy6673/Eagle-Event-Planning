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
 * Servlet implementation class servletCust
 */
@WebServlet("/servletCust")
public class servletCust extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletCust() {
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
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.customer");
			ArrayList<Customer> custs = new ArrayList<>();
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setCid(rs.getInt("id"));
				cust.setCname(rs.getString("name"));
				cust.setEvnt(rs.getString("event"));
				
				cust.setEmail(rs.getString("email"));
				cust.setPhone(rs.getString("phone"));
				
				custs.add(cust);
			}
			String json = new Gson().toJson(custs);
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
						line.split(",")[4].split(":")[1].replace("\"", "").replace("}", ""));

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
		if (data.get("method").equals("insert")) {
			createCust em = new createCust(Integer.parseInt(data.get("id")), data.get("name"), data.get("email"),data.get("phone"), con);
			System.out.println(em.insertCust());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("update")) {
			createCust em = new createCust(Integer.parseInt(data.get("id")), data.get("name"), data.get("email"),data.get("phone"), con);
			System.out.println(em.updateCust());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("delete")) {
			createCust em = new createCust(Integer.parseInt(data.get("id")), data.get("name"), data.get("email"),data.get("phone"), con);
			System.out.println(em.deleteCust());
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
