package ru.urfu;

import ru.urfu.updates.HandlerRegistry;

/**
 * Класс для запуска приложения
 */
public class Application {

    public static void main(String[] args) {
        HandlerRegistry handlerRegistry = new HandlerRegistry();

        String telegramBotName = System.getenv("telegram_botName");
        String telegramToken = System.getenv("telegram_token");
        new TelegramBot(telegramBotName, telegramToken, handlerRegistry)
                .start();

        String discordToken = System.getenv("discord_token");
        new DiscordBot(discordToken, handlerRegistry)
                .start();

        /*
         тут может быть сколько угодно чат платформ
         и все должны работать одинаково
        */
    }

}
