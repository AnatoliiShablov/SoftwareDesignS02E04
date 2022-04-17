package ru.shablov.common;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class User {
    public final int id;
    public final SortedMap<Integer, Stocks> stocks = new TreeMap<>();
    public int money;

    public User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", money=" + money + ", stocks=" + stocks + '}';
    }
}
