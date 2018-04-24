package guest.model;

public class Guestpreferences {

	private long id;
	private int gid;
	private int ogid;
	private short preferred;
	private int eventid;

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOgid() {
		return ogid;
	}

	public void setOgid(int ogid) {
		this.ogid = ogid;
	}

	public short getPreferred() {
		return preferred;
	}

	public void setPreferred(short preferred) {
		this.preferred = preferred;
	}

}
