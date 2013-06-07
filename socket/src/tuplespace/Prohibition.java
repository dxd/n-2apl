package tuplespace;

import java.sql.Timestamp;
import java.util.Date;

import oopl.DistributedOOPL;
import net.jini.core.entry.Entry;

public class Prohibition implements TimeEntry {

	public String agent;
	public String prohibition;
	public String sanction;
	public Integer clock;
	public Timestamp time;
	

	public Prohibition() {

	}
	public Prohibition(String agent, String prohibition, String sanction, Integer clock) {
		
		this.agent = agent;
		this.prohibition = prohibition;
		this.sanction = sanction;
		this.clock = clock;
		this.time = new Timestamp(new Date().getTime());
	}
	
	public Prohibition(String agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "Prohibition [agent=" + agent + ", prohibition=" + prohibition
				+ ", sanction=" + sanction + ", clock=" + clock + ", time="
				+ time + "]";
	}
	@Override
	public int[] toArray(DistributedOOPL oopl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTime() {
		this.time = new Timestamp(new Date().getTime());
		
	}
	@Override
	public Timestamp getTime() {
		return this.time;
	}
}
