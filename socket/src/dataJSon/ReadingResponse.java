package dataJSon;

public class ReadingResponse {
	
	private float distance;
	private float reading;
	
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getReading() {
		return reading;
	}
	public void setReading(float reading) {
		this.reading = reading;
	}
	@Override
	public String toString() {
		return "ReadingResponse [distance=" + distance + ", reading=" + reading
				+ "]";
	}

}
