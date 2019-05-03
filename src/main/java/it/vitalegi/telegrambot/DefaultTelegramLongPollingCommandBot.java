package it.vitalegi.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import it.vitalegi.telegrambot.command.HelloCommand;
import it.vitalegi.telegrambot.command.HelloCommand2;
import it.vitalegi.telegrambot.command.HelpCommand;

public class DefaultTelegramLongPollingCommandBot extends TelegramLongPollingCommandBot {

	public DefaultTelegramLongPollingCommandBot(String botUsername) {
		super(botUsername);
		register(new HelloCommand());
		register(new HelloCommand2());
		HelpCommand helpCommand = new HelpCommand(this);
		register(helpCommand);

		registerDefaultAction((absSender, message) -> {
			SendMessage commandUnknownMessage = new SendMessage();
			commandUnknownMessage.setChatId(message.getChatId());
			commandUnknownMessage.setText("The command '" + message.getText()
					+ "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
			try {
				absSender.execute(commandUnknownMessage);
			} catch (TelegramApiException e) {
				log.error(e.getMessage(), e);
			}
			helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
		});
	}

	@Override
	public void processNonCommandUpdate(Update update) {
		log.info("processNonCommandUpdate {}", update.toString());

		SendMessage commandUnknownMessage = new SendMessage();
		commandUnknownMessage.setChatId(update.getMessage().getChatId());
		commandUnknownMessage.setText("The command '" + update.getMessage().getText()
				+ "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
		try {
			execute(commandUnknownMessage);
		} catch (TelegramApiException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public String getBotToken() {
		return TelegramBotApplication.BOT_TOKEN;
	}

	Logger log = LoggerFactory.getLogger(DefaultTelegramLongPollingCommandBot.class);
}
