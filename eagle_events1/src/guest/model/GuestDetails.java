package guest.model;

import java.util.List;

public class GuestDetails {

	private Guest guest;

	private List<Integer> hatedsittig;
	private List<Integer> lovesitting;

	private int tableassisned = 0;

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public List<Integer> getHatedsittig() {
		return hatedsittig;
	}

	public void setHatedsittig(List<Integer> hatedsittig) {
		this.hatedsittig = hatedsittig;
	}

	public List<Integer> getLovesitting() {
		return lovesitting;
	}

	public void setLovesitting(List<Integer> lovesitting) {
		this.lovesitting = lovesitting;
	}

	public int getTableassisned() {
		return tableassisned;
	}

	public void setTableassisned(int tableassisned) {
		this.tableassisned = tableassisned;
	}

}
