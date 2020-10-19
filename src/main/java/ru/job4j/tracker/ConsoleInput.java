package ru.job4j.tracker;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для организации пользовательского ввода с консоли.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-05
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
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
