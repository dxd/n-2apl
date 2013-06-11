package tuplespace;

import java.sql.Timestamp;
import java.util.Date;

import oopl.DistributedOOPL;
import net.jini.core.entry.Entry;

public class ActionRequest implements TimeEntry {
	
	public Integer id;
	public String agent;
	public String type;
	public Cell cell;
	public Integer clock;
	public Timestamp time;
	
	public ActionRequest() {
		
	}
	public ActionRequest(String agent, String type, Cell cell, int clock) {
		this.agent = agent;
		this.cell = cell;
		this.type = type;
		this.clock = clock;
		this.time = new Timestamp(new Date().getTime());
	}
	public ActionRequest(Integer clock) {
		this.clock = clock;
	}

	public ActionRequest(String agent, String type) {
		this.agent = agent;
		this.type = type;
	}
	
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public String toString() {
		return "ActionRequest [id=" + id + ", agent=" + agent + ", type="
				+ type + ", cell=" + cell + ", clock=" + clock + ", time="
				+ time + "]";
	}
	@Override
	public Integer getClock() {
		return clock;
	}

	@Override
	public void setClock(int clock) {
		this.clock = clock;
	}

}
