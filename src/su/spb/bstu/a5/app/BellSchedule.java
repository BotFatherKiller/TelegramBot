package su.spb.bstu.a5.app;

public class BellSchedule {

	private Bell[] schedule = new Bell[10];

	public BellSchedule() {
		this.schedule[1] = new Bell(10, 40);
	}

	public Bell[] getSchedule() {
		return schedule;
	}

	public void setSchedule(Bell[] schedule) {
		this.schedule = schedule;
	}
}
