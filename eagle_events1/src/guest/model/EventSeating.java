package guest.model;

import java.io.Serializable;
import java.util.List;

public class EventSeating implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Guest> guests;
	private int tableno;

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public int getTableno() {
		return tableno;
	}

	public void setTableno(int tableno) {
		this.tableno = tableno;
	}

}
