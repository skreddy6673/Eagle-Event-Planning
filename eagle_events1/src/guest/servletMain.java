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
 * Servlet implementation class servletMain
 */
@WebServlet("/servletMain")
public class servletMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletMain() {
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
			ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.employee");
			ArrayList<Employee> emps = new ArrayList<>();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEid(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSal(rs.getInt("salary"));
				emp.setDesig(rs.getString("designation"));
				emp.setPass(rs.getString("password"));
				emp.setEmail(rs.getString("email"));
				emps.add(emp);
			}
			String json = new Gson().toJson(emps);
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
						line.split(",")[6].split(":")[1].replace("\"", "").replace("}", ""));
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
			CreateEmp em = new CreateEmp(Integer.parseInt(data.get("id")), data.get("name"),
					Integer.parseInt(data.get("sal")), data.get("desig"), data.get("pass"), data.get("email"), con);
			System.out.println(em.insertEmp());
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("update")) {
			CreateEmp em = new CreateEmp(Integer.parseInt(data.get("id")), data.get("name"),
					Integer.parseInt(data.get("sal")), data.get("desig"), data.get("pass"), data.get("email"), con);
			System.out.println(em.updateEmp());
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (data.get("method").equals("delete")) {
			CreateEmp em = new CreateEmp(Integer.parseInt(data.get("id")), data.get("name"),
					Integer.parseInt(data.get("sal")), data.get("desig"), data.get("pass"), data.get("email"), con);
			System.out.println(em.deleteEmp());
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
