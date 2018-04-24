package guest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import guest.dao.GuestDao;
import guest.model.Guest;
import guest.model.Guestpreferences;

/**
 * Servlet implementation class AddSingleGuestServlet
 */
@WebServlet("/AddSingleGuestServlet")
public class AddSingleGuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSingleGuestServlet() {
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

		GuestDao guestDao = new GuestDao();

		String firstname = data.get("firstname");
		String lastname = data.get("lastname");
		String eventid = data.get("eventid");
		String preferredguests = data.get("preferredguests");
		System.out.println(preferredguests);
		String notpreferredguests = data.get("notpreferredguests");

		Guest guest = new Guest();
		guest.setEventid(Integer.parseInt(eventid));
		guest.setFirstname(firstname);
		guest.setLastname(lastname);

		int guestid = 0;

		if (data.get("mode").equals("insert")) {

			guestid = guestDao.findnextGuestid(Integer.parseInt(eventid));

		} else {

			guestid = Integer.parseInt(data.get("guestid"));

		}
		guest.setGuestid(guestid);

		List<Guestpreferences> guestpreferences = new ArrayList<>();
		String[] pguests = preferredguests.split("-");

		for (String string : pguests) {

			Guestpreferences guestpreference = new Guestpreferences();
			guestpreference.setGid(guestid);
			guestpreference.setEventid(Integer.parseInt(eventid));
			guestpreference.setOgid(Integer.parseInt(string));
			guestpreference.setPreferred((short) 1);
			guestpreferences.add(guestpreference);
		}

		String[] notpguests = notpreferredguests.split("-");

		for (String string : notpguests) {

			if (string.trim().equals("")) {
				continue;
			}
			Guestpreferences guestpreference = new Guestpreferences();
			guestpreference.setGid(guestid);
			guestpreference.setEventid(Integer.parseInt(eventid));
			guestpreference.setOgid(Integer.parseInt(string));
			guestpreference.setPreferred((short) 0);
			guestpreferences.add(guestpreference);
		}

		guest.setGuestpreferences(guestpreferences);

		if (data.get("mode").equals("insert")) {

			guestDao.addGuest(guest);
		} else {
			guestDao.updateGuest(guest);
		}

		List<Guest> guests = guestDao.readGuests(Integer.parseInt(eventid));

		String json = new Gson().toJson(guests);
		response.setContentType("application/json");
		response.getWriter().write(json);

	}

}
