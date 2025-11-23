package ru.urfu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.urfu.command.CommandHandlerRegistry;
import ru.urfu.command.CommandNotFound;

import java.util.Scanner;

/**
 * Основной класс консольного приложения.
 */
@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final Scanner scanner;


    public ConsoleApp(
            CommandHandlerRegistry commandHandlerRegistry,
            Scanner scanner
    ) {
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.scanner = scanner;
    }

    /**
     * Точка входа приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Консольное приложение ===");

        while (true) {
            System.out.println("\nКоманды: import, list, create, export, exit");
            System.out.print("> ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("exit")) {
                return;
            }

            doCommand(cmd);
        }
    }

    private void doCommand(String cmd) {
        try {
            commandHandlerRegistry.handle(cmd);
        } catch (CommandNotFound e) {
            System.out.println("Неизвестная команда");
        }
    }
}
