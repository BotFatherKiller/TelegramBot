package su.spb.bstu.a5.app;

public class BellSchedule {

	private Bell[] schedule = new Bell[7];

	public BellSchedule() {
		this.schedule[0] = new Bell(8, 50);
		this.schedule[1] = new Bell(10, 40);
		this.schedule[2] = new Bell(12, 30);
		this.schedule[3] = new Bell(14, 45);
		this.schedule[4] = new Bell(16, 35);
		this.schedule[5] = new Bell(18, 20);
		this.schedule[6] = new Bell(21, 15);
	}

	public Bell[] getSchedule() {
		return schedule;
	}

	public void setSchedule(Bell[] schedule) {
		this.schedule = schedule;
	}
}
