package su.spb.bstu.a5.app;

public class BellSchedule {

	private Bell[] schedule = new Bell[1];

	public BellSchedule() {
		this.schedule[0] = new Bell(1, 54);
	}

	public Bell[] getSchedule() {
		return schedule;
	}

	public void setSchedule(Bell[] schedule) {
		this.schedule = schedule;
	}
}
