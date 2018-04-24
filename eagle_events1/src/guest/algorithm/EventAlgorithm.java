package guest.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guest.event;
import guest.dao.EventDao;
import guest.dao.GuestDao;
import guest.model.EventSeating;
import guest.model.EventSeatingbyAlphabets;
import guest.model.Guest;
import guest.model.GuestDetails;
import guest.model.Table;

public class EventAlgorithm {

	private int eventid;

	public EventAlgorithm(int eventid) {

		this.eventid = eventid;
		initialization();
		crossOver();

	}

	Map<Integer, GuestDetails> guestDetailsMap;
	List<GuestDetails> guestDetailsList;
	List<Table> tables = new ArrayList<>();

	private Map<Integer, ArrayList<Integer>> chromosome = new HashMap<>();

	event event;

	public void initialization() {

		GuestDao guestDao = new GuestDao();
		guestDetailsMap = guestDao.readGuestswithPreferences(eventid);
		guestDetailsList = new ArrayList<GuestDetails>(guestDetailsMap.values());

		EventDao eventDao = new EventDao();
		event = eventDao.readEvent(eventid);

		createNewTable();
	}

	public Table createNewTable() {

		Table table = new Table();
		table.setTableno((tables.size() + 1));
		System.out.println("capacity"+(event.getChairspertable() - event.getTablesize()));
		table.setTableCapacity(event.getChairspertable() - event.getTablesize());
		tables.add(table);
		return table;
	}

	public boolean isTableFull(Table table) {

		return table.getGuestnumbers().size() >= table.getTableCapacity();
	}

	public void crossOver() {

		// for (Table nextTable : tables) {
		//
		// if (isTableFull(nextTable)) {
		// continue;
		// }
		//
		// for (GuestDetails nextGuest : guestDetailsList) {
		// if (nextGuest.getTableassisned() == 0) {
		//
		//
		// }
		// }
		// }
		for (Map.Entry<Integer, GuestDetails> entry : guestDetailsMap.entrySet()) {
			GuestDetails nextGuest = entry.getValue();
			if (nextGuest.getTableassisned() == 0) {

				findAvailableTableforPreferredSitters(nextGuest);

			}
		}

		for (Map.Entry<Integer, GuestDetails> entry : guestDetailsMap.entrySet()) {
			GuestDetails nextGuest = entry.getValue();
			if (nextGuest.getTableassisned() == 0) {

				
				tableloop: for (Table nextTable : tables) {
					if (isTableFull(nextTable)) {
						continue tableloop;
					}
					if (!isTableFull(nextTable)) {
						int fitness = fitnessCalculation(nextGuest, nextTable);
						if (fitness == -1) {
							continue tableloop;
						}
						addGuesttoTable(nextGuest.getGuest().getGuestid(), nextTable);
						List<Integer> loveSitting = nextGuest.getLovesitting();
						for (Integer integer : loveSitting) {
							addGuesttoTable(integer, nextTable);
						}
					}
				}
			  
			    if(nextGuest.getTableassisned()==0){
			   
			    	Table newTable =  createNewTable();
			    	addGuesttoTable(nextGuest.getGuest().getGuestid(), newTable);
			    }
			}
		}
	}

	public void setTableAssigned(int guestnumber) {

		guestDetailsMap.get(guestnumber).setTableassisned(1);
	}

	public void findAvilableTableforGuest() {

	}

	public void addGuesttoTable(int guestno, Table table) {

		if (guestDetailsMap.get(guestno).getTableassisned() == 0) {
			if (!isTableFull(table)) {

				int fitness = fitnessCalculation(guestDetailsMap.get(guestno), table);

				if (fitness != -1) {
					guestDetailsMap.get(guestno).getGuest().setTableNumber(table.getTableno());
					table.addGuest(guestno);
					setTableAssigned(guestno);
				}

			}
		}
	}

	public void findAvailableTableforPreferredSitters(GuestDetails guest) {

		int needcapacity = 1 + guest.getLovesitting().size();
		for (Table table : tables) {

			int availableCapacity = table.getTableCapacity() - table.getGuestnumbers().size();
			if (availableCapacity >= needcapacity) {

				addGuesttoTable(guest.getGuest().getGuestid(), table);

				List<Integer> loveSitting = guest.getLovesitting();
				for (Integer integer : loveSitting) {

					addGuesttoTable(integer, table);

				}

				break;

			}

		}
	}

	public int fitnessCalculation(GuestDetails guest, Table table) {

		int guestid = guest.getGuest().getGuestid();
		List<Integer> guestnumbers = table.getGuestnumbers();

		for (Integer guestnumber : guestnumbers) {

			List<Integer> hatedsittig = guestDetailsMap.get(guestnumber).getHatedsittig();

			if (hatedsittig.contains(guestid)) {
				return -1;
			}

			List<Integer> guesthatedsittig = guestDetailsMap.get(guestid).getHatedsittig();

			if (guesthatedsittig.contains(guestnumber)) {
				return -1;
			}
		}

		// List<Integer> hatedsittig = guest.getHatedsittig();
		// List<Integer> lovesitting = guest.getLovesitting();
		// if (hatedsittig.contains(guestid)) {
		// return -1;
		// } else if (lovesitting.contains(guestid)) {
		// return 1;
		// }
		return 0;
	}

	public List<EventSeatingbyAlphabets> evaluationbyAlphabets() {

		List<EventSeatingbyAlphabets> eventSeatings = new ArrayList<>();

		List<GuestDetails> guestslist = new ArrayList<GuestDetails>(guestDetailsMap.values());
		HashMap<String, List<Guest>> hashMap = new HashMap<String, List<Guest>>();
		for (GuestDetails guestDetails : guestslist) {

			String alphabet = guestDetails.getGuest().getFirstname().substring(0, 1).toUpperCase();
			if (!hashMap.containsKey(alphabet)) {
				List<Guest> list = new ArrayList<Guest>();
				list.add(guestDetails.getGuest());

				hashMap.put(alphabet, list);
			} else {
				hashMap.get(alphabet).add(guestDetails.getGuest());
			}
		}

		for (Map.Entry<String, List<Guest>> entry : hashMap.entrySet()) {

			EventSeatingbyAlphabets eventSeatingbyAlphabets = new EventSeatingbyAlphabets();
			eventSeatingbyAlphabets.setAlphabet(entry.getKey());
			eventSeatingbyAlphabets.setGuests(entry.getValue());
			eventSeatings.add(eventSeatingbyAlphabets);
		}

		return eventSeatings;
	}

	public List<EventSeating> evaluation() {

		List<EventSeating> eventSeatings = new ArrayList<>();
		for (Table table : tables) {
			System.out.println("table " + table.getTableno());
			List<Integer> guestNumbers = table.getGuestnumbers();
			EventSeating eventSeating = new EventSeating();
			eventSeating.setTableno(table.getTableno());
			List<Guest> guests = new ArrayList<>();
			for (Integer integer : guestNumbers) {
				System.out.println(integer + "  " + guestDetailsMap.get(integer).getGuest().getFirstname() + " "
						+ guestDetailsMap.get(integer).getGuest().getLastname());
				guests.add(guestDetailsMap.get(integer).getGuest());
			}
			eventSeating.setGuests(guests);
			System.out.println("\n\n\n");
			eventSeatings.add(eventSeating);
		}
		return eventSeatings;

	}

	public static void main(String[] args) {

		EventAlgorithm eventAlgorithm= new EventAlgorithm(5);
		eventAlgorithm.initialization();
		eventAlgorithm.evaluation();
		System.out.println();
	}

}
