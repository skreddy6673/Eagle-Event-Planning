package guest.model;

import java.util.List;

public class Guest {

	private long id;
	private int guestid;
	private String firstname;
	private String lastname;
	private int eventid;
	private String name;
	private int tableNumber;
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<Guestpreferences> guestpreferences;

	public List<Guestpreferences> getGuestpreferences() {
		return guestpreferences;
	}

	public void setGuestpreferences(List<Guestpreferences> guestpreferences) {
		this.guestpreferences = guestpreferences;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGuestid() {
		return guestid;
	}

	public void setGuestid(int guestid) {
		this.guestid = guestid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

}
