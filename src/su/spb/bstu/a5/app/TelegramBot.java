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
	private static Groups listGroup = new Groups();

	public static void main(String[] args) {
		System.out.println("Bot started...");

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		// TODO: добавить оптимизатор

		// Создаем поток оповещений
		Thread tt = new Thread(nt);
		// Запускаем поток оповещений
		tt.start();

		try {
			telegramBotsApi.registerBot(new TelegramBot());
		} catch (TelegramApiRequestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

		if (query != null && query.getData().equals("pause")) {
			removeUser(query.getFrom(), query.getFrom().getId().toString());
		}

		if (query != null && query.getData().equals("play")) {
			MySQLConnector mySqlConnector = new MySQLConnector();

			Statement mysqlStatement = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				mySqlConnector.connect();
				mysqlStatement = mySqlConnector.getMysqlConnection().createStatement();
				String sql;
				sql = "update bot_users set active=1  where chatID='" + query.getFrom().getId().toString() + "'";
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
			
			sendInlineKeboard(query.getFrom().getId().toString(), "Уведомления включены.", "Отписаться", "pause");
			
			nt.updateUsers();
		}

		if (message != null && message.getText().equals("/start")) {
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
				sendMessage.setText("Выберите вашу группу из списка доступных:");
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

		if (query != null && TelegramBot.listGroup.groupList.contains(query.getData())) {
			register(query.getFrom(), query.getFrom().getId().toString(), query.getData());
		}

	}

	private void register(User user, String chatID, String group) {
		addUser(user.getUserName(), chatID, group);

		sendInlineKeboard(chatID, "Вы подписались на уведомления для группы " + group + ".", "Отписаться", "pause");
	}

	private void sendInlineKeboard(String chatID, String messageText, String buttonText, String callBackData) {
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatID.toString());
		sendMessage.setText(messageText);
		sendMessage.setReplyMarkup(keyboard);
		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText(buttonText);
		button.setCallbackData(callBackData);
		listButton.add(rowInline);
		rowInline.add(button);
		keyboard.setKeyboard(listButton);

		try {
			nt.bot.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		nt.updateUsers();
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

		sendInlineKeboard(chatID, "Уведомления отключены.", "Возобновить", "play");
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
