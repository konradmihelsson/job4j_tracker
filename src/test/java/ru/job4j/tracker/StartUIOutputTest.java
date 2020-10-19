package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class StartUIOutputTest {

    // поле хранит ссылку на стандартный вывод
    private final PrintStream stdout = System.out;
    // буферный поток вывода
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Ignore("Disabled for educational reason.")
    @Test
    public void whenShowAllItems() {
        Store tracker = new SqlTracker();
        Item first = tracker.add(new Item("test_1", "test_desc_1"));
        Item second = tracker.add(new Item("test_2", "test_desc_2"));
        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        Input input = new StubInput(new String[]{"2", "7"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .append("---------- Перечень существующих заявок. ----------")
                                .append(System.lineSeparator())
                                .append("Идентификатор:  Имя:    Описание:")
                                .append(System.lineSeparator())
                                .append("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo test_1 test_desc_1")
                                .append(System.lineSeparator())
                                .append("okK02CI02DtBzNJoqaxMwPOoypIevwZ4 test_2 test_desc_2")
                                .append(System.lineSeparator())
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Ignore("Disabled for educational reason.")
    @Test
    public void whenFindItemById() {
        Store tracker = new SqlTracker();
        Item first = tracker.add(new Item("test_1", "test_desc_1"));
        Item second = tracker.add(new Item("test_2", "test_desc_2"));
        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        Input input = new StubInput(new String[]{"5", "DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo", "7"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .append("---------- Заявка найдена. ----------")
                                .append(System.lineSeparator())
                                .append("Идентификатор:  Имя:    Описание:")
                                .append(System.lineSeparator())
                                .append("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo test_1 test_desc_1")
                                .append(System.lineSeparator())
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }

    @Ignore("Disabled for educational reason.")
    @Test
    public void whenFindItemByName() {
        Store tracker = new SqlTracker();
        Item first = new Item("1st", "1st");
        Item second = new Item("2nd", "2nd");
        Item third = new Item("3rd", "3rd");
        Item fourth = new Item("2nd", "4th");
        Item fifth = new Item("5th", "5th");
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);
        first.setId("11111111111111111111111111111111");
        second.setId("22222222222222222222222222222222");
        third.setId("33333333333333333333333333333333");
        fourth.setId("44444444444444444444444444444444");
        fifth.setId("55555555555555555555555555555555");
        Input input = new StubInput(new String[]{"6", "2nd", "7"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .append("---------- Найдены следующие заявки. ----------")
                                .append(System.lineSeparator())
                                .append("Идентификатор:  Имя:    Описание:")
                                .append(System.lineSeparator())
                                .append("22222222222222222222222222222222 2nd 2nd")
                                .append(System.lineSeparator())
                                .append("44444444444444444444444444444444 2nd 4th")
                                .append(System.lineSeparator())
                                .append("Меню:")
                                .append(System.lineSeparator())
                                .append("1. Добавить новую заявку.")
                                .append(System.lineSeparator())
                                .append("2. Отобразить существующие заявки.")
                                .append(System.lineSeparator())
                                .append("3. Редактировать заявку.")
                                .append(System.lineSeparator())
                                .append("4. Удалить заявку.")
                                .append(System.lineSeparator())
                                .append("5. Поиск заявки по идентификатору.")
                                .append(System.lineSeparator())
                                .append("6. Поиск заявок по имени заявки.")
                                .append(System.lineSeparator())
                                .append("7. Выйти из программы.")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }
}