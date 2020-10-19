package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class StartUITest {

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "test name", "test desc", "7"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
    }

    @Test
    public void whenUserEditItemThenTrackerUpdateItem() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "test desc"));
        Input input = new StubInput(new String[]{"3", item.getId(), "replaced name", "", "7"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("replaced name"));
    }

    @Test
    public void whenUserDeleteWhenTrackerRemoveItem() {
        Tracker tracker = new Tracker();
        List<Item> expected = new ArrayList<>(2);

        Item first = tracker.add(new Item("test_1", "test_desc_1"));
        Item second = tracker.add(new Item("test_2", "test_desc_2"));
        Item third = tracker.add(new Item("test_3", "test_desc_3"));
        expected.add(first);
        expected.add(third);
        Input input = new StubInput(new String[]{"4", second.getId(), "7"});
        new StartUI(input, tracker).init();
        assertThat(expected, is(tracker.findAll()));
    }
}