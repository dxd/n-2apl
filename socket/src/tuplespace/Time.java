package tuplespace;

import java.sql.Timestamp;
import java.util.Date;

import oopl.DistributedOOPL;
import net.jini.core.entry.Entry;

public class Time implements TimeEntry {

	public Integer clock;
	public Timestamp time;
	
	public Time() {

	}
	public Time(int clock) {
		this.clock = clock;
		this.time = new Timestamp(new Date().getTime());
	}
	@Override
	public String toString() {
		return "Time [clock=" + clock + ", time=" + time + "]";
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
