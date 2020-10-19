package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        String query = "insert into items (name) values ('" + item.getName() + "')";
        Item result = null;
        try (Statement stmt = cn.createStatement()) {
            if (stmt.executeUpdate(query) == 1) {
                result = item;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean replace(String id, Item item) {
        return transmit("update items set name = '" + item.getName() + "' where id = " + Integer.parseInt(id));
    }

    @Override
    public boolean delete(String id) {
        return transmit("delete from items where id = " + Integer.parseInt(id));
    }

    @Override
    public List<Item> findAll() {
        return receive("select id, name from items");
    }

    @Override
    public List<Item> findByName(String key) {
        return receive("select id, name from items where name = '" + key + "'");
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        String query = "select id, name from items where id = " + id;
        try (Statement stmt = cn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                result = new Item(rs.getString(2), "id from db = " + rs.getInt(1));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    private boolean transmit(String query) {
        boolean result = false;
        try (Statement stmt = cn.createStatement()) {
            if (stmt.executeUpdate(query) == 1) {
                result = true;

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    private List<Item> receive(String query) {
        List<Item> result = new ArrayList<>();
        try (Statement stmt = cn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(new Item(rs.getString(2), "id from db = " + rs.getInt(1)));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }
}
