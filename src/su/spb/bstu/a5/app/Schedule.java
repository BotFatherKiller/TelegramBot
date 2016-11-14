package su.spb.bstu.a5.app;

import java.sql.Time;

public class Schedule {

	private int id = 0;
	private String chet = new String("");
	private String weekday = new String("");
	private String type = new String("");
	private String predmet = new String("");
	private String prepod = new String("");
	private Time time = new Time(0);
	private String party = new String("");
	private String audit = new String("");
	
	public Schedule(int id, String chet, String weekday, String type, String predmet, String prepod, Time time,
			String party, String audit) {
		this.id = id;
		this.chet = chet;
		this.weekday = weekday;
		this.type = type;
		this.predmet = predmet;
		this.prepod = prepod;
		this.time = time;
		this.party = party;
		this.audit = audit;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChet() {
		return chet;
	}
	public void setChet(String chet) {
		this.chet = chet;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPredmet() {
		return predmet;
	}
	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}
	public String getPrepod() {
		return prepod;
	}
	public void setPrepod(String prepod) {
		this.prepod = prepod;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", chet=" + chet + ", weekday=" + weekday + ", type=" + type + ", predmet="
				+ predmet + ", prepod=" + prepod + ", time=" + time + ", party=" + party + ", audit=" + audit + "]";
	}

}
