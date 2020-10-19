package ru.job4j.tracker;

/**
 * Абстрактный класс, реализующий общие методы
 * пользовательских действий.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-02-22
 */
public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    protected BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * Метод возвращает ключ.
     */
    @Override
    public int key() {
        return this.key;
    }

    /**
     * Метод отображает информацию о действии.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
