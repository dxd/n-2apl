package dataJSon;

public class JoinResponse {

	private String team_name;
	private int user_id;
	
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "JoinResponse [team_name=" + team_name + ", user_id=" + user_id
				+ "]";
	}
	
}
