package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Класс для хранения и реализации различных операций с заявками.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-05
 */
public class MemTracker {
    /**
     * Массив для хранение заявок.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * Метод реализаущий добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод поиска заявки по id и ее замены (на item).
     * @param id искомый идентификатор.
     * @param item заявка на замену.
     */
    public void replace(String id, Item item) {
        boolean isFound = false;
        int index = 0;
        Predicate<String> predicate = s -> s.equals(id);
        while (!isFound) {
            if (predicate.test(this.items.get(index).getId())) {
                this.items.set(index, item);
                isFound = true;
            } else {
                index++;
            }
        }
    }

    /**
     * Метод удаления элемента по id
     * @param id идентификатор
     */
    public void delete(String id) {
        boolean isFound = false;
        int index = 0;
        Predicate<String> predicate = s -> s.equals(id);
        while (!isFound && (index < this.items.size())) {
            if (predicate.test(this.items.get(index).getId())) {
                isFound = true;
            } else {
                index++;
            }
        }
        if (isFound) {
            this.items.remove(index);
        }
    }

    /**
     * Метод возвращает массив всех созданных заявок.
     * @return массив заявок.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Метод возвращает массив заявок с именами, идентичными заданному значению.
     * @param key заданное значение.
     * @return массив заявок с именами, совпадающими с заданным значением.
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        Predicate<String> predicate = s -> s.equals(key);
        for (Item item : items) {
            if (predicate.test(item.getName())) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Метод нахождения заявки по идентификатору id
     * @param id идентификатор
     * @return заявка Item
     */
    public Item findById(String id) {
        Item result = null;
        boolean isFound = false;
        int index = 0;
        Predicate<String> predicate = s -> s.equals(id);
        while (!isFound && (index < this.items.size())) {
            if (predicate.test(this.items.get(index).getId())) {
                result = this.items.get(index);
                isFound = true;
            } else {
                index++;
            }
        }
        return result;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        String result = "";
        Random random = new Random();
        char[] allowedChars = "0123456789ABCDIFGHIJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuvwxyz!@#$%^&*()_+-=?;:`~<>,.|".toCharArray();
        while (result.length() < 32) {
            result = result.concat(String.valueOf(allowedChars[random.nextInt(allowedChars.length)]));
        }
        return result;
    }
}
