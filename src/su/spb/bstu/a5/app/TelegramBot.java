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
	private static Groups listGroup = new Groups("");

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

		if (message != null && message.getText().equals("/start")) {

			String[] buttonText = new String[3];
			buttonText[0] = "студент";
			buttonText[1] = "магистрант";
			buttonText[2] = "преподаватель";

			String[] callBackData = new String[3];
			callBackData[0] = "student";
			callBackData[1] = "undergraduate";
			callBackData[2] = "lecturer";

			setInlineKeyboard(
					"Добро пожаловать " + message.getFrom().getFirstName()
							+ ", пожалуйста, скажите как мне Вас расценивать",
					buttonText, callBackData, 3, 1, message.getChatId().toString());
		}

		if (query != null && query.getData().equals("student")) {
			String[] buttonText = new String[6];
			buttonText[0] = "Факультет 'А'";
			buttonText[1] = "Факультет 'Е'";
			buttonText[2] = "Факультет 'И'";
			buttonText[3] = "Факультет 'О'";
			buttonText[4] = "Факультет 'Р'";
			buttonText[5] = "УВЦ";

			String[] callBackData = new String[6];
			callBackData[0] = "A";
			callBackData[1] = "E";
			callBackData[2] = "I";
			callBackData[3] = "O";
			callBackData[4] = "P";
			callBackData[5] = "D";

			setInlineKeyboard(
					"Очень приятно, студент " + query.getFrom().getFirstName()
							+ ", пожалуйста, скажите мне свой факультет",
					buttonText, callBackData, 6, 1, query.getFrom().getId().toString());
		}

		if (query != null && query.getData().equals("lecturer")) {
			String[] buttonText = new String[13];
			buttonText[0] = "Толпегин О.А";
			buttonText[1] = "Шалыгин А.С";
			buttonText[2] = "Зазимко В.А";
			buttonText[3] = "Акимов Г.А";
			buttonText[4] = "Петрова И.Л";
			buttonText[5] = "Клочков А.В";
			buttonText[6] = "Лемешонок Т.Ю";
			buttonText[7] = "Курилова Е.А";
			buttonText[8] = "Дьячкова П.Д";
			buttonText[9] = "Алексеева К.С";
			buttonText[10] = "Теляков Р.Ф";
			buttonText[11] = "Аюпов Р.Р";
			buttonText[12] = "Горохов А.В";

			String[] callBackData = new String[13];
			callBackData[0] = "Толпегин О.А";
			callBackData[1] = "Шалыгин А.С";
			callBackData[2] = "Зазимко В.А";
			callBackData[3] = "Акимов Г.А";
			callBackData[4] = "Петрова И.Л";
			callBackData[5] = "Клочков А.В";
			callBackData[6] = "Лемешонок Т.Ю";
			callBackData[7] = "Курилова Е.А";
			callBackData[8] = "Дьячкова П.Д";
			callBackData[9] = "Алексеева К.С";
			callBackData[10] = "Теляков Р.Ф";
			callBackData[11] = "Аюпов Р.Р";
			callBackData[12] = "Горохов А.В";

			setInlineKeyboard(
					"Здравствуйте, " + query.getFrom().getFirstName() + " " + query.getFrom().getLastName()
							+ ", пожалуйста, выберите себя из списка преподавателей кафедры",
					buttonText, callBackData, 13, 2, query.getFrom().getId().toString());
		}

		if (query != null) {
			if (query.getData().equals("Толпегин О.А") || query.getData().equals("Шалыгин А.С")
					|| query.getData().equals("Зазимко В.А") || query.getData().equals("Акимов Г.А")
					|| query.getData().equals("Петрова И.Л") || query.getData().equals("Клочков А.В")
					|| query.getData().equals("Лемешонок Т.Ю") || query.getData().equals("Курилова Е.А")
					|| query.getData().equals("Дьячкова П.Д") || query.getData().equals("Алексеева К.С")
					|| query.getData().equals("Теляков Р.Ф") || query.getData().equals("Аюпов Р.Р")
					|| query.getData().equals("Горохов А.В")) {
				String password = "easydrug";
				boolean check = true;
				do {
					System.out.println("Hello!");
					SendMessage sendMessage = new SendMessage();
					sendMessage.enableMarkdown(true);
					sendMessage.setChatId(query.getFrom().getId().toString());
					sendMessage.setText("Отлично, " + query.getFrom().getFirstName() + " "
							+ query.getFrom().getLastName() + " , пожалуйста, введите Ваш пароль:");
					if (message != null && message.getText().equals(password)) {
						check = false;
					} else {
						sendMessage.setText(query.getFrom().getFirstName() + " " + query.getFrom().getLastName()
								+ " , пожалуйста, введите Ваш пароль заного:");
					}
				} while (check);
			}
		}

		if (query != null)

		{
			if (query.getData().equals("A") || query.getData().equals("E") || query.getData().equals("I")
					|| query.getData().equals("O") || query.getData().equals("P") || query.getData().equals("D")) {

				Groups facultyListGroup = new Groups(query.getData());

				InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
				List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();
				SendMessage sendMessage = new SendMessage();
				int i = 0;

				for (String gr : facultyListGroup.groupList) {

					sendMessage.enableMarkdown(true);
					sendMessage.setChatId(query.getFrom().getId().toString());
					sendMessage.setText(
							"Хорошо, " + query.getFrom().getFirstName() + " ,пожалуйста, выберите свою группу");
					sendMessage.setReplyMarkup(keyboard);
					InlineKeyboardButton button = new InlineKeyboardButton();
					button.setText(gr);
					button.setCallbackData(gr);
					rowInline.add(button);

					if ((i % 4) == 0) {
						listButton.add(rowInline);
						rowInline = new ArrayList<>();
					}

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

		if (query != null && TelegramBot.listGroup.groupList.contains(query.getData())) {
			registerStudent(query.getFrom(), query.getFrom().getId().toString(), query.getData());
		}

	}

	private void setInlineKeyboard(String messageText, String[] buttonText, String[] callBackData, int lineCount,
			int rowCount, String chatId) {
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		SendMessage sendMessage = new SendMessage();

		for (int i = 0; i < lineCount; i++) {

			sendMessage.enableMarkdown(true);
			sendMessage.setChatId(chatId);
			sendMessage.setText(messageText);
			sendMessage.setReplyMarkup(keyboard);
			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(buttonText[i]);
			button.setCallbackData(callBackData[i]);
			rowInline.add(button);

			if (i % rowCount == 0) {
				listButton.add(rowInline);
				rowInline = new ArrayList<>();
			}
		}

		keyboard.setKeyboard(listButton);

		try {
			nt.bot.sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void registerStudent(User user, String chatID, String group) {
		addUser(user.getUserName(), chatID, group, 1);

		sendInlineKeboard(chatID, "Вы подписались на уведомления для группы " + group + ".", "Отписаться", "pause");
	}

	private void registerLecturer(User user, String chatID, String group) {
		addUser(user.getUserName(), chatID, group, 3);

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

	private void addUser(String username, String chatID, String group, int rank) {
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
				sql = "insert into bot_users(username, chatID, groupname, rank) values ('" + username + "','" + chatID
						+ "','" + group + "', '" + rank + "')";
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
