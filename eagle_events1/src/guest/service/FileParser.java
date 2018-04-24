package guest.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import guest.dao.GuestDao;
import guest.model.Guest;
import guest.model.Guestpreferences;

public class FileParser {

	InputStream inputStream;
	private int eventid;

	public FileParser(InputStream inputStream, int eventid) {

		this.inputStream = inputStream;
		this.eventid = eventid;
	}

	public void guestFileParser() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String array_line;
		boolean firstline = true;

		int columnlength = 0;
		String[] headerarray = null;

		GuestDao guestDao = new GuestDao();

		while ((array_line = reader.readLine()) != null) {

			if (firstline) {
				firstline = false;
				headerarray = array_line.split(",");
				columnlength = headerarray.length;
				continue;
			}
			String[] array = array_line.split(",");

			Guest guest = new Guest();
			guest.setGuestid(Integer.parseInt(array[0]));
			guest.setFirstname(array[1]);
			guest.setLastname(array[2]);
			guest.setEventid(eventid);

			List<Guestpreferences> guestpreferences = new ArrayList<>();

			for (int i = 3; i < array.length; i++) {

				Guestpreferences guestpreference = new Guestpreferences();
				guestpreference.setEventid(eventid);
				guestpreference.setGid(Integer.parseInt(array[0]));
				guestpreference.setOgid(Integer.parseInt(array[i]));

				System.out.println(Integer.parseInt(array[0]) + "  " + Integer.parseInt(array[i]));
				System.out.println(headerarray[i]);
				if (headerarray[i].trim().equals("Same Table")) {
					guestpreference.setPreferred((short) 1);
				} else {
					guestpreference.setPreferred((short) 0);

				}
				guestpreferences.add(guestpreference);

			}

			guest.setGuestpreferences(guestpreferences);

			guestDao.addGuest(guest);
		}
		reader.close();
	}
}
