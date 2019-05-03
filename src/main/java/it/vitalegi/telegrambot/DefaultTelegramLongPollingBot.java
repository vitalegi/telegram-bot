package it.vitalegi.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class DefaultTelegramLongPollingBot extends TelegramLongPollingBot {

	public DefaultTelegramLongPollingBot() {
		super();
	}

	@Override
	public void onUpdateReceived(Update update) {
		log.info("onUpdateReceived {}", update);
		String text = update.getMessage().getText();
		log.info("Received: {}", text);

		SendMessage message = new SendMessage();
		message.setChatId(update.getMessage().getChatId());
		message.setText("Hello");
		try {
			super.execute(message);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public String getBotUsername() {
		log.info("getBotUsername");
		return TelegramBotApplication.BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		log.info("getBotToken");
		return TelegramBotApplication.BOT_TOKEN;
	}

	Logger log = LoggerFactory.getLogger(DefaultTelegramLongPollingBot.class);
}
