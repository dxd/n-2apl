package dataJSon;

public class Location {

	private int player_id;
	private float longitude;
	private float latitude;
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "Location [player_id=" + player_id + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}
	
	
}
