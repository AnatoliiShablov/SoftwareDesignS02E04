package ru.shablov.common;

import java.util.Objects;

public class Stock {
    public final int id;
    public final String companyName;
    public int price = 0;

    public Stock(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id == stock.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Stock{" + "id=" + id + ", companyName='" + companyName + '\'' + ", price=" + price + '}';
    }
}
