package guest.model;

import java.io.Serializable;
import java.util.List;

public class EventSeatingbyAlphabets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String alphabet;
	private List<Guest> guests;

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

}
