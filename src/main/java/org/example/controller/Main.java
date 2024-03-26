package org.example.controller;

import org.example.DTO.Contact;
import org.example.manager.ContactManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final ContactManager contactManager;

    @Autowired
    public Main(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотреть контакты");
            System.out.println("2. Добавить контакт");
            System.out.println("3. Удалить контакт");
            System.out.println("4. Сохранить контакты в файл");
            System.out.println("5. Выйти из приложения");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    contactManager.displayContacts();
                    break;
                case 2:
                    System.out.println("Введите данные контакта в формате Ф. И. О.; номер телефона; адрес электронной почты:");
                    String input = scanner.nextLine();
                    String[] contactInfo = input.split(";");
                    if (contactInfo.length != 3) {
                        System.out.println("Некорректный формат ввода. Пожалуйста, введите Ф. И. О.; номер телефона; адрес электронной почты.");
                        break;
                    }
                    String fullName = contactInfo[0];
                    String phoneNumber = contactInfo[1];
                    String email = contactInfo[2];
                    contactManager.addContact(fullName, phoneNumber, email);
                    break;
                case 3:
                    System.out.println("Введите email контакта, который хотите удалить:");
                    String emailToRemove = scanner.nextLine();
                    contactManager.removeContact(emailToRemove);
                    break;
                case 4:
                    contactManager.saveContactsToFile();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }

        scanner.close();
    }
}