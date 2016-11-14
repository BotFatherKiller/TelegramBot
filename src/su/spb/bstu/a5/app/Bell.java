package su.spb.bstu.a5.app;

public class Bell {

	private int hour = 0;
	private int minute = 0;

	public Bell(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

}
