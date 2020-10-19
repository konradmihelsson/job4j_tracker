package ru.job4j.tracker;

import java.util.List;

/**
 * Класс пользовательского интерфейса для взаимодействия с пользователем.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-05
 */
public class StartUI implements Stop {

    /**
     * Получение данных от пользователя.
     */
    private Input input;

    /**
     * Хранение заявок.
     */
    private Store tracker;

    /**
     * Флаг выполнения/выхода.
     */
    private boolean processing = true;

    /**
     * Конструктор, инициализирующий поля.
     * @param input пользовательский ввод.
     * @param tracker хранение заявок.
     */
    StartUI(Input input, Store tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы.
     */
    public void init() {
        this.tracker.init();
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        menu.add(new Exit(this));
        List<Integer> menuItemsKeys = menu.getMenuItemsKeys();

        do {
            menu.show();
            int key = input.ask("Выберите пункт меню: ", menuItemsKeys);
            menu.select(key);
        } while (processing);
    }

    public static void main(String[] args) {
        new StartUI(
                new ValidateInput(
                        new ConsoleInput()
                ),
                new SqlTracker()
        ).init();
    }

    /**
     * Метод реализует выход из основного цикла программы.
     */
    @Override
    public void stop() {
        this.processing = false;
    }
}
