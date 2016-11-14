package su.spb.bstu.a5.app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TelegramBot extends TelegramLongPollingBot {

	public static final String ADMIN_CHAT_ID = "-170187740";
	private static Notifier nt = new Notifier();
	private static UserList listUsers = new UserList();
	private static ScheduleList listSchedule = new ScheduleList();
	private static Groups listGroup = new Groups();

	public static void main(String[] args) {
		System.out.println("Bot started..");

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		// TODO: добавить оптимизатор

		// Создаем поток оповещений
		Thread tt = new Thread(nt);
		// Запускаем поток оповещений
		tt.start();

		listUsers.updateActiveUsers();
		try {
			telegramBotsApi.registerBot(new TelegramBot());
		} catch (TelegramApiRequestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MySQLConnector mySqlConnector = new MySQLConnector();

		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "SELECT count(id) FROM bot_users;";
			ResultSet rs = mysqlStatement.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("count(id)");
				nt.sendNotification("Количество зарегистрированных пользователей: " + id, TelegramBot.ADMIN_CHAT_ID);
			}
			rs.close();
			mysqlStatement.close();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			sql = "SELECT count(id) FROM bot_users where active=1;";
			rs = mysqlStatement.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("count(id)");
				nt.sendNotification("Количество активных пользователей: " + id, TelegramBot.ADMIN_CHAT_ID);
			}
			rs.close();
			mysqlStatement.close();

			mySqlConnector.closeConnection();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mysqlStatement != null)
					mysqlStatement.close();
			} catch (SQLException se2) {
			}
			if (mySqlConnector.getMysqlConnection() != null)
				mySqlConnector.closeConnection();
		}

	}

	@Override
	public String getBotUsername() {
		return "-170187740";
	}

	@Override
	public String getBotToken() {
		return "282302455:AAFgI6CLT-j09Rz1W4WgUmtC-j3h9Drg7UU";
	}

	@Override
	public void onUpdateReceived(Update update) {

		Message message = update.getMessage();
		CallbackQuery query = update.getCallbackQuery();

		if (query != null && TelegramBot.listGroup.groupList.contains(query.getData())) {
			register(query.getFrom(), query.getFrom().getId().toString(), query.getData());
		} else

		if (message != null && message.hasText()) {
			if (message.getText().equals("/отписаться")) {
				removeUser(message.getFrom(), message.getChatId().toString());
			} else if (message.getText().equals("/reboot")) {
				nt.sendNotification("Бот будет перезагружен через несколько секунд.", TelegramBot.ADMIN_CHAT_ID);
				String curDir = System.getProperty("user.dir");
				try {
					Runtime.getRuntime().exec("java -jar " + curDir + "\\telegrambot.jar &");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			} else if (message.getText().equals("/shutdown")) {

				nt.sendNotification("Бот будет отключен через несколько секунд.", TelegramBot.ADMIN_CHAT_ID);
				System.exit(0);
			} else {
				InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

				List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();
				SendMessage sendMessage = new SendMessage();
				int i = 0;

				for (String gr : TelegramBot.listGroup.groupList) {

					if ((i % 4) == 0) {
						listButton.add(rowInline);
						rowInline = new ArrayList<>();
					}

					sendMessage.enableMarkdown(true);
					sendMessage.setChatId(message.getChatId().toString());
					sendMessage.setText("Выберите вашу группу из списка доступных");
					sendMessage.setReplyMarkup(keyboard);
					InlineKeyboardButton button = new InlineKeyboardButton();
					button.setText(gr);
					button.setCallbackData(gr);
					rowInline.add(button);
					i++;
				}

				keyboard.setKeyboard(listButton);
				try {
					nt.bot.sendMessage(sendMessage);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

		}

		TelegramBot.nt.sendNotification(
				"\nЧтобы отписаться от уведомлений отправьте мне сообщение с текстом '/отписаться'.",
				message.getChatId().toString());
	}

	private void register(User user, String chatID, String group) {
		addUser(user.getUserName(), chatID, group);
		TelegramBot.nt.sendNotification(
				"Вы подписались на уведомления для группы " + group
						+ ". Чтобы отписаться от уведомлений отправьте мне сообщение с текстом '/отписаться'.",
				chatID.toString());
		TelegramBot.listUsers.updateActiveUsers();
	}

	private void removeUser(User user, String chatID) {
		MySQLConnector mySqlConnector = new MySQLConnector();

		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();
			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "update bot_users set active=0  where chatID='" + chatID + "'";
			mysqlStatement.executeUpdate(sql);
			mysqlStatement.close();
			mySqlConnector.closeConnection();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mysqlStatement != null)
					mysqlStatement.close();
			} catch (SQLException se2) {
			}
			if (mySqlConnector.getMysqlConnection() != null)
				mySqlConnector.closeConnection();
		}
		nt.updateUsers();
		nt.sendNotification("Уведомления отключены. Отправьте мне номер своей группы чтобы включить их снова.", chatID);
	}

	private void addUser(String username, String chatID, String group) {
		int id = 0;
		MySQLConnector mySqlConnector = new MySQLConnector();
		Statement mysqlStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnector.connect();

			mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
			String sql;
			sql = "select count(id) from bot_users where chatID='" + chatID + "'";

			ResultSet rs = mysqlStatement.executeQuery(sql);

			if (rs.next()) {
				id = rs.getInt("count(id)");
			}
			rs.close();
			mysqlStatement.close();

			if (id == 0) {
				mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
				sql = "insert into bot_users(username, chatID, groupname) values ('" + username + "','" + chatID + "','"
						+ group + "')";
				mysqlStatement.executeUpdate(sql);
				mysqlStatement.close();
				mySqlConnector.closeConnection();
			} else {
				mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
				sql = "update bot_users set active=1, groupname='" + group + "' where chatID = '" + chatID + "'";
				mysqlStatement.executeUpdate(sql);
				mysqlStatement.close();
				mySqlConnector.closeConnection();

			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mysqlStatement != null)
					mysqlStatement.close();
			} catch (SQLException se2) {
			}
			if (mySqlConnector.getMysqlConnection() != null)
				mySqlConnector.closeConnection();
		}
		// nt.updateUsers();
	}
}
