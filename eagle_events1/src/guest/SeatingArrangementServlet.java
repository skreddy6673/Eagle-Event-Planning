package guest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import guest.algorithm.EventAlgorithm;
import guest.model.EventSeating;
import guest.model.EventSeatingbyAlphabets;

/**
 * Servlet implementation class SeatingArrangementServlet
 */
@WebServlet("/SeatingArrangementServlet")
public class SeatingArrangementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeatingArrangementServlet() {
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

		EventAlgorithm eventAlgorithm = new EventAlgorithm(Integer.parseInt(data.get("eventid")));

		if (data.get("order").equals("table")) {
			List<EventSeating> eventSeatings = eventAlgorithm.evaluation();
			String json = new Gson().toJson(eventSeatings);
			response.setContentType("application/json");
			response.getWriter().write(json);
		} else {

			List<EventSeatingbyAlphabets> eventSeatings = eventAlgorithm.evaluationbyAlphabets();
			String json = new Gson().toJson(eventSeatings);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

}
