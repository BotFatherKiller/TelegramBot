package su.spb.bstu.a5.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class Notifier implements Runnable {

	public Notifier() {
		super();
		bellSchedule = new BellSchedule();
	}

	BellSchedule bellSchedule = null;
	Calendar cl = Calendar.getInstance();
	BotNotifier bot = new BotNotifier();
	Date date = new Date();
	int currentHour = 0;
	int currentMin = 0;

	@Override
	public void run() {
		Calendar cl2 = Calendar.getInstance();
		cl2.set(2016, 9, 1);
		int currentweek = cl.get(Calendar.WEEK_OF_YEAR) - cl2.get(Calendar.WEEK_OF_YEAR);
		this.bot.listUsers.updateActiveUsers();
		while (true) {
			cl = Calendar.getInstance();
			currentHour = cl.get(Calendar.HOUR_OF_DAY);
			currentMin = cl.get(Calendar.MINUTE);
			//Если прозвенел звонок
			Bell myBell = new Bell(currentHour, currentMin);
				for (int i=0; i<this.bot.listUsers.users.size(); i++){
					System.out.println(bot.listUsers.users.get(i).getUsername() + " | " + bot.listUsers.users.get(i).getChatID() + " | " + bot.listUsers.users.get(i).getGroupname());
					for (int j=0; j<this.bot.listSchedule.scheduleList.size(); j++){
						//System.out.println(this.bot.listUsers.users.get(i).getGroupname() + " | " + this.bot.listSchedule.scheduleList.get(j).getParty() + " | Часы из расписания: " + this.bot.listSchedule.scheduleList.get(j).getTime().getHours() + " | Часы сервера: " + myBell.getHour() + " | Минуты расписания: " + this.bot.listSchedule.scheduleList.get(j).getTime().getMinutes() + " | Часы сервера: " + myBell.getMinute());
						if (this.bot.listUsers.users.get(i).getGroupname().equals(this.bot.listSchedule.scheduleList.get(j).getParty()) && this.bot.listSchedule.scheduleList.get(j).getTime().getHours() == myBell.getHour() &&  this.bot.listSchedule.scheduleList.get(j).getTime().getMinutes() == myBell.getMinute()){
							if (this.bot.listSchedule.scheduleList.get(j).getChet().equals("Четная") && currentweek % 2 != 0){
								sendNotification("Аудитория: " + this.bot.listSchedule.scheduleList.get(j).getAudit() + "\nПредмет: " + this.bot.listSchedule.scheduleList.get(j).getPredmet() + "\nВремя начала пары: " + currentHour + ":" + currentMin, this.bot.listUsers.users.get(i).getChatID());
								
							} else if (this.bot.listSchedule.scheduleList.get(j).getChet().equals("Нечетная") && currentweek % 2 == 0){
								sendNotification("Аудитория: " + this.bot.listSchedule.scheduleList.get(j).getAudit() + "\nПредмет: " + this.bot.listSchedule.scheduleList.get(j).getPredmet() + "\nВремя начала пары: " + currentHour + ":" + currentMin, this.bot.listUsers.users.get(i).getChatID());
							}
						}
					}
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
				bot.sendMessage(sendMessage);
			} catch (org.telegram.telegrambots.exceptions.TelegramApiException e) {
				e.printStackTrace();
			}

	}

	public void updateUsers() {
		bot.listUsers.updateActiveUsers();
	}

}
