package ru.job4j.tracker;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenInputString() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[]{"invalid", "1"})
        );
        List<Integer> chooseKeys = new ArrayList<>();
        chooseKeys.add(1);
        input.ask("Please enter.....", chooseKeys);
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Некорректный ввод. Необходимо ввести целое число.%n")
                )
        );
    }

    @Test
    public void whenInputWrongKey() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[]{"777", "1"})
        );
        List<Integer> chooseKeys = new ArrayList<>();
        chooseKeys.add(1);
        chooseKeys.add(2);
        chooseKeys.add(3);
        input.ask("Please enter...", chooseKeys);
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Некорректный ввод. Необходимо ввести число из диапазона ключей пунктов меню.%n")
                )
        );
    }
}
