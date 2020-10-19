package ru.job4j.tracker;

/**
 * Интерфейс, описывающий пользовательское действие.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-17
 */
public interface UserAction {

    /**
     * Метод возвращает ключ.
     */
    int key();

    /**
     * Метод описывает действие.
     */
    void execute(Input input, Store tracker);

    /**
     * Метод отображает информацию о действии.
     */
    String info();
}
