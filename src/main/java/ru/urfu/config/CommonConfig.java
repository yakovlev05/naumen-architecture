package ru.urfu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

/**
 * Конфигурационный класс для общих бинов.
 */
@Configuration
public class CommonConfig {

    /**
     * Бин для сканера ввода с консоли.
     *
     * @return Scanner
     */
    @Bean
    public Scanner cliScanner() {
        return new Scanner(System.in);
    }

}
