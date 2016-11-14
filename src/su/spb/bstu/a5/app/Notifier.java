package su.spb.bstu.a5.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;

public class Notifier implements Runnable {

	BellSchedule bellSchedule = new BellSchedule();
	Calendar cl = Calendar.getInstance();
	TelegramBot bot = new TelegramBot();
	Date date = new Date();
	int currentHour = 0;
	int currentMin = 0;
	List<User> users = new ArrayList<User>();

	@Override
	public void run() {
		//Отладочный вывод сообщения о запуске бота
		String notification = "Бот запущен. Поток оповещений запущен (";
		notification += "ID: " + Thread.currentThread().getId() + ").";
		sendNotification(notification, TelegramBot.ADMIN_CHAT_ID);
		
		while (true) {
			cl = Calendar.getInstance();
			currentHour = cl.get(Calendar.HOUR);
			currentMin = cl.get(Calendar.MINUTE);
			//Если прозвенел звонок
			if (Arrays.asList(bellSchedule).contains(new Bell(currentHour, currentMin))) {
				// TODO: Проверка на наличие занятия у группы и рассылка уведомления каждому зарегистрированному пользователю
				sendNotification("Звонок на пару! " + currentHour + ":" + currentMin, TelegramBot.ADMIN_CHAT_ID);
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	void sendNotification(String text, String chatID) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatID);
		sendMessage.setText(text);	
			try {
				this.bot.sendMessage(sendMessage);
			} catch (org.telegram.telegrambots.exceptions.TelegramApiException e) {
				e.printStackTrace();
			}

	}

	public void updateUsers() {
		//Обновить базу пользователей
	}

}
