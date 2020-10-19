package ru.job4j.tracker;

import java.util.List;

public class StubInput implements Input {
    private String[] answers;
    private int position = 0;

    /**
     * Класс для организации тестирования ввода.
     * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
     * @since version 0.1 2018-02-22
     */
    StubInput(String[] answers) {
        this.answers = answers;
    }

    @Override
    public String ask(String question) {
        return this.answers[position++];
    }

    @Override
    public int ask(String question, List<Integer> range) {
        Integer key = Integer.valueOf(this.ask(question));
        boolean isExist = false;
        for (Integer value : range) {
            if (key.equals(value)) {
                isExist = true;
                break;
            }
        }
        if (isExist) {
            return key;
        } else {
            throw new MenuOutException("Ввод пользователя вне диапазона ключей пунктов меню.");
        }
    }
}
