package guest.model;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private int tableno;
	private int tableCapacity;
	private int filledCapacity =0;
	
	private List<Integer> guestnumbers = new ArrayList<>();

	
	public void addGuest(int guestnumber){
		guestnumbers.add(guestnumber);
	}
	
	public List<Integer> getGuestnumbers() {
		return guestnumbers;
	}

	public void setGuestnumbers(List<Integer> guestnumbers) {
		this.guestnumbers = guestnumbers;
	}

	public int getTableno() {
		return tableno;
	}

	public void setTableno(int tableno) {
		this.tableno = tableno;
	}

	public int getTableCapacity() {
		return tableCapacity;
	}

	public void setTableCapacity(int tableCapacity) {
		this.tableCapacity = tableCapacity;
	}

	public int getFilledCapacity() {
		return filledCapacity;
	}

	public void setFilledCapacity(int filledCapacity) {
		this.filledCapacity = filledCapacity;
	}

	
}
