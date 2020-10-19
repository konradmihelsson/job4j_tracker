package ru.job4j.tracker;

import java.util.List;

/**
 * Класс для организации пользовательского ввода с консоли
 * с проверкой корректности ввода.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-02-22
 */
public class ValidateInput implements Input {

    private final Input input;

    ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, List<Integer> range) {
        int value = -1;
        boolean isExist = false;
        do {
            try {
                value = this.input.ask(question, range);
                isExist = true;
            } catch (MenuOutException moe) {
                System.out.println("Некорректный ввод. Необходимо ввести число из диапазона ключей пунктов меню.");
            } catch (NumberFormatException nfe) {
                System.out.println("Некорректный ввод. Необходимо ввести целое число.");
            }
        } while (!isExist);
        return value;
    }
}
