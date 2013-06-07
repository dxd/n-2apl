package dataJSon;

public class Cargo {
	
	private int id;
	private float longitude;
	private float latitude;
	private int player_id;
	private float radius;
	private boolean exposed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public boolean isExposed() {
		return exposed;
	}
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}
	@Override
	public String toString() {
		return "Cargo [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + ", player_id=" + player_id + ", radius=" + radius
				+ ", exposed=" + exposed + "]";
	}

}
