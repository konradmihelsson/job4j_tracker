package ru.job4j.tracker;

/**
 * Класс исключения, выбрасываемое при вводе ключа меню
 * вне допустимых значений.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-02-21
 */
public class MenuOutException extends RuntimeException {

    public MenuOutException(String msg) {
        super(msg);
    }
}
