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
 * Servlet implementation class servletValid
 */
@WebServlet("/servletValid")
public class servletValid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletValid() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> data= new HashMap<>();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    if ((line = reader.readLine()) != null){
		    	data.put(line.split(",")[0].split(":")[0].replace("\"", "").replace("{", ""), line.split(",")[0].split(":")[1].replace("\"", ""));
		    	data.put(line.split(",")[1].split(":")[0].replace("\"", ""), line.split(",")[1].split(":")[1].replace("\"", "").replace("}", ""));
		    }
		  } catch (Exception e) { e.printStackTrace();}
		  Connection con=null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.google.gson.Gson");
				con = DBConnection.getConnection();
				Statement st = con.createStatement();
				System.out.println("NAME"+data.get("name"));
				ResultSet rs = st.executeQuery("SELECT * FROM eagle_events.employee where name='"+data.get("name")+"'");
				ArrayList<Employee> emps = new ArrayList<>();
				if(rs.next()){
					Employee emp = new Employee();
					emp.setEid(rs.getInt("id"));
					emp.setName(rs.getString("name"));
					emp.setSal(rs.getInt("salary"));
					emp.setDesig(rs.getString("designation"));
					emp.setPass(rs.getString("password"));
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

}
