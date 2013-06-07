package dataJSon;

public class Reading {
	
	private int id;
	private float longitude;
	private float latitude;
	private int player_id;
	private float value;
	

	public Reading(float longitude, float latitude, int player_id, float value) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.player_id = player_id;
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Reading [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + ", player_id=" + player_id + ", value=" + value
				+ "]";
	}

}
