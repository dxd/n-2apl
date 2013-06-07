package dataJSon;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class Status {
	
	private List<Request> request = new ArrayList<Request>();
	private List<Location> location = new ArrayList<Location>();
	private List<Reading> reading = new ArrayList<Reading>();
	private List<Cargo> cargo = new ArrayList<Cargo>();
	private List<Player> player = new ArrayList<Player>();
	
	public Status(){
		
	}

	public List<Request> getRequests() {
		return request;
	}


	public void setRequests(List<Request> requests) {
		this.request = requests;
	}

	public List<Location> getLocations() {
		return location;
	}

	public void setLocations(List<Location> locations) {
		this.location = locations;
	}

	public List<Reading> getReadings() {
		return reading;
	}

	public void setReadings(List<Reading> readings) {
		this.reading = readings;
	}

	public List<Cargo> getCargos() {
		return cargo;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargo = cargos;
	}

	public List<Player> getPlayers() {
		return player;
	}

	public void setPlayers(List<Player> players) {
		this.player = players;
	}

	public int getPlayerId(String agent) {
		//System.out.println(player.toString());
		for (Player p : player)
		{
			if (p.getName().equals(agent))
				return p.getId();
		}
		return 0;
	}
	
	public String getPlayerName(int id) {
		for (Player p : player)
		{
			if (p.getId() == id)
				return p.getName();
		}
		return null;
	}

	@Override
	public String toString() {
		return "Status [request=" + request + ", location=" + location
				+ ", reading=" + reading + ", cargo=" + cargo + ", player="
				+ player + "]";
	}

	public void addReading(ReadingResponse r, LatLng latlng, int id) {
		
		reading.add(new Reading((float) latlng.getLatitude(), (float) latlng.getLongitude(), id, Float.valueOf(r.getReading())));
		
	}

	public void addPlayer(JoinResponse r, String name) {
		
		player.add(new Player(r.getUser_id(), name, r.getTeam_name()));
		
	}




}
