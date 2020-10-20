package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Класс меню для взаимодействия с пользователем.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-17
 */
public class MenuTracker {

    /**
     * Получение данных от пользователя.
     */
    private Input input;

    /**
     * Хранение заявок.
     */
    private Store tracker;

    /**
     * Хранение действий над заявками.
     */
    private List<UserAction> actions = new ArrayList<>(7);

    private Consumer<String> printer = System.out::println;

    MenuTracker(Input input, Store tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Инициализация пользовательских действий над заявками.
     * Заполнение массива действиями над заявками.
     */
    public void fillActions() {
        this.actions.add(0, this.new AddItem(1, "Добавить новую заявку."));
        this.actions.add(1, new MenuTracker.ShowItems(2, "Отобразить существующие заявки."));
        this.actions.add(2, new EditItem(3, "Редактировать заявку."));
        this.actions.add(3, this.new DeleteItem(4, "Удалить заявку."));
        this.actions.add(4, new MenuTracker.FindById(5, "Поиск заявки по идентификатору."));
        this.actions.add(5, new FindByName(6, "Поиск заявок по имени заявки."));
    }

    /**
     * Отдельное добавление действия в массив действий.
     */
    public void add(UserAction action) {
        this.actions.add(6, action);
    }

    /**
     * Выбор и выполнение действия.
     */
    public void select(int key) {
        this.actions.get(key - 1).execute(this.input, this.tracker);
    }

    /**
     * Отображение меню.
     */
    public void show() {
        this.printer.accept("Меню:");
        for (UserAction action : actions) {
            this.printer.accept(action.info());
        }
    }

    /**
     * Создание массива с ключами пунктов меню.
     */
    public List<Integer> getMenuItemsKeys() {
        List<Integer> result = new ArrayList<>(this.actions.size());
        for (UserAction action : actions) {
            result.add(action.key());
        }
        return result;
    }

    /**
     * Класс описывает добавление новой заявки.
     * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
     * @version 0.1
     * @since version 0.1 2018-01-17
     */
    private class AddItem extends BaseAction {

        AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) {
            String name = input.ask("Введите имя для новой заявки: ");
            String desc = input.ask("Введите описание для новой заявки: ");
            Item item = new Item(name, desc);
            tracker.add(item);
            printer.accept("---------- Создана новая заявка с именем ".concat(item.getName()).concat(" ----------"));
        }
    }

    /**
     * Класс описывает отображение существующих заявок.
     * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
     * @version 0.1
     * @since version 0.1 2018-01-17
     */
    private static class ShowItems extends BaseAction {

        private Consumer<String> printer = System.out::println;

        ShowItems(final int key, final String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) {
            this.printer.accept("---------- Перечень существующих заявок. ----------");
            this.printer.accept("Идентификатор:  Имя:    Описание:");
            for (Item item : tracker.findAll()) {
                this.printer.accept(String.format("%s %s %s", item.getId(), item.getName(), item.getDesc()));
            }
        }
    }

    /**
     * Класс описывает удаление заявки.
     * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
     * @version 0.1
     * @since version 0.1 2018-01-17
     */
    private class DeleteItem extends BaseAction {

        DeleteItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) {
            String id = input.ask("Введите идентификатор удаляемой заявки: ");
            tracker.delete(id);
            printer.accept("---------- Заявка удалена. ----------");
        }
    }

    /**
     * Класс описывает поиск заявки по идентификатору.
     * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
     * @version 0.1
     * @since version 0.1 2018-01-17
     */
    private static class FindById extends BaseAction {

        private Consumer<String> printer = System.out::println;

        FindById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) {
            String id = input.ask("Введите идентификатор искомой заявки: ");
            Item item = tracker.findById(id);
            if (item == null) {
                this.printer.accept("---------- Заявка с таким идентификатором не найдена. ----------");
            } else {
                this.printer.accept("---------- Заявка найдена. ----------");
                this.printer.accept("Идентификатор:  Имя:    Описание:");
                this.printer.accept(item.getId().concat(" ").concat(item.getName()).concat(" ").concat(item.getDesc()));
            }
        }
    }
}

/**
 * Класс описывает редактирование заявки.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-17
 */
class EditItem extends BaseAction {

    private Consumer<String> printer = System.out::println;

    EditItem(final int key, final String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Store tracker) {
        String id = input.ask("Введите идентификатор редактируемой заявки: ");
        Item item = tracker.findById(id);
        if (item == null) {
            this.printer.accept("---------- Заявка с таким идентификатором не найдена. ----------");
        } else {
            String name = input.ask("Введите новое имя для редактируемой заявки или нажмите Enter, чтобы оставить прежнее: ");
            if (!name.equals("")) {
                item.setName(name);
            }
            String desc = input.ask("Введите новое описание для редактируемой заявки или нажмите Enter, чтобы оставить прежнее: ");
            if (!desc.equals("")) {
                item.setDesc(desc);
            }
            tracker.replace(id, item);
            this.printer.accept("---------- Заявка отредактирована. ----------");
        }
    }
}

/**
 * Класс описывает поиск заявок с именем, совпадающим с заданным.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-17
 */
class FindByName extends BaseAction {

    private Consumer<String> printer = System.out::println;

    FindByName(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Store tracker) {
        String key = input.ask("Введите ключ для поиска заявок по имени: ");
        List<Item> items = tracker.findByName(key);
        if (items.size() == 0) {
            this.printer.accept("---------- Заявок с таким именем не найдено. ----------");
        } else {
            this.printer.accept("---------- Найдены следующие заявки. ----------");
            this.printer.accept("Идентификатор:  Имя:    Описание:");
            for (Item item : items) {
                this.printer.accept(item.getId().concat(" ").concat(item.getName()).concat(" ").concat(item.getDesc()));
            }
        }
    }
}

/**
 * Класс описывает выход из меню и завершение.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-17
 */
class Exit implements UserAction {
    private StartUI ui;

    Exit(StartUI ui) {
        this.ui = ui;
    }

    @Override
    public int key() {
        return 7;
    }

    @Override
    public void execute(Input input, Store tracker) {
        this.ui.stop();
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Выйти из программы.");
    }
}