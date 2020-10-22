package ru.job4j.tracker;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс заявка.
 * У класса есть поля идентификатор, имя, описание, дата создания, комментарии.
 * У класса есть методы геттеры и сеттеры.
 * @author Konrad Mihelsson (konrad.mihelsson@gmail.com)
 * @version 0.1
 * @since version 0.1 2018-01-05
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private long created;
    private String[] comments;

    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getCreated() == item.getCreated() &&
                getId().equals(item.getId()) &&
                getName().equals(item.getName()) &&
                getDesc().equals(item.getDesc()) &&
                Arrays.equals(getComments(), item.getComments());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getDesc(), getCreated());
        result = 31 * result + Arrays.hashCode(getComments());
        return result;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String[] getComments() {
        return this.comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }
}
