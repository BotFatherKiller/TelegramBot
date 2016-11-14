package su.spb.bstu.a5.app;

public class User {

	private int id = 0;
	private String username = new String("");
	private String chatID = new String("");
	private String groupname = new String("");

	public User(int id, String username, String chatID, String groupname) {
		this.id = id;
		this.username = username;
		this.chatID = chatID;
		this.groupname = groupname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChatID() {
		return chatID;
	}

	public void setChatID(String chatID) {
		this.chatID = chatID;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", chatID=" + chatID + ", groupname=" + groupname + "]";
	}

}
