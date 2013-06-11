package game;
//import helperTS.Update;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
//import org.json.JSONTokener;

import tuplespace.ActionRequest;
import tuplespace.Cargo;
import tuplespace.Coin;
import tuplespace.NotificationHandler;
import tuplespace.Points;
import tuplespace.Position;
import tuplespace.TimeEntry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonToken;
import com.javadocmd.simplelatlng.LatLng;

import dataJSon.*;



public class Synchronization {

	private static String server = "http://albinoni.cs.nott.ac.uk:49992";
	private static int gameId = 9;
	
	public JSpace jspace;

	private Status status;

	//public Update update;
    
	public Synchronization(JSpace jspace) {
		this.jspace = jspace;
	}
	
	private void initialize() {
		
		resetGame();
		postJoin("email", "a1", "runner");
		postJoin("email1", "a2", "runner");
		postJoin("email2", "a3", "runner");
		postJoin("email3", "t1", "truck");
		postJoin("email4", "c1", "controller");
		startGame();
		//postLocation(65, new LatLng(52.9511,-1.1866));
		//postLocation(66, new LatLng(52.9518,-1.1860));
		//postLocation(67, new LatLng(52.9513,-1.185));
		//getReading(6, new LatLng(52.951623,-1.186357));
		register();
	}
	private void startGame() {
		//get
    	try {

    		URL ruby = new URL(server + "/admin/games/" + gameId+ "/start"); 
    		
    		Reader reader = new InputStreamReader(ruby.openStream());
    		reader.close();
    		
    	} catch (MalformedURLException e) {
    	} catch (IOException e) {
    	}
		
	}
	private void resetGame() {
		//get
    	try {

    		URL ruby = new URL(server + "/admin/games/" + gameId+ "/reset"); 
    		
    		Reader reader = new InputStreamReader(ruby.openStream());
    		reader.close();
    		
    	} catch (MalformedURLException e) {
    	} catch (IOException e) {
    	}
		
	}

	private String buildPostData(ArrayList<SimpleEntry<String, Object>> params) throws UnsupportedEncodingException{
		
		String data = "";
		for (SimpleEntry<String, Object> entry : params)
		{
			data += "&" + URLEncoder.encode(entry.getKey().toString(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8");
		}
		return data;
		
	}

	private void PostRequest(String url, String data, Object result){
		try {
			URL ruby = new URL(server + url);
			
    	    URLConnection conn = ruby.openConnection();
    	    System.out.println(conn.toString());
    	    System.out.println(data);
    	    conn.setDoOutput(true);
    	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    	    wr.write(data);
    	    wr.flush();

    	    // Get the response
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	    Gson gson = new GsonBuilder().create();
		    result = gson.fromJson(reader, result.getClass());
		    System.out.println(result.toString());
		    reader.close();


    	} catch (Exception e) {
    	
        
		}
	}
	
	
	public void getStatus() {
    	//get
    	try {

    		URL ruby = new URL(server + "/game/" + gameId+ "/status.json"); 
    		
    		Reader reader = new InputStreamReader(ruby.openStream());
    		//BufferedReader reader = new BufferedReader(new FileReader("/Users/dxd/git/Git/socket/src/status.json"));
    		
    		    Gson gson = new GsonBuilder().create();
    		    status = gson.fromJson(reader, Status.class);
    		    System.out.println(status.toString());
    		    reader.close();
    		
    	} catch (MalformedURLException e) {
    	} catch (IOException e) {
    	}
 
	}
	public void run(int clock) {
		
		pull();
		//update = new Update(status);
    	if (clock == 1)
    	{
    		initialize();
    	}
    	jspace.writeTime(clock);
		
	}

	public void postRequests(ArrayList<TimeEntry> r) {
		for (TimeEntry p : r) {
			postRequest((Coin) p);
		}
	}
	
	public void postRequest(Coin coin) {
		LatLng latlng = Game.gridToLocation(coin.getCell());
		updateRequests(latlng);
		
	}
	private void updateRequests(LatLng loc) {
		Generic gen = new Generic();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("latitude", loc.getLatitude()));
		params.add(new SimpleEntry<String, Object>("longitude", loc.getLongitude()));
		
		String url = "/game/" + gameId + "/request";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, gen);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}


	public void postCargos(ArrayList<TimeEntry> r) {
		for (TimeEntry p : r) {
			postCargo((Cargo) p);
		}
	}
	
	public void postCargo(Cargo cargo) {
		LatLng latlng = Game.gridToLocation(cargo.getCell());
		dropCargos(latlng);
	}

