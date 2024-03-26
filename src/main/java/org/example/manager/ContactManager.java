package org.example.manager;

import org.example.DTO.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class ContactManager {

    private List<Contact> contacts;

    @Value("${contacts.file.save.location}")
    private String saveFileLocation;

    private final ContactInitializer contactInitializer;

    @Autowired
    public ContactManager(ContactInitializer contactInitializer) {
        this.contactInitializer = contactInitializer;
        this.contacts = new ArrayList<>();
        try {
            this.contacts.addAll(contactInitializer.initializeContactsFromFile());
        } catch (IOException e) {
            System.err.println("Ошибка при инициализации контактов из файла: " + e.getMessage());
        }
    }

    public void addContact(String fullName, String phoneNumber, String email) {
        Contact newContact = new Contact(fullName, phoneNumber, email);
        contacts.add(newContact);
        System.out.println("Контакт успешно добавлен.");
    }

    public void removeContact(String email) {
        Contact contactToRemove = null;
        for (Contact contact : contacts) {
            if (contact.getEmail().equals(email)) {
                contactToRemove = contact;
                break;
            }
        }
        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            System.out.println("Контакт с email " + email + " удален.");
        } else {
            System.out.println("Контакт с email " + email + " не найден.");
        }
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Список контактов пуст.");
        } else {
            System.out.println("Список контактов:");
            for (Contact contact : contacts) {
                System.out.println(contact.getFullName() + " | " + contact.getPhoneNumber() + " | " + contact.getEmail());
            }
        }
    }

    public void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveFileLocation))) {
            for (Contact contact : contacts) {
                writer.println(contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail());
            }
            System.out.println("Контакты успешно сохранены в файл " + saveFileLocation);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении контактов в файл: " + e.getMessage());
        }
    }
}