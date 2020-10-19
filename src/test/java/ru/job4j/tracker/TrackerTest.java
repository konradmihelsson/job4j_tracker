package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("name1", "desc1", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item oldItem = new Item("name1", "description1", 999999999L);
        tracker.add(oldItem);
        Item newItem = new Item("name1_1", "description1_1", 898989898989L);
        newItem.setId(oldItem.getId());
        tracker.replace(oldItem.getId(), newItem);
        assertThat(tracker.findById(oldItem.getId()).getName(), is("name1_1"));
    }

    @Test
    public void whenDeleteItemThenDeleteArrayElement() {
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("4th", "4th", 444L);
        Item fifth = new Item("5th", "5th", 555L);

        Tracker tracker = new Tracker();
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);

        Tracker expected = new Tracker();
        expected.add(first);
        expected.add(second);
        expected.add(third);
        expected.add(fourth);

        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        third.setId("pu1XhqCAFPggxDAa6F07sHTt05TnsD0j");
        fourth.setId("8NqsTns6XjCdYNVvbh0Q0FI65YI38QRN");
        fifth.setId("LKM32vMOqF88aPsAbqGoj3DgQWQ2Jt7T");

        tracker.delete("LKM32vMOqF88aPsAbqGoj3DgQWQ2Jt7T");

        assertThat(expected.findAll(), is(tracker.findAll()));
    }

    @Test
    public void whenDeleteItemIdNotFindThenNotDeleteArrayElements() {
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);

        Tracker tracker = new Tracker();
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);

        Tracker expected = new Tracker();
        expected.add(first);
        expected.add(second);
        expected.add(third);

        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        third.setId("pu1XhqCAFPggxDAa6F07sHTt05TnsD0j");

        tracker.delete("LKM32vMOqF88aPsAbqGoj3DgQWQ2Jt7T");

        assertThat(expected.findAll(), is(tracker.findAll()));
    }

    @Test
    public void whenFindAllThenReturnNotNullElementsOnly() {
        Tracker tracker = new Tracker();
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("4th", "4th", 444L);
        Item fifth = new Item("5th", "5th", 555L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);

        List<Item> expected = new ArrayList<>();
        expected.add(first);
        expected.add(second);
        expected.add(third);
        expected.add(fourth);
        expected.add(fifth);

        assertThat(expected, is(tracker.findAll()));
    }

    @Test
    public void whenFindByNameThenReturnArrayOfItemsWithSameName() {
        Tracker tracker = new Tracker();
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("2nd", "4th", 444L);
        Item fifth = new Item("5th", "5th", 555L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);

        List<Item> expected = new ArrayList<>();
        expected.add(second);
        expected.add(fourth);

        assertThat(expected, is(tracker.findByName("2nd")));
    }

    @Test
    public void whenNotFindByNameThenReturnArrayWithoutElements() {
        Tracker tracker = new Tracker();
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("4th", "4th", 444L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        List<Item> expected = new ArrayList<>();

        assertThat(expected, is(tracker.findByName("5th")));
    }

    @Test
    public void whenFindByIdThenReturnElementWithSameId() {
        Tracker tracker = new Tracker();
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("4th", "4th", 444L);
        Item fifth = new Item("5th", "5th", 555L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);
        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        third.setId("pu1XhqCAFPggxDAa6F07sHTt05TnsD0j");
        fourth.setId("8NqsTns6XjCdYNVvbh0Q0FI65YI38QRN");
        fifth.setId("LKM32vMOqF88aPsAbqGoj3DgQWQ2Jt7T");

        assertThat(second, is(tracker.findById("okK02CI02DtBzNJoqaxMwPOoypIevwZ4")));
    }

    @Test
    public void whenNotFindByIdThenReturnNull() {
        Tracker tracker = new Tracker();
        Item first = new Item("1st", "1st", 111L);
        Item second = new Item("2nd", "2nd", 222L);
        Item third = new Item("3rd", "3rd", 333L);
        Item fourth = new Item("4th", "4th", 444L);
        Item fifth = new Item("5th", "5th", 555L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        tracker.add(fifth);
        first.setId("DnNjYmQgYs9KbC5W82Oz5UOoDLztkmVo");
        second.setId("okK02CI02DtBzNJoqaxMwPOoypIevwZ4");
        third.setId("pu1XhqCAFPggxDAa6F07sHTt05TnsD0j");
        fourth.setId("8NqsTns6XjCdYNVvbh0Q0FI65YI38QRN");
        fifth.setId("LKM32vMOqF88aPsAbqGoj3DgQWQ2Jt7T");

        assertThat(null, is(tracker.findById("11112222333344445555666677778888")));
    }
}