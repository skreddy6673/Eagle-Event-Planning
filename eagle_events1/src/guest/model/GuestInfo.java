package guest.model;

import java.io.Serializable;
import java.util.List;

public class GuestInfo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Guest guest;
	private List<Guest> sitWith;
	private List<Guest> notsitWith;

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public List<Guest> getSitWith() {
		return sitWith;
	}

	public void setSitWith(List<Guest> sitWith) {
		this.sitWith = sitWith;
	}

	public List<Guest> getNotsitWith() {
		return notsitWith;
	}

	public void setNotsitWith(List<Guest> notsitWith) {
		this.notsitWith = notsitWith;
	}

}
