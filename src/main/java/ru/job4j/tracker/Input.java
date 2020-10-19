package ru.job4j.tracker;

import java.util.List;

/**
 * Интерфейс для организации различных возможностей ввода.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-05
 */
public interface Input {
    String ask(String question);
    int ask(String question, List<Integer> range);
}