	private void dropCargos(LatLng loc) {
		Generic gen = new Generic();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("latitude", loc.getLatitude()));
		params.add(new SimpleEntry<String, Object>("longitude", loc.getLongitude()));
		
		String url = "/game/" + gameId + "/dropCargo";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, gen);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void postPoint(ArrayList<TimeEntry> r) {
		for (TimeEntry p : r) {
			updatePoints(((Points)p).id, ((Points)p).value);
		}
		
	}
	
	private void updatePoints(int id, int points) {
		Generic gen = new Generic();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("id", id));
		params.add(new SimpleEntry<String, Object>("points", points));
		
		String url = "/game/" + gameId + "/updatePoints";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, gen);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getReadings(ArrayList<TimeEntry> r) {
		for (TimeEntry t : r)
		{
			ActionRequest ar = (ActionRequest)t;
			if (ar.type.equalsIgnoreCase("reading")) {
				getReading(ar);
			}
			else if (ar.type == "investigation") {
				getInvestigation(ar);
			}
		}
	}

	public void getReading(ActionRequest ar) {
		System.out.println(ar);
		LatLng latlng = Game.gridToLocation(ar.getCell());
		ReadingResponse rr = getReading(status.getPlayerId(ar.agent), latlng);
		
		jspace.writeReading(new tuplespace.Reading(ar.agent, ar.cell,rr.getDistance()), Start.gs.getClock(), status);
	}
	
	public ReadingResponse getReading(int id, LatLng loc) {

		ReadingResponse result = new ReadingResponse();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("id", id));
		params.add(new SimpleEntry<String, Object>("latitude", loc.getLatitude()));
		params.add(new SimpleEntry<String, Object>("longitude", loc.getLongitude()));
		
		String url = "/game/" + gameId + "/getReading";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, result);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	public void getInvestigation(ActionRequest readUpdate) {
		// TODO Auto-generated method stub
		
	}
	
	public void postLocations(ArrayList<TimeEntry> r) {
		for (TimeEntry p : r) {
			postLocation((Position) p);
		}
		
	}

	public void postLocation(Position loc) {
		LatLng latlng = Game.gridToLocation(loc.getCell());
		postLocation(status.getPlayerId(loc.agent), latlng);
		//System.out.println(loc.getId().toString()+ latlng.toString());
	}
	
	public void postLocation(int id, LatLng loc) {

		Generic gen = new Generic();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("id", id));
		params.add(new SimpleEntry<String, Object>("latitude", loc.getLatitude()));
		params.add(new SimpleEntry<String, Object>("longitude", loc.getLongitude()));
		
		String url = "/game/" + gameId + "/postLocation";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, gen);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
  
	private void pull() {
		getStatus();
	}
	
	public JoinResponse postJoin(String string, String string2, String string3) {
		JoinResponse response = new JoinResponse();
		ArrayList<SimpleEntry<String, Object>> params = new ArrayList<SimpleEntry<String, Object>>();
		params.add(new SimpleEntry<String, Object>("email", string));
		params.add(new SimpleEntry<String, Object>("name", string2));
		params.add(new SimpleEntry<String, Object>("team", string3));
		
		String url = "/game/" + gameId + "/join";
		try {
			String data = buildPostData(params);
			PostRequest(url, data, response);
			status.addPlayer(response, string2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response;
		
	}
	public void register() {
		try {
			for (int i=0; i<jspace.agents.length;i++) {
				JSpace.space.notify(new Position(jspace.agents[i]), null,
						new NotificationHandler(this),
						Lease.FOREVER,
						new MarshalledObject(new String[]{"position", jspace.agents[i]}));
				JSpace.space.notify(new ActionRequest(jspace.agents[i],"reading"), null,
						new NotificationHandler(this),
						Lease.FOREVER,
						new MarshalledObject(new String[]{"makeReading", jspace.agents[i]}));
				JSpace.space.notify(new ActionRequest(jspace.agents[i],"investigation"), null,
						new NotificationHandler(this),
						Lease.FOREVER,
						new MarshalledObject(new String[]{"makeInvestigation", jspace.agents[i]}));
				JSpace.space.notify(new tuplespace.Coin(jspace.agents[i]), null,
						new NotificationHandler(this),
						Lease.FOREVER,
						new MarshalledObject(new String[]{"coin", jspace.agents[i]}));
				JSpace.space.notify(new tuplespace.Points(jspace.agents[i]), null,
						new NotificationHandler(this),
						Lease.FOREVER,
						new MarshalledObject(new String[]{"points", jspace.agents[i]}));
			}
			JSpace.space.notify(new tuplespace.Cargo(), null,
					new NotificationHandler(this),
					3000000,
					new MarshalledObject(new String[]{"cargo","agent"}));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

















	
}

