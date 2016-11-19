package su.spb.bstu.a5.app;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class BotNotifier extends TelegramLongPollingBot {
	
	public UserList listUsers = new UserList();
	public ScheduleList listSchedule = new ScheduleList();
	
	@Override
	public String getBotUsername() {
		return "-170187740";
	}

	@Override
	public String getBotToken() {
		return "282302455:AAFgI6CLT-j09Rz1W4WgUmtC-j3h9Drg7UU";
	}

	@Override
	public void onUpdateReceived(Update arg0) {
	}
}
