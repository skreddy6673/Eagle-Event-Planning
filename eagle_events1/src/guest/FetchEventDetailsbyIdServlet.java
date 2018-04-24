package guest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import guest.dao.EventDao;

/**
 * Servlet implementation class EventDetailsbyIdServlet
 */
@WebServlet("/FetchEventDetailsbyIdServlet")
public class FetchEventDetailsbyIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchEventDetailsbyIdServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HashMap<String, String> data = new HashMap<>();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			if ((line = reader.readLine()) != null) {
				data.put(line.split(",")[0].split(":")[0].replace("\"", "").replace("{", ""),
						line.split(",")[0].split(":")[1].replace("\"", ""));

				data.put(line.split(",")[1].split(":")[0].replace("\"", ""),
						line.split(",")[1].split(":")[1].replace("\"", "").replace("}", ""));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String eventid = data.get("eventid");
		EventDao eventDao = new EventDao();
		event event = eventDao.readEvent(Integer.parseInt(eventid));
		String json = new Gson().toJson(event);
		response.setContentType("application/json");
		response.getWriter().write(json);

	}

}
