package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name", "desc"));
            assertThat(tracker.findByName("name").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplaceThenReturnNewName() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1");
            Item item2 = new Item("name2", "desc2");
            tracker.add(item1);
            tracker.replace(item1.getId(), item2);
            assertThat(tracker.findById(item1.getId()).getName(), is(item2.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteItemThenDecreaseNumOfColumns() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1");
            tracker.add(item1);
            int expected = tracker.findAll().size();
            tracker.delete(item1.getId());
            assertThat(tracker.findAll().size(), is(expected - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteItemIdNotFindThenNotDecreaseNumOfColumns() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1");
            tracker.add(item1);
            int expected = tracker.findAll().size();
            tracker.delete(String.valueOf(Integer.MAX_VALUE));
            assertThat(tracker.findAll().size(), is(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByIdThenReturnElementWithSameId() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1");
            tracker.add(item1);
            assertThat(tracker.findById(item1.getId()), is(item1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenNotFindByIdThenReturnNull() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1");
            tracker.add(item1);
            assertThat(tracker.findById(String.valueOf(Integer.MAX_VALUE)), is(nullValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
