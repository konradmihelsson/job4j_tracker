package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection connection) {
        this.cn = connection;
    }

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
        String query = "insert into items (name, description) values (?, ?)";
        Item result = null;
        try (PreparedStatement ps = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            if (ps.executeUpdate() == 1) {
                result = new Item(item.getName(),item.getDesc());
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getString(1));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        String query = "update items set name = ?, description = ? where id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.setInt(3, Integer.parseInt(id));
            if (ps.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        String query = "delete from items where id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));
            if (ps.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        String query = "select id, name, description from items";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getString(3));
                item.setId(rs.getString(1));
                result.add(item);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        String query = "select id, name, description from items where name = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getString(3));
                item.setId(rs.getString(1));
                result.add(item);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        String query = "select id, name, description from items where id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Item(rs.getString(2), rs.getString(3));
                result.setId(rs.getString(1));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }
}
