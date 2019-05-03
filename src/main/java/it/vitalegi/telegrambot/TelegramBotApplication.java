package it.vitalegi.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TelegramBotApplication implements ApplicationRunner {

	public static final String BOT_USERNAME = "PASTE-HERE";
	public static final String BOT_TOKEN = "PASTE-HERE";

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			//telegramBotsApi.registerBot(new DefaultTelegramLongPollingBot());
			telegramBotsApi.registerBot(new DefaultTelegramLongPollingCommandBot(BOT_USERNAME));
		} catch (TelegramApiException e) {
			log.error("", e);
		}
	}

	Logger log = LoggerFactory.getLogger(TelegramBotApplication.class);

}
