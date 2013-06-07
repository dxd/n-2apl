package dataJSon;

public class ReadingResponse {
	
	private float distance;
	private String reading;
	
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public String getReading() {
		return reading;
	}
	public void setReading(String reading) {
		this.reading = reading;
	}
	@Override
	public String toString() {
		return "ReadingResponse [distance=" + distance + ", reading=" + reading
				+ "]";
	}

}
